package CLI;

import DigiPackage.*;

import java.util.List;
import java.util.Scanner;

import static CLI.InputHelper.getIntInput;

public class ConsoleProgram {

    static Scanner sc = new Scanner(System.in);
    static CRUDoperations crudoperations = new CRUDoperations();
    static SearchOperations searchoperations = new SearchOperations();
    static Reportoperations reportoperations = new Reportoperations();

    static Repository repository = new Repository();
    static UniversityRepository universityRepository = new UniversityRepository();
    static boolean isManager = false;

    public static void main(String[] args) {
        initTestData();
        run();
    }

    private static void initTestData() {
        try {
            Student student1 = new Student("001", "Сковорода Григорій Савич", "07.12.2005", "skovoroda@gmail.com", "+380000000001", "A 000/01 бп", 3, "ІПЗ-3", 2023, FormOfStudy.Budget, Status.Studying);
            Student student2 = new Student("002", "Колісник Денис Максимович", "02.10.2007", "kolisnyk@gmail.com", "+380633155000", "A 120/20 бп", 1, "ІПЗ-1", 2025, FormOfStudy.Budget, Status.Studying);
            repository.addStudent(student1);
            repository.addStudent(student2);

            Teacher teacher1 = new Teacher("D01", "Глибовець Андрій Миколайович", "25.10.1985", "a.glybovets@ukma.edu.ua", "+380444636985", Position.Dean, ScientificDegree.Doctor_of_science, AcademicTitle.None, "30.05.2019", 1.0, 1000);
            Teacher teacher2 = new Teacher("D02", "Малашонок Геннадій Іванович", "10.08.1985", "malashanok@ukma.edu.ua", "+380444257723", Position.Head_of_department, ScientificDegree.Doctor_of_science, AcademicTitle.None, "10.04.2015", 1.0, 600);
            Teacher teacher3 = new Teacher("D03", "Пєчкурова Олена Миколаївна", "01.01.1999", "pyechkurova@ukma.edu.ua", "+380441234567", Position.Senior_lecturer, ScientificDegree.None, AcademicTitle.Docent, "02.02.2002", 1.0, 500);
            repository.addTeacher(teacher1);
            repository.addTeacher(teacher2);
            repository.addTeacher(teacher3);

            Faculty faculty1 = new Faculty("F00", "Факультет Інформатики", "ФІ", teacher1, "https://www.fin.ukma.edu.ua/contacts");
            universityRepository.addFaculty(faculty1);

            Department department1 = new Department("D00", "Кафедра Мережних Технологій", faculty1, teacher2, "Кабінет: 1-204");
            universityRepository.addDepartment(department1);
        } catch (Exception e) {
            System.out.println("Помилка ініціалізації тестових даних: " + e.getMessage());
        }
    }

    private static void run() {
        System.out.println("Вітаємо в програмі DigiUni Registry(консольна інформаційна система університету)");
        authenticate();
        while (true) {
            mainMenu();
        }
    }

    private static void authenticate() {
        System.out.println("\n--- АВТОРИЗАЦІЯ ---");
        System.out.println("1) Увійти як Менеджер (повний доступ)");
        System.out.println("2) Увійти як Гість/Студент (тільки перегляд та пошук)");
        int roleChoice = getIntInput("Ваш вибір", 1, 2);

        if (roleChoice == 1) {
            int attempts = 0;
            while (attempts < 3) {
                System.out.print("Введіть пароль: ");
                String password = sc.nextLine();

                if (password.equals("admin")) {            // JUST SIMPLE PASSWORD; CAN BE CHANGED
                    isManager = true;
                    System.out.println("Успішно! Ви увійшли як Менеджер.");
                    return;
                } else {
                    attempts++;
                    System.out.println("Невірний пароль. Залишилось спроб: " + (3 - attempts));
                }
            }
            System.out.println("Вичерпано ліміт спроб. Вас автоматично авторизовано як Гостя.");
            isManager = false;

        } else {
            isManager = false;
            System.out.println("Ви увійшли як Гість.");
        }
    }

    private static void mainMenu() {
        System.out.println("Оберіть з чим бажаєте працювати: 1)Університет 2)Факультет 3)Кафедра 4)Викладач 5)Студент 6)Пошуки 7)Сортування");
        int choice = getIntInput("Оберіть операцію", 1, 7);
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

        }
    }

    private static int chooseOperation() {
        if (isManager) {
            System.out.println("1. Створити");
            System.out.println("2. Редагувати");
            System.out.println("3. Видалити");
        }
        System.out.println("4. Читати");
        System.out.println("0. Назад");

        while (true) {
            int choice = getIntInput("Ваш вибір", 0, 4);
            if (!isManager && (choice == 1 || choice == 2 || choice == 3)) {
                System.out.println("Помилка: У вас немає доступу до цієї операції.(Тільки для менеджера)");
            } else {
                return choice;
            }
        }
    }

    private static void workWithTeacher() {
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
    }

    private static void workWithStudent() {
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
    }

    private static void workWithFaculty() {
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
    }

    private static void workWithUniversity() {
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

    }

    private static void workWithDepartment() {
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

    }




    private static void reports() {
        System.out.println("Оберіть звіт: 1)Студенти за курсом 2)Назад");
        int choice = getIntInput("Ваш вибір", 1, 2);
        if (choice == 1) {
            reportoperations.studentByCourse(repository.getStudents());
        }
    }


    private static void search() {
        System.out.println("Обрано пошук");
        int choosenSearchByPerson = getIntInput("Доступні операції: 1)Пошук Персони 2)Пошук Студента 3)Пошук Викладача", 1, 3);
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
            choosenSearch = getIntInput("Доступні операції: 1)Пошук за email 2)Пошук за ПІБ 3)Пошук за номером телефону", 1, 4);
        else
            choosenSearch = getIntInput("Доступні операції: 1)Пошук за email 2)Пошук за ПІБ 3)Пошук за номером телефону 4)Пошук за групою 5)Пошук за курсом", 1, 6);
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
                searchoperations.searchById(humans);
                break;
            case 5:
                searchoperations.searchGroup(repository);
                break;
            default:
                searchoperations.searchCourse(repository);
        }

    }

}