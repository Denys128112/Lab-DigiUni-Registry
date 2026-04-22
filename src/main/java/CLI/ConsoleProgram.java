package CLI;

import DigiPackage.*;
import network.Client;
import network.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saving.SaveOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static CLI.InputHelper.getIntInput;

public class ConsoleProgram {
    private static final Logger log = LoggerFactory.getLogger(ConsoleProgram.class);
    static Scanner sc = new Scanner(System.in);
    static CRUDoperations crudoperations = new CRUDoperations();
    static SearchOperations searchoperations = new SearchOperations();
    static Reportoperations reportoperations = new Reportoperations();

    static UserSession currentSession;

    static Repository repository = new Repository();
    static UniversityRepository universityRepository = new UniversityRepository();
    static UserRole currentUserRole = UserRole.GUEST;
    static SaveOperations saveOperations = new SaveOperations();
    static Server server = new Server();
    static Client client;
    static boolean is_finished = false;
    public static void main(String[] args) {
        try {
            log.debug("Starting ConsoleProgram load repositories from JSONs");
            saveOperations.loadDatabase(repository, universityRepository);
            log.info("Loading users from database successful");
        }
        catch (IOException e) {
            log.error("JSON missed {}",e.getMessage());
            System.out.println("Нажаль дані з бази данних були втрачені, ми дуже вам співчуваємо, але вам треба заповнити все з самісінького початку або завантижити з іншого комп'ютера");
        }
        run();
    }
    private static void run() {
        log.debug("Starting ConsoleProgram run");
        System.out.println("Вітаємо в програмі DigiUni Registry(консольна інформаційна система університету)");
        authenticate();
        backgroundSave();
        while (!is_finished) {
            mainMenu();
        }
    }

    private static void backgroundShare() {
        Thread dataSharer= new Thread(() -> {
            try {
                server.startServer(repository,universityRepository);
            } catch (IOException e) {
                log.error("Problem with sharing {}",e.getMessage());
            }
        });
        dataSharer.setDaemon(true);
        dataSharer.start();

    }
    private static void updateDatabaseFromNet() {
        Client client = new Client();
        try {
            client.accept();
        }catch (Exception e){
            log.error("cannot accept connection {}",e.getMessage());
        }
    }

    private static void authenticate() {
        log.debug("Authenticating user");
        System.out.println("\n--- АВТОРИЗАЦІЯ ---");
        System.out.println("1) Увійти як Адміністратор (повний доступ)");
        System.out.println("2) Увійти як Менеджер (запис та читання)");
        System.out.println("3) Увійти як Гість (тільки читання)");

        int roleChoice = getIntInput("Ваш вибір", 1, 3);

        if (roleChoice == 1 || roleChoice == 2) {
            System.out.print("Введіть пароль: ");
            String password = sc.nextLine();

            if (roleChoice == 1 && password.equals("admin")) {           // JUST SIMPLE PASSWORD; CAN BE CHANGED
                currentUserRole = UserRole.ADMIN;
                currentSession = new UserSession("Адміністратор", currentUserRole);
                System.out.println("Успішно! Сесія створена для: " + currentSession.username());
                log.info("admin sesion started {}",currentSession.username());
            } else if (roleChoice == 2 && password.equals("manager")) {                          // JUST SIMPLE PASSWORD; CAN BE CHANGED
                currentUserRole = UserRole.MANAGER;
                currentSession = new UserSession("Менеджер", currentUserRole);
                System.out.println("Успішно! Сесія створена для: " + currentSession.username());
                log.info("manager sesion started {}",currentSession.username());
            } else {
                System.out.println("Невірний пароль. Вас авторизовано як Гостя.");
                currentUserRole = UserRole.GUEST;
                log.warn("incorect password was 3 times in row");
                log.info("guest sesion started");
            }
        } else {
            currentUserRole = UserRole.GUEST;
            currentSession = new UserSession("Гість", currentUserRole);
            System.out.println("Ви увійшли як Гість.");
            log.info("guest sesion started {}",currentSession.username());
        }
    }

