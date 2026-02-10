package CLI;
import DigiPackage.*;
import java.util.Optional;
import java.util.Scanner;


public class ConsoleProgram implements Searching {

    static Scanner sc = new Scanner(System.in);
    static Student[] student = new Student[100];
    static Person[] persons = new Person[200];
    static Teacher[] teachers = new Teacher[100];
    static UniversityService service = new UniversityService();

    public static void main(String[] args) {
        initTestData(); // Ініціалізуємо дані перед запуском
        run();
    }

    private static void initTestData() {
        try {
            service.createUniversity("Національний університет «Києво-Могилянська академія»","НАУКМА","Київ"," вулиця Григорія Сковороди, 2, Київ, 04655");
            Student student1 = new Student("001","Сковорода Григорій Савич","07.12.2005","skovoroda@gmail.com","+380000000001","A 000/01 бп",3,"ІПЗ-3",2023,FormOfStudy.Budget,Status.Studying);
            Student student2 = new Student("002","Колісник Денис Максимович","02.10.2007","kolisnyk@gmail.com","+380633155000","A 120/20 бп",1,"ІПЗ-1",2025,FormOfStudy.Budget,Status.Studying);
            student[0]=student1;
            student[1]=student2;
            Teacher teacher1= new Teacher("D01","Глибовець Андрій Миколайович","25.10.1985","a.glybovets@ukma.edu.ua","+380444636985",Position.Dean,ScientificDegree.Doctor_of_science,AcademicTitle.None,"30.05.2019",1.0,1000);
            Teacher teacher2= new Teacher("D02","Малашонок Геннадій Іванович","10.08.1985","malashanok@ukma.edu.ua","+380444257723",Position.Head_of_department,ScientificDegree.Doctor_of_science,AcademicTitle.None,"10.04.2015",1.0,600);
            Teacher teacher3=new Teacher("D03","Пєчкурова Олена Миколаївна","01.01.1999","pyechkurova@ukma.edu.ua","+380441234567",Position.Senior_lecturer,ScientificDegree.None,AcademicTitle.Docent,"02.02.2002",1.0,500);
            teachers[0]=teacher1;
            teachers[1]=teacher2;
            teachers[2]=teacher3;
            Faculty faculty1=new Faculty("F00","Факультет Інформатики","ФІ",teacher1,"https://www.fin.ukma.edu.ua/contacts");
            service.addFaculty(faculty1);
            Department department1=new Department("D00","Кафедра Мережних Технологій",faculty1,teacher2,"Кабінет: 1-204");
            service.addDepartment(department1);
        } catch (Exception e) {
            System.out.println("Помилка ініціалізації даних: " + e.getMessage());
        }
    }

    private static void run() {
        System.out.println("Вітаємо в програмі DigiUni Registry(консольна інформаційна система університету)");
        while (true) {
            mainMenu();
        }
    }

