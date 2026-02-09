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
            System.out.println("1. Створити");
            System.out.println("2. Редагувати");
            System.out.println("3. Видалити");
            System.out.println("0. Назад");

            int operation = getIntInput("Ваш вибір", 0, 3);
            if (operation == 0) return;

            System.out.println("\n--- ОБЕРІТЬ СУТНІСТЬ ---");
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
        System.out.println("\n--- СТВОРЕННЯ ФАКУЛЬТЕТУ ---");

        Teacher dean = null;
        if (teachers[0] != null) {
            dean = teachers[0];
        } else {
            System.out.println("Помилка: Немає викладачів. Спочатку створіть викладача.");
            return;
        }

        String code = getValidatedInput("Введіть код", "^[A-Za-z0-9]+$", "Код має містити тільки літери та цифри (наприклад: FI)");
        String name = getValidatedInput("Введіть назву", "^[A-Za-zAz-яА-ЯІіЇїЄєҐґ'\\s\\-]+$", "Назва має містити літери (наприклад: Факультет Інформатики)");
        String shortName = getValidatedInput("Введіть абревіатуру", "^[A-Za-zAz-яА-ЯІіЇїЄєҐґ]+$", "Абревіатура має бути без пробілів (наприклад: ФІ)");
        String contacts = getValidatedInput("Введіть контакти", "^.+$", "Контакти не можуть бути порожніми");

        try {
            Faculty f = new Faculty(code, name, shortName, dean, contacts);
            service.addFaculty(f);
            System.out.println("Факультет успішно створено");
        } catch (Exception e) {
            System.out.println("Помилка створення: " + e.getMessage());
        }
    }

    private static void createDepartment() {
        System.out.println("\n--- СТВОРЕННЯ КАФЕДРИ --");

        Teacher head = null;
        if (teachers[0] != null) {
            head = teachers[0];
        } else {
            System.out.println("Помилка: Немає викладачів.");
            return;
        }

        String fCode = "";
        while (true) {
            fCode = getValidatedInput("Введіть код факультету", "^[A-Za-z0-9]+$", "Код має містити тільки літери та цифри");
            if (service.getFacultyByCode(fCode) != null) break;
            System.out.println("Факультет з таким кодом не знайдено. Спробуйте ще раз.");
        }
        Faculty f = service.getFacultyByCode(fCode);

        String code = getValidatedInput("Введіть код кафедри", "^[A-Za-z0-9]+$", "Код має містити тільки літери та цифри (наприклад: MATH)");
        String name = getValidatedInput("Введіть назву", "^[A-Za-zAz-яА-ЯІіЇїЄєҐґ'\\s\\-]+$", "Назва має містити літери");
        String loc = getValidatedInput("Введіть локацію", "^.+$", "Локація не може бути порожньою");

        try {
            Department d = new Department(code, name, f, head, loc);
            service.addDepartment(d);
            System.out.println("Кафедру успішно створено");
        } catch (Exception e) {
            System.out.println("Помилка створення: " + e.getMessage());
        }
    }

    private static void createStudent() {
        System.out.println("\n--- ДОДАВАННЯ СТУДЕНТА ---");

        String id = getValidatedInput("Введіть ID", "^[A-Za-z0-9]+$", "ID має містити літери або цифри");
        String name = getValidatedInput("Введіть ПІБ", "^[A-Za-zAz-яА-ЯІіЇїЄєҐґ']+\\s[A-Za-zAz-яА-ЯІіЇїЄєҐґ']+\\s[A-Za-zAz-яА-ЯІіЇїЄєҐґ']+$", "ПІБ має складатись з 3 слів через пробіл (Прізвище Ім'я По-батькові)");
        String dob = getValidatedInput("Введіть дату народження", "^\\d{2}\\.\\d{2}\\.\\d{4}$", "Формат дати має бути дд.мм.рррр (наприклад 01.01.2005)");
        String email = getValidatedInput("Введіть Email", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "Некоректний формат email");
        String phone = getValidatedInput("Введіть телефон", "^\\+?\\d{10,13}$", "Телефон має містити цифри (наприклад +380991234567)");
        String rb = getValidatedInput("Введіть номер залікової", "^.{11}$", "Номер залікової має містити рівно 11 символів (наприклад: А 1234/56 бп)");

        int course = getIntInput("Введіть курс (1-6)", 1, 6);
        String group = getValidatedInput("Введіть групу", "^[A-Za-zА-Яа-я]+-\\d+$", "Група має бути у форматі Літери-Цифра (наприклад IPZ-1)");
        int year = getIntInput("Введіть рік вступу (2019-2026)", 2019, 2026);

        System.out.println("Оберіть форму навчання: 1. Бюджет 2. Контракт");
        int fChoice = getIntInput("Ваш вибір", 1, 2);
        FormOfStudy form = (fChoice == 1) ? FormOfStudy.Budget : FormOfStudy.Contract;

        System.out.println("Оберіть статус: 1. Навчається 2. Академ 3. Відрахований");
        int sChoice = getIntInput("Ваш вибір", 1, 3);
        Status status = Status.Studying;
        if (sChoice == 2) status = Status.Academic_vacation;
        if (sChoice == 3) status = Status.Expelled;

        try {
            Student st = new Student(id, name, dob, email, phone, rb, course, group, year, form, status);

            boolean added = false;
            for (int i = 0; i < student.length; i++) {
                if (student[i] == null) {
                    student[i] = st;
                    added = true;
                    System.out.println("Студента успішно додано");
                    break;
                }
            }
            if (!added) System.out.println("Помилка: Масив студентів переповнений");

        } catch (IllegalArgumentException e) {
            System.out.println("Помилка логіки сутності: " + e.getMessage());
        }
    }

    private static void createTeacher() {
        System.out.println("\n--- ДОДАВАННЯ ВИКЛАДАЧА ---");

        String id = getValidatedInput("Введіть ID", "^[A-Za-z0-9]+$", "ID має містити літери або цифри");
        String name = getValidatedInput("Введіть ПІБ", "^[A-Za-zAz-яА-ЯІіЇїЄєҐґ']+\\s[A-Za-zAz-яА-ЯІіЇїЄєҐґ']+\\s[A-Za-zAz-яА-ЯІіЇїЄєҐґ']+$", "ПІБ має складатись з 3 слів через пробіл");
        String dob = getValidatedInput("Введіть дату народження", "^\\d{2}\\.\\d{2}\\.\\d{4}$", "Формат дати має бути дд.мм.рррр");
        String email = getValidatedInput("Введіть Email", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "Некоректний формат email");
        String phone = getValidatedInput("Введіть телефон", "^\\+?\\d{10,13}$", "Телефон має містити цифри");

        System.out.println("Посада: 1. Асистент 2. Доцент");
        int p = getIntInput("Ваш вибір", 1, 2);
        Position position = (p == 1) ? Position.Assistant : Position.Docent;

        System.out.println("Науковий ступінь: 1. Немає 2. Кандидат наук (PHD)");
        int d = getIntInput("Ваш вибір", 1, 2);
        ScientificDegree degree = (d == 1) ? ScientificDegree.None : ScientificDegree.PHD;

        System.out.println("Вчене звання: 1. Немає 2. Доцент");
        int t = getIntInput("Ваш вибір", 1, 2);
        AcademicTitle title = (t == 1) ? AcademicTitle.None : AcademicTitle.Docent;

        String dateEnter = getValidatedInput("Введіть дату прийняття на роботу", "^\\d{2}\\.\\d{2}\\.\\d{4}$", "Формат дати має бути дд.мм.рррр");

        double rate = 1.0;
        int workload = 600;

        try {
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
                    break;
                }
            }
            if (!added) System.out.println("Помилка: Масив викладачів переповнений");

        } catch (IllegalArgumentException e) {
            System.out.println("Помилка логіки сутності: " + e.getMessage());
        }
    }

    private static void updateFaculty() {
        String code = getValidatedInput("Введіть код факультету для редагування", "^.+$", "Код не може бути порожнім");
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
        String code = getValidatedInput("Введіть код факультету для видалення", "^.+$", "Код не може бути порожнім");
        if (service.removeFaculty(code)) {
            System.out.println("Факультет видалено");
        } else {
            System.out.println("Помилка: Факультет не знайдено");
        }
    }

    private static void updateDepartment() {
        String code = getValidatedInput("Введіть код кафедри для редагування", "^.+$", "Код не може бути порожнім");
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
        String code = getValidatedInput("Введіть код кафедри для видалення", "^.+$", "Код не може бути порожнім");
        if (service.removeDepartment(code)) {
            System.out.println("Кафедру видалено");
        } else {
            System.out.println("Помилка: Кафедру не знайдено");
        }
    }

    private static String getValidatedInput(String prompt, String regex, String errorMessage) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine();
            if (input.matches(regex)) {
                return input;
            } else {
                System.out.println("Помилка формату: " + errorMessage);
            }
        }
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