    private static void mainMenu() {
        log.debug("Main Menu openned");
        System.out.println("Оберіть з чим бажаєте працювати: 1)Університет 2)Факультет 3)Кафедра 4)Викладач 5)Студент 6)Пошуки 7)Сортування 8)Збереження 9)Мережеві операції 0) вийти");
        int choice = getIntInput("Оберіть операцію", 0, 9);
        switch (choice) {
            case 1:
                workWithUniversity();
                break;
            case 2:
                workWithFaculty();
                break;
            case 3:
                workWithDepartment();
                break;
            case 4:
                workWithTeacher();
                break;
            case 5:
                workWithStudent();
                break;
            case 6:
                search();
                break;
            case 7:
                reports();
                break;
            case 8:
                saveOperations.commitChanges();
                break;
            case 9:
                netOperations();
            case 0:
                askForCommitChanges();
                is_finished = true;
                break;
        }
    }

    private static void askForCommitChanges() {
        int choice=getIntInput("Ви хочете зберегти дані? 1)так 2)ні", 1,2);
        if (choice==1 ) {
            saveOperations.commitChanges();
            saveOperations.deleteTempFiles();
        }

    }

    private static void netOperations() {
        int a=getIntInput("1)Стати сервером 2)Завантажити дані з мережі 0)вийти",0,2);
        switch (a) {
            case 1:
                backgroundShare();
                break;
            case 2:
                updateDatabaseFromNet();
                break;
            default:
                break;
        }
    }

    private static int chooseOperation() {

        int mask = currentUserRole.getMask();

        if ((mask & UserRole.CAN_WRITE) != 0) {
            System.out.println("1. Створити");
            System.out.println("2. Редагувати");
        }
        if ((mask & UserRole.CAN_DELETE) != 0) {
            System.out.println("3. Видалити");
        }

        System.out.println("4. Читати");
        System.out.println("0. Назад");

        while (true) {
            int choice = getIntInput("Ваш вибір", 0, 4);

            boolean canWrite = (mask & UserRole.CAN_WRITE) != 0;
            boolean canDelete = (mask & UserRole.CAN_DELETE) != 0;

            if ((choice == 1 || choice == 2) && !canWrite) {
                System.out.println("Помилка: У вас немає доступу до створення або редагування.");
            } else if (choice == 3 && !canDelete) {
                System.out.println("Помилка: У вас немає доступу до видалення.");
            } else {
                return choice;
            }
        }
    }

    private static void workWithTeacher() {
        log.debug("Working with Teacher");
        int choice = 1;
        while (choice != 0) {
            choice = chooseOperation();
            switch (choice) {
                case 1:
                    crudoperations.createTeacher();
                    break;
                case 2:
                    crudoperations.updateTeacher();
                    break;
                case 3:
                    crudoperations.deleteTeacher();
                    break;
                case 4:
                    crudoperations.readTeacher();
                    break;

            }
        }
        log.info("Teacher finish work with teacher");
    }

    private static void workWithStudent() {
        log.debug("Working with Student");
        int choice = 1;
        while (choice != 0) {
            choice = chooseOperation();
            switch (choice) {
                case 1:
                    crudoperations.createStudent();
                    break;
                case 2:
                    crudoperations.updateStudent();
                    break;
                case 3:
                    crudoperations.deleteStudent();
                    break;
                case 4:
                    crudoperations.readStudent();
                    break;

            }
        }
        log.info(" finish work with student");
    }

    private static void workWithFaculty() {
        log.debug("Working with Faculty");
        int choice = 1;
        while (choice != 0) {
            choice = chooseOperation();
            switch (choice) {
                case 1:
                    crudoperations.createFaculty();
                    break;
                case 2:
                    crudoperations.updateFaculty();
                    break;
                case 3:
                    crudoperations.deleteFaculty();
                    break;
                case 4:
                    crudoperations.readFaculty();
                    break;

            }
        }
        log.info(" finish work with Faculty");
    }

    private static void workWithUniversity() {
        log.debug("Working with University");
        System.out.println("1) Читати");
        System.out.println("2) Список кафедр університета");
        System.out.println("3) Назад");
        int choice=getIntInput("Оберіть операцію", 1, 3);
        switch (choice) {
            case 1:
                System.out.println(crudoperations.getUniversity());
                break;
            default:
                System.out.println("В розробці");
        }
        log.info(" finish work with university");
    }

    private static void workWithDepartment() {
        log.debug("Working with Department");
        int choice = 1;
        while (choice != 0) {
            choice = chooseOperation();
            switch (choice) {
                case 1:
                    crudoperations.createDepartment();
                    break;
                case 2:
                    crudoperations.updateDepartment();
                    break;
                case 3:
                    crudoperations.deleteDepartment();
                    break;
                case 4:
                    crudoperations.readDepartment();
                    break;

            }
        }
        log.info(" finish work with department");
    }




