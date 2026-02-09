package CLI;
import DigiPackage.*;
import java.util.Optional;
import java.util.Scanner;


public class ConsoleProgram implements Searching{

    static Scanner sc = new Scanner(System.in);
    static Student[] student =new Student[3];
    static Person[] persons=new Person[6];
    static Teacher[] teachers=new Teacher[3];
    public static void main(String[] args) {
        run();
    }

    private static void run() {
        System.out.println("Вітаємо в програмі DigiUni Registry(консольна інформаційна систему університету)");
        while(true){
        mainMenu();}
    }
    private static void mainMenu() {
        System.out.println("Доступні операції 1)CRUD операції 2)Пошуки 3)Звіти");
        System.out.println("Оберіть операцію:");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                crud();
                break;
            case 2:
                search();
                break;
            case 3:
                reports();
                break;
            default:
                throw new IllegalArgumentException("Обрана операція некоректна");
        }
    }

    private static void crud() {
    }

    private static void reports() {
        System.out.println("Оберіть звіт: 1)Студенти за групою 2)In Progress3");
        System.out.println("Поки буде викликатися перший звіт");
        studentByCourse();
    }

    private static void studentByCourse() {
        if (student == null) return;
        int n =student.length;
        for (int i = 0; i < n - 1; i++){
            for (int j = 0; j < n - i - 1; j++){
                if(student[j]!=null && student[j+1]!=null) {
                    if (student[j].getCourse() > student[j + 1].getCourse()) {
                        Student temp = student[j];
                        student[j] = student[j + 1];
                        student[j + 1] = temp;
                    }
                } else if (student[j]==null && student[j+1]!=null) {
                    Student temp = student[j];
                    student[j] = student[j + 1];
                    student[j + 1] = temp;
                }
            }
        }
        int i=1;
        for(Student s: student){
            if(s!=null){
                System.out.println(i+") "+s);
                i++;
            }
        }
        }


    private static void search() {
        System.out.println("Обрано пошук");
        System.out.println("Доступні операції: 1)Пошук Персони 2)Пошук Студента 3)Пошук Викладача");
        int choosenSearchByPerson = sc.nextInt();
        if (choosenSearchByPerson == 2) {
            System.out.println("1)Пошук за ПІБ 2)За email 3)За номером телефону 4)За курсом 5)За групою");
            int choosenOperation = sc.nextInt();
            sc.nextLine();
            if (choosenOperation>=1 && choosenOperation<=5){
                switch (choosenOperation) {
                    case 1:
                        searchName(student);
                        break;
                    case 2:
                        searchEmail(student);
                        break;
                    case 3:
                        searchPhone(student);
                        break;
                    case 4:
                        searchCourse();
                        break;
                    case 5:
                        searchGroup();
                        break;
                    default:
                        System.out.println("Некоректна операція");
                }
            }
        }
        else if (choosenSearchByPerson == 3||choosenSearchByPerson == 1) {
            Person[] humans;
            if(choosenSearchByPerson == 3)
                humans=teachers;
            else humans=persons;
            System.out.println("1)Пошук за ПІБ 2)За email 3)За номером телефону");
            int choosenOperation = sc.nextInt();
            sc.nextLine();
            if (choosenOperation>=1 && choosenOperation<=5){
                switch (choosenOperation) {
                    case 1:
                        searchName(humans);
                        break;
                    case 2:
                        searchEmail(humans);
                        break;
                    case 3:
                        searchPhone(humans);
                        break;
                }}
        }

        else
            throw new IllegalArgumentException("Неправильно обраний вид пошуку");
    }



    private static void searchName(Person[] humans) {

        System.out.println("Введіть ПІБ у форматі ПРІЗВИЩЕ ІМ'Я ПОБАТЬКОВІ");
        String name = sc.nextLine();

        Optional<Person> maybePerson = Searching.findByName(name, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new IllegalArgumentException("User not found"));
            System.out.println(person);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
        private static void searchEmail(Person[] humans) {
            System.out.println("Введіть email:");
            String email = sc.nextLine();

            Optional<Person> maybePerson = Searching.findByEmail(email, humans);
            try {
                Person person = maybePerson.orElseThrow(() -> new IllegalArgumentException("Користувача з таким email не знайдено"));
                System.out.println(person);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    private static void searchPhone(Person[] humans) {
        System.out.println("Введіть номер телефону без +380:");
        String phone = "+380"+sc.nextLine();

        Optional<Person> maybePerson = Searching.findByPhone(phone, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new IllegalArgumentException("Користувача з таким телефоном не знайдено"));
            System.out.println(person);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void searchGroup() {
        System.out.println("Введіть групу(наприклад ІПЗ-1):");
        String group = sc.nextLine();

        Student[] maybeStudents = Searching.findByGroup(group, student);
        try{
        if (maybeStudents[0] == null) {
            throw new IllegalArgumentException("На групі " + group + " студентів не знайдено або введенні дані не коректні");
        }
        System.out.println("На групі " + group + " студентів знайдено:");
        for (Student s : maybeStudents) {
            if (s != null) System.out.println(s);
        }
    } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }
    }
    private static void searchCourse() {
        System.out.println("Введіть курс:");
        int course = sc.nextInt();
        Student[] maybeStudents = Searching.findByCourse(course, student);
        try{
            if (maybeStudents[0] == null) {
                throw new IllegalArgumentException("На " + course + " курсі студентів не знайдено або введенні дані не коректні");
            }
            System.out.println("На  " + course + " курсі студентів знайдено:");
            for (Student s : maybeStudents) {
                if (s != null) System.out.println(s);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    }

