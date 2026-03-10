package CLI;

import DigiPackage.*;

import java.util.List;
import java.util.Scanner;

import static CLI.ConsoleProgram.getIntInput;

public class CRUDoperations {
    UniversityService service = new UniversityService();
    Repository repository = new Repository();
    Scanner sc = new Scanner(System.in);

    public void createStudent() {
        System.out.println("\n--- ДОДАВАННЯ СТУДЕНТА ---");
        Student st = new Student();
        fillStudentData(st);
        repository.addStudent(st);
    }

    public void fillStudentData(Student st) {
        int step = 0;
        int totalSteps = 9;
        while (step <= totalSteps) {
            try {
                switch (step) {
                    case 0:
                        System.out.print("Введіть id: ");
                        st.setId(sc.nextLine());
                        step++;
                        break;
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
                        System.out.print("Введіть номер заліковки: ");
                        st.setIdOfRecordBook(sc.nextLine());
                        step++;
                        break;
                    case 6:
                        System.out.print("Введіть курс (1-6): ");
                        st.setCourse(Integer.parseInt(sc.nextLine()));
                        step++;
                        break;
                    case 7:
                        System.out.print("Введіть групу: ");
                        st.setGroup(sc.nextLine());
                        step++;
                        break;
                    case 8:
                        st.setFormOfStudy(chooseEnum("Оберіть форму навчання: 1)Бюджет 2)Контракт", FormOfStudy.values()));
                        step++;
                        break;
                    case 9:
                        st.setStudentStatus(chooseEnum("Оберіть статус: 1)Навчається 2)Академ 3)Відрахований", Status.values()));
                        step++;
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
                System.out.println("Спробуйте ще раз.");
            }
        }
        System.out.println("Студент був заповнений");
    }

    public void createTeacher() {
        System.out.println("\n--- ДОДАВАННЯ ВИКЛАДАЧА ---");
        Teacher t = new Teacher();
        fillTeacherData(t);
        repository.addTeacher(t);
    }

    public void fillTeacherData(Teacher t) {
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
                        System.out.print("Введіть дату народження: ");
                        t.setDateOfBirth(sc.nextLine());
                        step++;
                        break;
                    case 4:
                        System.out.print("Введіть Email: ");
                        t.setEmail(sc.nextLine());
                        step++;
                        break;
                    case 5:
                        System.out.print("Введіть телефон: ");
                        t.setPhone(sc.nextLine());
                        step++;
                        break;
                    case 6:
                        t.setPosition(chooseEnum("Посада: 1)Асистент 2)Викладач 3)Старший викладач 4)Доцент 5)Професор 6)Завкафедри 7)Декан", Position.values()));
                        step++;
                        break;
                    case 7:
                        t.setScientificDegree(chooseEnum("Науковий ступінь: 1)PHD 2)Кандидат 3)Доктор 4)Немає", ScientificDegree.values()));
                        step++;
                        break;
                    case 8:
                        t.setAcademicTitle(chooseEnum("Вчене звання: 1)Немає 2)Старший дослідник 3)Доцент 4)Професор", AcademicTitle.values()));
                        step++;
                        break;
                    case 9:
                        System.out.print("Дата прийняття: ");
                        t.setDateOfentering(sc.nextLine());
                        step++;
                        break;
                    case 10:
                        System.out.print("Ставка (0.25-1.5): ");
                        t.setRate(Double.parseDouble(sc.nextLine()));
                        step++;
                        break;
                    case 11:
                        System.out.print("Навантаження: ");
                        t.setWorkload(Integer.parseInt(sc.nextLine()));
                        step++;
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
        System.out.println("Викладач був заповнений");
    }

    public void createFaculty() {
        System.out.println("\n--- СТВОРЕННЯ ФАКУЛЬТЕТУ ---");
        Faculty f = new Faculty();
        fillFaculty(f);
        try {
            service.addFaculty(f);
            System.out.println(f.getName() + " успішно створений!");
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    public void fillFaculty(Faculty f) {
        int step = 1;
        int totalSteps = 5;
        while (step <= totalSteps) {
            try {
                switch (step) {
                    case 1:
                        System.out.print("Введіть код факультету: ");
                        f.setCode(sc.nextLine());
                        step++;
                        break;
                    case 2:
                        System.out.print("Введіть повну назву: ");
                        f.setName(sc.nextLine());
                        step++;
                        break;
                    case 3:
                        System.out.print("Введіть абревіатуру: ");
                        f.setShortName(sc.nextLine());
                        step++;
                        break;
                    case 4:
                        System.out.print("Введіть контакти: ");
                        f.setContacts(sc.nextLine());
                        step++;
                        break;
                    case 5:
                        if (!repository.getTeachers().isEmpty()) {
                            Teacher t = chooseTeacher();
                            f.setDean(t);
                            t.setPosition(Position.Dean);
                        } else {
                            System.out.println("Вам треба буде обрати Декана після створення вчителів");
                        }
                        step++;
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
        System.out.println("Дані факультету заповнено.");
    }

    public void createDepartment() {
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

    public void fillDepartmentData(Department d) {
        int step = 1;
        int totalSteps = 5;
        while (step <= totalSteps) {
            try {
                switch (step) {
                    case 1:
                        System.out.print("Введіть код кафедри: ");
                        d.setCode(sc.nextLine());
                        step++;
                        break;
                    case 2:
                        System.out.print("Введіть назву: ");
                        d.setName(sc.nextLine());
                        step++;
                        break;
                    case 3:
                        System.out.println("Оберіть ФАКУЛЬТЕТ:");
                        d.setFaculty(chooseFaculty());
                        step++;
                        break;
                    case 4:
                        if (!repository.getTeachers().isEmpty()) {
                            Teacher t = chooseTeacher();
                            d.setHead(t);
                            t.setPosition(Position.Head_of_department);
                        } else {
                            System.out.println("Після створення треба буде додати завідувача");
                        }
                        step++;
                        break;
                    case 5:
                        System.out.print("Введіть локацію: ");
                        d.setLocation(sc.nextLine());
                        step++;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    public void readFaculty() {
        System.out.println("\n--- СПИСОК ФАКУЛЬТЕТІВ ---");
        Faculty[] all = service.getFaculties();
        int count = 0;
        for (Faculty f : all) {
            if (f != null) {
                System.out.println((count + 1) + ". " + f);
                count++;
            }
        }
        if (count == 0) System.out.println("Факультетів поки не створено.");
    }

    public void readDepartment() {
        System.out.println("\n--- СПИСОК КАФЕДР ---");
        Department[] all = service.getDepartments();
        int count = 0;
        for (Department d : all) {
            if (d != null) {
                System.out.println((count + 1) + ". " + d);
                count++;
            }
        }
        if (count == 0) System.out.println("Кафедр поки не створено.");
    }

    public void readStudent() {
        System.out.println("\n--- СПИСОК СТУДЕНТІВ ---");
        int count = 0;
        for (Student s : repository.getStudents()) {
            if (s != null) {
                System.out.println((count + 1) + ". " + s);
                count++;
            }
        }
        if (count == 0) System.out.println("Студентів у реєстрі немає.");
    }

    public void readTeacher() {
        System.out.println("\n--- СПИСОК ВИКЛАДАЧІВ ---");
        if (repository.getTeachers().isEmpty()) {
            System.out.println("Викладачів у реєстрі немає.");
        } else {
            int count = 0;
            for (Teacher t : repository.getTeachers()) {
                if (t != null) {
                    System.out.println((count + 1) + ". " + t);
                    count++;
                }
            }
        }
    }

    public void updateStudent() {
        Student st = findStudent();
        if (st == null) return;
        boolean updating = true;
        while (updating) {
            System.out.println("\n--- РЕДАГУВАННЯ: " + st.getName() + " ---");
            System.out.println("1) ПІБ 2) Телефон 3) Курс 4) Група 5) Статус 6) Форма 0) Готово");
            int step = getIntInput("Оберіть поле", 0, 6);
            try {
                switch (step) {
                    case 3:
                        st.setCourse(getIntInput("Новий курс", 1, 6));
                        break;
                    case 4:
                        System.out.print("Нова група: ");
                        st.setGroup(sc.nextLine());
                        break;
                    case 5:
                        st.setStudentStatus(chooseEnum("Статус", Status.values()));
                        break;
                    case 6:
                        st.setFormOfStudy(chooseEnum("Форма", FormOfStudy.values()));
                        break;
                    case 0:
                        updating = false;
                        break;
                    default:
                        personUpdate(st, step);
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    public void updateTeacher() {
        Teacher t = findTeacher();
        if (t == null) return;
        boolean updating = true;
        while (updating) {
            System.out.println("\n--- РЕДАГУВАННЯ: " + t.getName() + " ---");
            System.out.println("1) ПІБ 2) Телефон 3) Посада 4) Ступінь 5) Звання 6) Ставка 7) Навантаження 0) Готово");
            int step = getIntInput("Оберіть поле", 0, 7);
            try {
                switch (step) {
                    case 3:
                        t.setPosition(chooseEnum("Посада", Position.values()));
                        break;
                    case 4:
                        t.setScientificDegree(chooseEnum("Ступінь", ScientificDegree.values()));
                        break;
                    case 5:
                        t.setAcademicTitle(chooseEnum("Звання", AcademicTitle.values()));
                        break;
                    case 6:
                        System.out.print("Ставка: ");
                        t.setRate(Double.parseDouble(sc.nextLine()));
                        break;
                    case 7:
                        System.out.print("Навантаження: ");
                        t.setWorkload(Integer.parseInt(sc.nextLine()));
                        break;
                    case 0:
                        updating = false;
                        break;
                    default:
                        personUpdate(t, step);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    public void updateFaculty() {
        Faculty f = findFaculty();
        if (f == null) return;
        boolean updating = true;
        while (updating) {
            System.out.println("\n--- РЕДАГУВАННЯ ФАКУЛЬТЕТУ ---");
            System.out.println("1) Назву 2) Код 3) Абревіатуру 4) Контакти 5) Декана 0) Готово");
            int choice = getIntInput("Вибір", 0, 5);
            try {
                switch (choice) {
                    case 1: System.out.print("Назва: "); f.setName(sc.nextLine()); break;
                    case 2: System.out.print("Код: "); f.setCode(sc.nextLine()); break;
                    case 3: System.out.print("Абревіатура: "); f.setShortName(sc.nextLine()); break;
                    case 4: System.out.print("Контакти: "); f.setContacts(sc.nextLine()); break;
                    case 5:
                        Teacher newDean = chooseTeacher();
                        if (newDean != null) f.setDean(newDean);
                        break;
                    case 0: updating = false; break;
                }
            } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
        }
    }

    public void updateDepartment() {
        Department d = findDepartment();
        if (d == null) return;
        while (true) {
            System.out.println("\n--- РЕДАГУВАННЯ КАФЕДРИ ---");
            System.out.println("1) Назву 2) Факультет 3) Завідувача 4) Локацію 0) Готово");
            int choice = getIntInput("Вибір", 0, 4);
            if (choice == 0) break;
            try {
                switch (choice) {
                    case 1: System.out.print("Назва: "); d.setName(sc.nextLine()); break;
                    case 2: d.setFaculty(chooseFaculty()); break;
                    case 3: d.setHead(chooseTeacher()); break;
                    case 4: System.out.print("Локація: "); d.setLocation(sc.nextLine()); break;
                }
            } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
        }
    }

    private void personUpdate(Person p, int step) {
        if (step == 2) {
            System.out.print("Новий телефон: ");
            p.setPhone(sc.nextLine());
        } else if (step == 1) {
            System.out.print("Новий ПІБ: ");
            p.setName(sc.nextLine());
        }
    }

    public void deleteStudent() {
        Student s = findStudent();
        if (s != null && getIntInput("Видалити? 1-Так, 0-Ні", 0, 1) == 1) {
            repository.getStudents().remove(s);
        }
    }

    public void deleteTeacher() {
        Teacher t = findTeacher();
        if (t != null && getIntInput("Видалити? 1-Так, 0-Ні", 0, 1) == 1) {
            repository.getTeachers().remove(t);
        }
    }

    public void deleteFaculty() {
        Faculty f = findFaculty();
        if (f != null && getIntInput("Видалити? 1-Так, 0-Ні", 0, 1) == 1) {
            service.removeFaculty(f.getCode());
        }
    }

    public void deleteDepartment() {
        Department d = findDepartment();
        if (d != null && getIntInput("Видалити? 1-Так, 0-Ні", 0, 1) == 1) {
            service.removeDepartment(d.getCode());
        }
    }

    public Teacher chooseTeacher() {
        List<Teacher> teachers = repository.getTeachers();
        if (teachers.isEmpty()) return null;
        for (int i = 0; i < teachers.size(); i++) {
            System.out.println((i + 1) + ") " + teachers.get(i).getName());
        }
        int choose = getIntInput("Вибір", 1, teachers.size());
        return teachers.get(choose - 1);
    }

    public Faculty chooseFaculty() {
        Faculty[] all = service.getFaculties();
        int count = 0;
        for (Faculty f : all) {
            if (f != null) System.out.println((++count) + ") " + f.getShortName());
        }
        if (count == 0) return null;
        int choice = getIntInput("Вибір факультету", 1, count);
        return all[choice - 1];
    }

    public Student findStudent() {
        System.out.println("\n--- ПОШУК СТУДЕНТА ---");
        System.out.println("1) За ID 2) За ПІБ 3) Зі списку 0) Назад");
        int choice = getIntInput("Вибір", 0, 3);
        return null;
    }

    public Teacher findTeacher() {
        System.out.println("\n--- ПОШУК ВИКЛАДАЧА ---");
        System.out.println("1) За ПІБ 2) Зі списку 0) Назад");
        int choice = getIntInput("Вибір", 0, 2);
        switch (choice) {
            case 2: return chooseTeacher();
            default: return null;
        }
    }

    public Faculty findFaculty() {
        System.out.println("\n--- ПОШУК ФАКУЛЬТЕТУ ---");
        System.out.println("1) За кодом 2) Зі списку 0) Назад");
        int choice = getIntInput("Вибір", 0, 2);
        switch (choice) {
            case 1:
                System.out.print("Код: ");
                return service.getFacultyByCode(sc.nextLine());
            case 2:
                return chooseFaculty();
            default:
                return null;
        }
    }

    public Department findDepartment() {
        System.out.println("1) За кодом 2) Зі списку 0) Назад");
        int method = getIntInput("Вибір", 0, 2);
        switch (method) {
            case 1:
                System.out.print("Код: ");
                return service.getDepartmentByCode(sc.nextLine());
            case 2:
                Department[] all = service.getDepartments();
                int count = 0;
                for (Department d : all) {
                    if (d != null) System.out.println((++count) + ") " + d.getName());
                }
                if (count == 0) return null;
                return all[getIntInput("Номер", 1, count) - 1];
            default:
                return null;
        }
    }

    public void getUniversity() {
        service.getUniversity();
    }

    private <T extends Enum<T>> T chooseEnum(String message, T[] values) {
        int choice = getIntInput(message, 1, values.length);
        return values[choice - 1];
    }
}