    private static void mainMenu() {
        System.out.println("Доступні операції 1)CRUD операції 2)Пошуки 3)Звіти");
        int choice = getIntInput("Оберіть операцію",1,3);
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
            if (operation==4){
                System.out.println("5. Університет");
                entity = getIntInput("Ваш вибір", 1, 5);
            }
            else {
                entity = getIntInput("Ваш вибір", 1, 4);
            }

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
                    else if (entity == 3) updateStudent();
                    else if (entity == 4) updateTeacher();
                    break;
                case 3:
                    if (entity == 1) deleteFaculty();
                    else if (entity == 2) deleteDepartment();
                    else if (entity == 3) deleteStudent();
                    else if (entity == 4) deleteTeacher();
                    break;
                    case 4:
                        if (entity == 1) readFaculty();
                        else if (entity == 2) readDepartment();
                        else if (entity == 3) readStudent();
                        else if (entity == 4) readTeacher();
                        else if (entity == 5) service.getUniversity();
                        break;
            }
        }
    }

    private static void readFaculty() {
        System.out.println("\n--- СПИСОК ФАКУЛЬТЕТІВ ---");
        Faculty[] allFaculties = service.getFaculties();
        int count = 0;
        for (Faculty f : allFaculties) {
            if (f != null) {
                System.out.println((count + 1) + ". " + f);
                count++;
            }
        }
        if (count == 0) System.out.println("Факультетів поки не створено.");
    }

    private static void readDepartment() {
        System.out.println("\n--- СПИСОК КАФЕДР ---");
        Department[] allDepartments = service.getDepartments();
        int count = 0;
        for (Department d : allDepartments) {
            if (d != null) {
                System.out.println((count + 1) + ". " + d);
                count++;
            }
        }
        if (count == 0) System.out.println("Кафедр поки не створено.");
    }

    private static void readStudent() {
        System.out.println("\n--- СПИСОК СТУДЕНТІВ ---");
        int count = 0;
        for (Student s : student) {
            if (s != null) {
                System.out.println((count + 1) + ". " + s);
                count++;
            }
        }
        if (count == 0) System.out.println("Студентів у реєстрі немає.");
    }

    private static void readTeacher() {
        System.out.println("\n--- СПИСОК ВИКЛАДАЧІВ ---");
        int count = 0;
        for (Teacher t : teachers) {
            if (t != null) {
                System.out.println((count + 1) + ". " + t);
                count++;
            }
        }
        if (count == 0) System.out.println("Викладачів у реєстрі немає.");
    }

    private static Student findStudent() {
        System.out.println("\n--- ПОШУК СТУДЕНТА ---");
        System.out.println("1) За ПІБ 2) Обрати зі списку 0) Назад");
        int choice = getIntInput("Ваш вибір", 0, 2);

        if (choice == 1) {
            searchName(student);
        } else if (choice == 2) {
            return chooseStudent();
        }
        return null;
    }

    private static Student chooseStudent() {
        int count = 0;
        for (int i = 0; i < student.length; i++) {
            if (student[i] != null) {
                System.out.println((count + 1) + ") " + student[i].getName());
                count++;
            }
        }
        if (count == 0) return null;
        int choice = getIntInput("Оберіть номер", 1, count);
        int current = 0;
        for (Student s : student) {
            if (s != null) {
                if (current == choice - 1) return s;
                current++;
            }
        }
        return null;
    }

    private static Teacher findTeacher() {
        System.out.println("\n--- ПОШУК ВИКЛАДАЧА ---");
        System.out.println("1) За ПІБ 2) Обрати зі списку 0) Назад");
        int choice = getIntInput("Ваш вибір", 0, 2);

        if (choice == 1) {
          searchName(teachers);
        } else if (choice == 2) {
            return chooseTeacher();
        }
        return null;
    }
    private static void deleteStudent() {
        Student s = findStudent();

        if (s == null) {
            System.out.println("Операцію скасовано або студента не знайдено.");
            return;
        }
        System.out.println("Ви впевнені, що хочете видалити студента " + s.getName() + "?");
        int confirm = getIntInput("1 - Так, 0 - Ні", 0, 1);
        if (confirm == 1) {
            boolean foundInStudents = false;
            for (int i = 0; i < student.length; i++) {
                if (student[i] == s) {
                    for (int j = i; j < student.length - 1; j++) {
                        student[j] = student[j + 1];
                    }
                    student[student.length - 1] = null;
                    foundInStudents = true;
                    break;
                }
            }
            if (foundInStudents) {
                for (int i = 0; i < persons.length; i++) {
                    if (persons[i] == s) {
                        for (int j = i; j < persons.length - 1; j++) {
                            persons[j] = persons[j + 1];
                        }
                        persons[persons.length - 1] = null;
                        break;
                    }
                }
                System.out.println("Студента успішно видалено з усіх реєстрів.");
            }
        } else {
            System.out.println("Видалення скасовано.");
        }
    }

    private static void deleteTeacher() {
        Teacher t = findTeacher();
        if (t == null) return;
        if (getIntInput("Видалити викладача " + t.getName() + "? 1-Так, 0-Ні", 0, 1) == 1) {
            for (int i = 0; i < teachers.length; i++) {
                if (teachers[i] == t) {
                    for (int j = i; j < teachers.length - 1; j++) {
                        teachers[j] = teachers[j + 1];
                    }
                    teachers[teachers.length - 1] = null;
                    System.out.println("Викладача видалено.");
                    break;
                }
            }
            for (int i = 0; i < persons.length; i++) {
                if (persons[i] == t) {
                    for (int j = i; j < persons.length - 1; j++) {
                        persons[j] = persons[j + 1];
                    }
                    persons[persons.length - 1] = null;
                    break;
                }
            }
        }

    }

    private static void updateStudent() {
        Student st = findStudent();
        if (st == null) return;

        boolean updating = true;
        while (updating) {
            System.out.println("\n--- РЕДАГУВАННЯ: " + st.getName() + " ---");
            System.out.println("1) ПІБ 2) Курс 3) Група 4) Статус 0) Готово");
            int step = getIntInput("Оберіть поле", 0, 4);
            try {
                switch (step) {
                    case 1 :
                    System.out.print("Новий ПІБ: ");
                    st.setName(sc.nextLine());
                    break;
                    case 2 :
                        st.setCourse(getIntInput("Новий курс", 1, 6));
                        break;
                    case 3:
                        System.out.print("Нова група: ");
                    st.setGroup(sc.nextLine());
                    break;
                    case 4:
                        int s = getIntInput("1)Навчається 2)Академ 3)Відрахований", 1, 3);
                        st.setStudentStatus(s == 1 ? Status.Studying : (s == 2 ? Status.Academic_vacation : Status.Expelled));
                        break;
                    case 0:
                    updating = false;
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage()); }
        }
    }

    private static void updateTeacher() {
        Teacher t = findTeacher();
        if (t == null) return;

        boolean updating = true;
        while (updating) {
            System.out.println("\n--- РЕДАГУВАННЯ: " + t.getName() + " ---");
            System.out.println("1) Посада 2) Ставка 3) Навантаження 0) Готово");
            int step = getIntInput("Оберіть поле", 0, 3);
            try {
                switch (step) {
                    case 1:
                        int p = getIntInput("1)Асистент 2)Викладач 3)Доцент 4)Професор", 1, 4);
                        if (p == 1) t.setPosition(Position.Assistant);
                        else if (p == 2) t.setPosition(Position.Lecturer);
                        else if (p == 3) t.setPosition(Position.Docent);
                        else t.setPosition(Position.Profesor);
                        break;
                    case 2 :
                        t.setRate(Double.parseDouble(sc.nextLine()));
                        break;
                    case 3 :
                        t.setWorkload(Integer.parseInt(sc.nextLine()));
                        break;
                    case 0:
                        updating = false;
                }
            } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
        }
    }

    private static void createFaculty() {
        System.out.println("\n--- СТВОРЕННЯ ФАКУЛЬТЕТУ ---");
        Faculty f = new Faculty();
        fillFaculty(f);
            try {
                service.addFaculty(f);
                System.out.println( f.getName() + " успішно створений!");
            } catch (Exception e) {
                System.out.println("Помилка при збереженні: " + e.getMessage());
            }

    }

    private static void fillFaculty(Faculty f) {
        int step = 1;
        int totalSteps = 5;

        while (step <= totalSteps) {
            try {
                switch (step) {
                    case 1:
                        System.out.print("Введіть код факультету (напр. FI): ");
                        f.setCode(sc.nextLine());
                        step++;
                        break;
                    case 2:
                        System.out.print("Введіть повну назву: ");
                        f.setName(sc.nextLine());
                        step++;
                        break;
                    case 3:
                        System.out.print("Введіть абревіатуру (ФІ, ФПвН): ");
                        f.setShortName(sc.nextLine());
                        step++;
                        break;
                    case 4:
                        System.out.print("Введіть контакти: ");
                        f.setContacts(sc.nextLine());
                        step++;
                        break;
                    case 5:
                        if (teachers[0] != null) {
                            Teacher t = chooseTeacher();
                            f.setDean(t);
                            t.setPosition(Position.Dean);
                        }
                        else
                            System.out.println("Вам треба буде обрати Декана після створення вчителів");
                        step++;
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
        System.out.println("Дані факультету заповнено.");
    }

    private static Teacher chooseTeacher() {
        int i = 1;
        for (Teacher t : teachers) {
            if (t != null) {
                System.out.println(i + ")" + t.getName());
                i++;
            }
        }
        int choose = getIntInput(("Оберіть викладача від 1 до " + i), 1, (i));
        return teachers[choose - 1];
    }


    private static void createDepartment() {
        System.out.println("\n--- СТВОРЕННЯ КАФЕДРИ ---");
        if (service.getFaculties().length == 0 || service.getFaculties()[0] == null) {
            System.out.println("Спочатку створіть факультет.");
            return;
        }
        Department d = new Department();
        fillDepartmentData(d);
        try {
            service.addDepartment(d);
            System.out.println("Кафедра " + d.getName() + " успішно створена!");
        } catch (Exception e) {
            System.out.println("Помилка при збереженні: " + e.getMessage());
        }
    }

    private static void fillDepartmentData(Department d) {
        int step = 1;
        int totalSteps = 5;

        while (step <= totalSteps) {
            try {
                switch (step) {
                    case 1:
                        System.out.print("Введіть код кафедри (напр. K01): ");
                        d.setCode(sc.nextLine());
                        step++;
                        break;
                    case 2:
                        System.out.print("Введіть назву (напр. Кафедра інформатики): ");
                        d.setName(sc.nextLine());
                        step++;
                        break;
                    case 3:
                        System.out.println("Оберіть ФАКУЛЬТЕТ, до якого належить кафедра:");
                        d.setFaculty(chooseFaculty());
                        step++;
                        break;
                    case 4:
                        System.out.println("Оберіть ЗАВІДУВАЧА кафедри:");
                        if (teachers[0] != null) {
                            Teacher t = chooseTeacher();
                            d.setHead(t);
                            t.setPosition(Position.Head_of_department);
                        }
                        else System.out.println("Після створення треба буде додати завідувача");
                        step++;
                        break;
                    case 5:
                        System.out.print("Введіть локацію (корпус, аудиторія): ");
                        d.setLocation(sc.nextLine());
                        step++;
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
                System.out.println("Спробуйте ще раз.");
            }
        }
    }


    private static Faculty chooseFaculty() {
        Faculty[] allFaculties = service.getFaculties();
        int count = 0;
        for (Faculty f : allFaculties) {
            if (f != null) {
                System.out.println((count + 1) + ") " + f.getShortName());
                count++;
            }
        }
        int choice = getIntInput("Оберіть номер факультету", 1, count);
        return allFaculties[choice - 1];
    }


    private static void createStudent() {
        System.out.println("\n--- ДОДАВАННЯ СТУДЕНТА ---");
        Student st = new Student();
        fillStudentData(st);
        for (int i = 0; i < student.length; i++) {
            if (student[i] == null) {
                student[i] = st;
                break;
            }
        }
        for (int i = 0; i < persons.length; i++) {
            if (persons[i] == null) {
                persons[i] = st;
                break;
            }
        }
    }

    private static void fillStudentData(Student st) {
        int step = 1;
        int totalSteps = 9;
        while (step <= totalSteps) {
            try {
                switch (step) {
                    case 1:
                        System.out.print("Введіть ПІБ: ");
                        st.setName(sc.nextLine());
                        step++;
                        break;
                    case 2:
                        System.out.print("Введіть дату народження (дд.мм.рррр): ");
                        st.setDateOfBirth(sc.nextLine());
                        step++;
                        break;
                    case 3:
                        System.out.print("Введіть Email: ");
                        st.setEmail(sc.nextLine());
                        step++;
                        break;
                    case 4:
                        System.out.print("Введіть телефон (+380...): ");
                        st.setPhone(sc.nextLine());
                        step++;
                        break;
                    case 5:
                        System.out.print("Введіть номер заліковки (А 000/00 бп): ");
                        st.setIdOfRecordBook(sc.nextLine());
                        step++;
                        break;
                    case 6:
                        System.out.print("Введіть курс (1-6): ");
                        st.setCourse(Integer.parseInt(sc.nextLine()));
                        step++;
                        break;
                    case 7:
                        System.out.print("Введіть групу (Напр. КІ-1): ");
                        st.setGroup(sc.nextLine());
                        step++;
                        break;
                    case 8:
                        int form = getIntInput("Оберіть форму навчання 1)Контракт 2)Бюджет: ", 1, 2);
                        if (form == 1) st.setFormOfStudy(FormOfStudy.Budget);
                        else st.setFormOfStudy(FormOfStudy.Contract);
                        step++;
                        break;
                    case 9:
                        int status = getIntInput("Оберіть форму навчання 1)Навчається 2)Академ відпустка 3)Відрахований", 1, 3);
                        if (status == 1) st.setStudentStatus(Status.Studying);
                        else if (status == 2) st.setStudentStatus(Status.Academic_vacation);
                        else st.setStudentStatus(Status.Expelled);
                        step++;
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
                System.out.println("Спробуйте ще раз.");
            }
            System.out.println("Студент був заповненний");
        }
    }

    private static void createTeacher() {
        System.out.println("\n--- ДОДАВАННЯ ВИКЛАДАЧА ---");
        Teacher t = new Teacher();
        fillTeacherData(t);

        boolean added = false;
        for (int i = 0; i < teachers.length; i++) {
            if (teachers[i] == null) {
                teachers[i] = t;
                added = true;
                break;
            }
        }
        for (int i = 0; i < persons.length; i++) {
            if (persons[i] == null) {
                persons[i] = t;
                break;
            }
        }
        if (added) {
            System.out.println("Викладача успішно збережено.");
        } else {
            System.out.println("Помилка: Немає вільного місця в базі викладачів.");
        }
    }

    private static void fillTeacherData(Teacher t) {
        int step = 1;
        int totalSteps = 11;
        while (step <= totalSteps) {
            try {
                switch (step) {
                    case 1:
                        System.out.print("Введіть ID: ");
                        t.setId(sc.nextLine());
                        step++;
                        break;
                    case 2:
                        System.out.print("Введіть ПІБ: ");
                        t.setName(sc.nextLine());
                        step++;
                        break;
                    case 3:
                        System.out.print("Введіть дату народження (дд.мм.рррр): ");
                        t.setDateOfBirth(sc.nextLine());
                        step++;
                        break;
                    case 4:
                        System.out.print("Введіть Email: ");
                        t.setEmail(sc.nextLine());
                        step++;
                        break;
                    case 5:
                        System.out.print("Введіть телефон (+380...): ");
                        t.setPhone(sc.nextLine());
                        step++;
                        break;
                    case 6:
                        System.out.println("Оберіть посаду: 1)Асистент 2)Викладач 3)Доцент 4)Професор 5)Старший викладач 6)Завкафедри 7)Декан");
                        int p = getIntInput("Ваш вибір", 1, 7);
                        if (p == 1) t.setPosition(Position.Assistant);
                        else if (p == 2) t.setPosition(Position.Lecturer);
                        else if (p == 3) t.setPosition(Position.Docent);
                        else if (p == 4) t.setPosition(Position.Profesor);
                        else if (p == 5) t.setPosition(Position.Senior_lecturer);
                        else if (p == 6) t.setPosition(Position.Head_of_department);
                        else t.setPosition(Position.Dean);
                        step++;
                        break;
                    case 7:
                        System.out.println("Науковий ступінь: 1)PHD 2)Кандидат наук 3)Доктор наук 4)Немає");
                        int d = getIntInput("Ваш вибір", 1, 4);
                        if (d == 1) t.setScientificDegree(ScientificDegree.PHD);
                        else if (d == 2) t.setScientificDegree(ScientificDegree.Candidate);
                        else if (d == 3) t.setScientificDegree(ScientificDegree.Doctor_of_science);
                        else t.setScientificDegree(ScientificDegree.None);
                        step++;
                        break;
                    case 8:
                        System.out.println("Вчене звання: 1)Професор 2)Доцент 3)Старший дослідник 4)Немає");
                        int title = getIntInput("Ваш вибір", 1, 4);
                        if (title == 1) t.setAcademicTitle(AcademicTitle.Profesor);
                        else if (title == 2) t.setAcademicTitle(AcademicTitle.Docent);
                        else if (title == 3) t.setAcademicTitle(AcademicTitle.Senior_researcher);
                        else t.setAcademicTitle(AcademicTitle.None);
                        step++;
                        break;
                    case 9:
                        System.out.print("Введіть дату прийняття на роботу (дд.мм.рррр): ");
                        t.setDateOfentering(sc.nextLine());
                        step++;
                        break;
                    case 10:
                        System.out.print("Введіть ставку (0.25 - 1.5): ");
                        t.setRate(Double.parseDouble(sc.nextLine()));
                        step++;
                        break;
                    case 11:
                        System.out.print("Введіть навантаження (0 - 1000 годин): ");
                        t.setWorkload(Integer.parseInt(sc.nextLine()));
                        step++;
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
                System.out.println("Спробуйте ще раз.");
            }
        }
        System.out.println("Викладач був заповнений");
    }
    private static Faculty findFaculty() {
        System.out.println("\n--- ПОШУК ФАКУЛЬТЕТУ ---");
        System.out.println("1) За кодом 2) Зі списку 0) Назад");
        int choice = getIntInput("Вибір", 0, 2);

        if (choice == 1) {
            System.out.print("Код факультету: ");
            return service.getFacultyByCode(sc.nextLine());
        } else if (choice == 2) {
            return chooseFaculty();
        }
        return null;
    }
    private static void updateFaculty() {
        Faculty f = findFaculty();

        if (f == null) {
            System.out.println("Факультет не знайдено або операцію скасовано.");
            return;
        }

        boolean updating = true;
        while (updating) {
            System.out.println("\n--- РЕДАГУВАННЯ ФАКУЛЬТЕТУ: " + f.getShortName() + " ---");
            System.out.println("Що ви хочете змінити?");
            System.out.println("1) Назву (" + f.getName() + ")");
            System.out.println("2) Код (" + f.getCode() + ")");
            System.out.println("3) Абревіатуру (" + f.getShortName() + ")");
            System.out.println("4) Контакти (" + f.getContacts() + ")");
            System.out.println("5) Декана");
            System.out.println("0) Завершити редагування");

            int choice = getIntInput("Ваш вибір", 0, 5);

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Введіть нову назву: ");
                        f.setName(sc.nextLine());
                        break;
                    case 2:
                        System.out.print("Введіть новий код: ");
                        f.setCode(sc.nextLine());
                        break;
                    case 3:
                        System.out.print("Введіть нову абревіатуру: ");
                        f.setShortName(sc.nextLine());
                        break;
                    case 4:
                        System.out.print("Введіть нові контакти: ");
                        f.setContacts(sc.nextLine());
                        break;
                    case 5:
                        System.out.println("Оберіть нового Декана:");
                        Teacher newDean = chooseTeacher();
                        if (newDean != null) {
                            f.setDean(newDean);
                        }
                        break;
                    case 0:
                        updating = false;
                        break;
                }
                if (choice != 0) System.out.println("Дані оновлено!");
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
                System.out.println("Старе значення збережено.");
            }
        }
    }


    private static void deleteFaculty() {
        Faculty f = findFaculty();
        if (f == null) {
            System.out.println("Операцію скасовано або факультет не знайдено.");
            return;
        }

        System.out.println("Ви впевнені, що хочете видалити факультет " + f.getShortName() + "?");
        int confirm = getIntInput("1 - Так, 0 - Ні", 0, 1);

        if (confirm == 1) {
            if (service.removeFaculty(f.getCode()))
                System.out.println("Факультет успішно видалено.");
             else
                System.out.println("Помилка: не вдалося видалити факультет.");
        }
    }

    private static void updateDepartment() {
        Department d = findDepartment();
        if (d == null) {
            System.out.println("Кафедру не знайдено або операцію скасовано.");
            return;
        }

        while (true) {
            System.out.println("\n--- РЕДАГУВАННЯ КАФЕДРИ: " + d.getName() + " ---");
            System.out.println("Що змінити? 1) Назву 2) Факультет 3) Завідувача 4) Локацію 0) Готово");
            int choice = getIntInput("Ваш вибір", 0, 4);
            if (choice == 0) break;
            try {
                switch (choice) {
                    case 1:
                        System.out.print("Введіть нову назву: ");
                        d.setName(sc.nextLine());
                        break;
                    case 2:
                        System.out.println("Оберіть новий факультет:");
                        d.setFaculty(chooseFaculty());
                        break;
                    case 3:
                        System.out.println("Оберіть нового завідувача:");
                        d.setHead(chooseTeacher());
                        break;
                    case 4:
                        System.out.print("Введіть нову локацію: ");
                        d.setLocation(sc.nextLine());
                        break;
                }
                System.out.println("Поле оновлено.");
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }


    private static Department findDepartment() {
        System.out.println("Як знайти кафедру? 1) За кодом 2) Обрати зі списку 0) Назад");
        int method = getIntInput("Ваш вибір", 0, 2);
        if (method == 1) {
            System.out.print("Введіть код кафедри: ");
            String code = sc.nextLine();
            if(code!=null && !code.isEmpty())
            return service.getDepartmentByCode(code);
            else
                return null;

        } else if (method == 2) {
            Department[] all = service.getDepartments();
            int count = 0;
            for (Department d : all) {
                if (d != null) {
                    System.out.println((count + 1) + ") [" + d.getCode() + "] " + d.getName());
                    count++;
                }
            }
            if (count == 0) return null;
            int choice = getIntInput("Оберіть номер", 1, count);
            return all[choice-1];
        }
        return null;
    }

        private static void deleteDepartment() {
            Department d = findDepartment();
            if (d == null) {
                System.out.println("Операцію скасовано або кафедру не знайдено.");
                return;
            }
            System.out.println("Ви впевнені, що хочете видалити кафедру " + d.getName() + "? (1 - Так, 0 - Ні)");
            if (getIntInput("Підтвердження", 0, 1) == 1) {
                if (service.removeDepartment(d.getCode())) {
                    System.out.println("Кафедру видалено.");
                } else {
                    System.out.println("Помилка видалення.");
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
                        System.out.println(searchName(student));
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



    private static Person searchName(Person[] humans) {

        System.out.println("Введіть ПІБ у форматі ПРІЗВИЩЕ ІМ'Я ПО-БАТЬКОВІ");
        String name = sc.nextLine();

        Optional<Person> maybePerson = Searching.findByName(name, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new IllegalArgumentException("User not found"));
            return person;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
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

