package CLI;
import DigiPackage.*;
import java.util.Optional;
import java.util.Scanner;


public class ConsoleProgram implements Searching{

    static Scanner sc = new Scanner(System.in);
    static Student[] student =new Student[3];
    static Person[] persons=new Person[6];
    static Teacher[] teachers=new Teacher[3];
    static UniversityService service = new UniversityService();
    public static void main(String[] args) {
        run();
    }

    private static void run() {
        System.out.println("Вітаємо в програмі DigiUni Registry(консольна інформаційна система університету)");
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
        while (true) {
            System.out.println("--- CRUD ОПЕРАЦІЇ ---");
            System.out.println("1. Створити");
            System.out.println("2. Редагувати");
            System.out.println("3. Видалити");
            System.out.println("0. Назад");

            int operation = getIntInput("Ваш вибір", 0, 3);
            if (operation == 0) return;

            System.out.println("--- ОБЕРІТЬ СУТНІСТЬ ---");
            System.out.println("1. Факультет");
            System.out.println("2. Кафедра");
            System.out.println("3. Студент");
            System.out.println("4. Викладач");

            int entity = getIntInput("Ваш вибір", 1, 4);

            switch (operation) {
                case 1:
                    if (entity == 1) createFaculty();
                    else if (entity == 2) createDepartment();
                    else if (entity == 3) createStudent();
                    else if (entity == 4) createTeacher();
                    break;
                case 2:
                    if (entity == 1) updateFaculty();
                    else if (entity == 2) updateDepartment();
                    else System.out.println("Редагування персон у розробці");
                    break;
                case 3:
                    if (entity == 1) deleteFaculty();
                    else if (entity == 2) deleteDepartment();
                    else System.out.println("Видалення персон у розробці");
                    break;
            }
        }
    }

    private static void createFaculty() {
        System.out.println("--- СТВОРЕННЯ ФАКУЛЬТЕТУ ---");

        Teacher dean = null;
        if (teachers[0] != null) {
            dean = teachers[0];
        } else {
            System.out.println("Помилка: Немає викладачів. Спочатку створіть викладача.");
            return;
        }

        while (true) {
            try {
                String code = getStringInput("Введіть код");
                String name = getStringInput("Введіть назву");
                String shortName = getStringInput("Введіть абревіатуру");
                String contacts = getStringInput("Введіть контакти");

                Faculty f = new Faculty(code, name, shortName, dean, contacts);
                service.addFaculty(f);
                System.out.println("Факультет успішно створено");
                return;
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
                System.out.println("Спробуйте ще раз.");
            }
        }
    }

    private static void createDepartment() {
        System.out.println("--- СТВОРЕННЯ КАФЕДРИ ---");

        Teacher head = null;
        if (teachers[0] != null) {
            head = teachers[0];
        } else {
            System.out.println("Помилка: Немає викладачів.");
            return;
        }

        while (true) {
            try {
                String fCode = getStringInput("Введіть код факультету");
                Faculty f = service.getFacultyByCode(fCode);
                if (f == null) {
                    System.out.println("Помилка: Факультет не знайдено.");
                    continue;
                }

                String code = getStringInput("Введіть код кафедри");
                String name = getStringInput("Введіть назву");
                String loc = getStringInput("Введіть локацію");

                Department d = new Department(code, name, f, head, loc);
                service.addDepartment(d);
                System.out.println("Кафедру успішно створено");
                return;
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
                System.out.println("Спробуйте ще раз.");
            }
        }
    }