    private static void reports() {
        log.debug("Reports operations");
        System.out.println("Оберіть звіт: 1)Студенти за курсом 2)Студенти за Алфавітом 3)Викладачі за Алфавітом 4)Люди за Алфавітом 5)Студенти факультету за алфавітом 6)Викладачі факультету за Алфавітом 7)Викладачі кафедри за алфавітом 8)Студенти кафедри за алфавітом 9)Студенти кафедри за курсами 10)Фільтровані студенти певного курсу 11)Фільтровані студенти кафедри за курсом");
        int choice = getIntInput("Ваш вибір", 1, 11);
        log.trace("choice {} was choosen",choice);
        List result= new ArrayList<>();
        switch (choice){
            case 1:
                result=reportoperations.studentByCourse(repository.getStudents());
                break;
            case 2:
                result=reportoperations.personsByAlpabhet(repository.getStudents());
                break;
            case 3:
                result=reportoperations.personsByAlpabhet(repository.getTeachers());
                break;
            case 4:
                result=reportoperations.personsByAlpabhet(repository.getPersons());
                break;
            case 5:
                result=reportoperations.personsByAlpabhet(crudoperations.findFaculty().getStudentsOfFaculty());
                break;
            case 6:
                result=reportoperations.personsByAlpabhet(crudoperations.findFaculty().getTeachersOfFaculty());
                break;
            case 7:
                result=reportoperations.personsByAlpabhet(crudoperations.findDepartment().getTeachersOfDepartment());
                break;
            case 8:
                result=reportoperations.personsByAlpabhet(crudoperations.findDepartment().getStudentsOfDepartment());
                break;
            case 9:
                result=reportoperations.studentByCourse(crudoperations.findDepartment().getStudentsOfDepartment());
                break;
            case 10:
                result=reportoperations.filterStudentsByCourse(repository.getStudents(),getIntInput("Оберіть курс:",1,4));
                break;
            case 11:
                result=reportoperations.filterStudentsByCourse(crudoperations.findDepartment().getStudentsOfDepartment(),getIntInput("Оберіть курс:",1,4));
                break;
        }
        if (!result.isEmpty())
        result.forEach(System.out::println);
        log.info(" finish work with reports operations");
    }


    private static void search() {
        log.debug("Search operations");
        System.out.println("Обрано пошук");
        int choosenSearchByPerson = getIntInput("Доступні операції: 1)Пошук Персони 2)Пошук Студента 3)Пошук Викладача", 1, 3);
        log.trace("choice {} was choosen",choosenSearchByPerson);
        List<? extends Person> humans;
        switch (choosenSearchByPerson) {
            case 1:
                humans = repository.getPersons();
                break;
            case 2:
                humans = repository.getStudents();
                break;
            default:
                humans = repository.getTeachers();
        }
        int choosenSearch;
        if (choosenSearchByPerson != 2)
            choosenSearch = getIntInput("Доступні операції: 1)Пошук за email 2)Пошук за ПІБ 3)Пошук за номером телефону", 1, 3);
        else
            choosenSearch = getIntInput("Доступні операції: 1)Пошук за email 2)Пошук за ПІБ 3)Пошук за номером телефону 4)Пошук за групою 5)Пошук за курсом", 1, 5);
        log.trace("choice {} was choosen",choosenSearch);
        switch (choosenSearch) {
            case 1:
                searchoperations.searchEmail(humans);
                break;
            case 2:
                searchoperations.searchName(humans);
                break;
            case 3:
                searchoperations.searchPhone(humans);
                break;
            case 4:
                searchoperations.searchGroup(repository);
                break;
            default:
                searchoperations.searchCourse(repository);
        }
        log.info(" finish work with search operations");
    }
    private static void backgroundSave() {
        Thread backgroundSaver=new Thread(()->{while (true){
            try {
                Thread.sleep(180000);
                log.info("Autosaving background saving");
                saveOperations.saveTempDatabase(repository, universityRepository);
            }catch (InterruptedException e){
                log.error("Thread interrupted");
            }
        }
        });
        backgroundSaver.setDaemon(true);
        backgroundSaver.start();

    }
}