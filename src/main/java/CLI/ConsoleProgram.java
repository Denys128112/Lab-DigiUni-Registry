package CLI;
import DigiPackage.*;

import java.util.List;
import java.util.Scanner;

public class ConsoleProgram {

    static Scanner sc = new Scanner(System.in);
    static CRUDoperations crudoperations = new CRUDoperations();
    static SearchOperations searchoperations = new SearchOperations();
     static Reportoperations reportoperations= new  Reportoperations();
     static Repository repository = new Repository();

    public static void main(String[] args) {
        run();
    }


    private static void run() {
        System.out.println("Вітаємо в програмі DigiUni Registry(консольна інформаційна система університету)");
        while (true) {
            mainMenu();
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

    private static void workWithTeacher() {
    }
    private static void workWithStudent() {
    }
    private static void workWithFaculty() {
    }

    private static void workWithUniversity() {

    }
    private static void  workWithDepartment() {

    }

    private static void crud() {
        while (true) {
            System.out.println("1. Створити");
            System.out.println("2. Редагувати");
            System.out.println("3. Видалити");
            System.out.println("4. Читати");
            System.out.println("0. Назад");

            int operation = getIntInput("Ваш вибір", 0, 4);
            if (operation == 0) return;

            System.out.println("\n--- ОБЕРІТЬ СУТНІСТЬ ---");
            System.out.println("1. Факультет");
            System.out.println("2. Кафедра");
            System.out.println("3. Студент");
            System.out.println("4. Викладач");
            int entity;
            if (operation == 4) {
                System.out.println("5. Університет");
                entity = getIntInput("Ваш вибір", 1, 5);
            } else {
                entity = getIntInput("Ваш вибір", 1, 4);
            }

            switch (operation) {
                case 1:
                    if (entity == 1) crudoperations.createFaculty();
                    else if (entity == 2) crudoperations.createDepartment();
                    else if (entity == 3) crudoperations.createStudent();
                    else if (entity == 4) crudoperations.createTeacher();
                    break;
                case 2:
                    if (entity == 1) crudoperations.updateFaculty();
                    else if (entity == 2) crudoperations.updateDepartment();
                    else if (entity == 3) crudoperations.updateStudent();
                    else if (entity == 4) crudoperations.updateTeacher();
                    break;
                case 3:
                    if (entity == 1) crudoperations.deleteFaculty();
                    else if (entity == 2) crudoperations.deleteDepartment();
                    else if (entity == 3) crudoperations.deleteStudent();
                    else if (entity == 4) crudoperations.deleteTeacher();
                    break;
                case 4:
                    if (entity == 1) crudoperations.readFaculty();
                    else if (entity == 2) crudoperations.readDepartment();
                    else if (entity == 3) crudoperations.readStudent();
                    else if (entity == 4) crudoperations.readTeacher();
                    else if (entity == 5) crudoperations.getUniversity();
                    break;
            }
        }
    }

    public static int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Помилка: Число має бути від " + min + " до " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Це не число. Введіть цифру.");
            }
        }
    }

    private static void reports() {
        System.out.println("Оберіть звіт: 1)Студенти за групою 2)In Progress3");
        System.out.println("Поки буде викликатися перший звіт");
        reportoperations.studentByCourse();
    }




    private static void search() {
        System.out.println("Обрано пошук");
        int choosenSearchByPerson = getIntInput("Доступні операції: 1)Пошук Персони 2)Пошук Студента 3)Пошук Викладача",1,3);
        List<? extends Person> humans;
        switch (choosenSearchByPerson) {
            case 1:
                humans= repository.getPersons();
                break;
            case 2:
                humans= repository.getStudents();
                break;
            default:
                humans= repository.getTeachers();
        }

    }

}