    private static void createStudent() {
        System.out.println("--- ДОДАВАННЯ СТУДЕНТА ---");

        while (true) {
            try {
                String id = getStringInput("Введіть ID");
                String name = getStringInput("Введіть ПІБ");
                String dob = getStringInput("Введіть дату народження");
                String email = getStringInput("Введіть Email");
                String phone = getStringInput("Введіть телефон");
                String rb = getStringInput("Введіть номер залікової");

                int course = getIntInput("Введіть курс (1-6)", 1, 6);
                String group = getStringInput("Введіть групу");
                int year = getIntInput("Введіть рік вступу", 1900, 2100);

                System.out.println("Оберіть форму навчання: 1. Бюджет 2. Контракт");
                int fChoice = getIntInput("Ваш вибір", 1, 2);
                FormOfStudy form = (fChoice == 1) ? FormOfStudy.Budget : FormOfStudy.Contract;

                System.out.println("Оберіть статус: 1. Навчається 2. Академ 3. Відрахований");
                int sChoice = getIntInput("Ваш вибір", 1, 3);
                Status status = Status.Studying;
                if (sChoice == 2) status = Status.Academic_vacation;
                if (sChoice == 3) status = Status.Expelled;

                Student st = new Student(id, name, dob, email, phone, rb, course, group, year, form, status);

                boolean added = false;
                for (int i = 0; i < student.length; i++) {
                    if (student[i] == null) {
                        student[i] = st;
                        added = true;
                        System.out.println("Студента успішно додано");
                        return;
                    }
                }
                if (!added) {
                    System.out.println("Помилка: Масив студентів переповнений");
                    return;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
                System.out.println("Спробуйте ще раз ввести коректні дані.");
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void createTeacher() {
        System.out.println("--- ДОДАВАННЯ ВИКЛАДАЧА ---");

        while (true) {
            try {
                String id = getStringInput("Введіть ID");
                String name = getStringInput("Введіть ПІБ");
                String dob = getStringInput("Введіть дату народження");
                String email = getStringInput("Введіть Email");
                String phone = getStringInput("Введіть телефон");

                System.out.println("Посада: 1. Асистент 2. Доцент");
                int p = getIntInput("Ваш вибір", 1, 2);
                Position position = (p == 1) ? Position.Assistant : Position.Docent;

                System.out.println("Науковий ступінь: 1. Немає 2. Кандидат наук (PHD)");
                int d = getIntInput("Ваш вибір", 1, 2);
                ScientificDegree degree = (d == 1) ? ScientificDegree.None : ScientificDegree.PHD;

                System.out.println("Вчене звання: 1. Немає 2. Доцент");
                int t = getIntInput("Ваш вибір", 1, 2);
                AcademicTitle title = (t == 1) ? AcademicTitle.None : AcademicTitle.Docent;

                String dateEnter = getStringInput("Введіть дату прийняття на роботу");

                double rate = 1.0;
                int workload = 600;

                Teacher teacher = new Teacher(id, name, dob, email, phone, position, degree, title, dateEnter, rate, workload);

                boolean added = false;
                for (int i = 0; i < teachers.length; i++) {
                    if (teachers[i] == null) {
                        teachers[i] = teacher;
                        for (int j = 0; j < persons.length; j++) {
                            if (persons[j] == null) {
                                persons[j] = teacher;
                                break;
                            }
                        }
                        added = true;
                        System.out.println("Викладача успішно додано");
                        return;
                    }
                }
                if (!added) {
                    System.out.println("Помилка: Масив викладачів переповнений");
                    return;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
                System.out.println("Спробуйте ще раз ввести коректні дані.");
            }
        }
    }

    private static void updateFaculty() {
        String code = getStringInput("Введіть код факультету для редагування");
        Faculty f = service.getFacultyByCode(code);
        if (f != null) {
            System.out.println("Знайдено: " + f);
            System.out.println("Введіть нові значення (або натисніть Enter, щоб залишити старі):");

            System.out.print("Нова назва: ");
            String name = sc.nextLine();
            if (!name.isEmpty()) f.setName(name);

            System.out.print("Нові контакти: ");
            String contacts = sc.nextLine();
            if (!contacts.isEmpty()) f.setContacts(contacts);

            System.out.println("Успішно оновлено");
        } else {
            System.out.println("Помилка: Факультет не знайдено");
        }
    }

    private static void deleteFaculty() {
        String code = getStringInput("Введіть код факультету для видалення");
        if (service.removeFaculty(code)) {
            System.out.println("Факультет видалено");
        } else {
            System.out.println("Помилка: Факультет не знайдено");
        }
    }

    private static void updateDepartment() {
        String code = getStringInput("Введіть код кафедри для редагування");
        Department d = service.getDepartmentByCode(code);
        if (d != null) {
            System.out.println("Знайдено: " + d);
            System.out.print("Нова назва (або натисніть Enter, щоб залишити стару): ");
            String name = sc.nextLine();
            if (!name.isEmpty()) d.setName(name);
            System.out.println("Успішно оновлено");
        } else {
            System.out.println("Помилка: Кафедру не знайдено");
        }
    }

    private static void deleteDepartment() {
        String code = getStringInput("Введіть код кафедри для видалення");
        if (service.removeDepartment(code)) {
            System.out.println("Кафедру видалено");
        } else {
            System.out.println("Помилка: Кафедру не знайдено");
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine();
    }

    private static int getIntInput(String prompt, int min, int max) {
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

        System.out.println("Введіть ПІБ у форматі ПРІЗВИЩЕ ІМ'Я ПО-БАТЬКОВІ");
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

