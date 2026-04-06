package CLI;

import DigiPackage.*;
import exceptions.EntityAlreadyExistsException;
import exceptions.ValidatingException;

import java.util.List;
import java.util.Optional;

import static CLI.InputHelper.getDoubleInput;
import static CLI.InputHelper.getIntInput;
import static CLI.InputHelper.getStringInput;

import static CLI.ConsoleProgram.repository;
import static CLI.ConsoleProgram.universityRepository;

public class CRUDoperations {
    Searching searching = new Searching();

    public void createStudent() {
        System.out.println("\n--- ДОДАВАННЯ СТУДЕНТА ---");
        Student st = new Student();
        fillStudentData(st);
        try {
            repository.addStudent(st);
        }
        catch(EntityAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    public void fillStudentData(Student st) {
        int step = 0;
        int totalSteps = 9;
        while (step <= totalSteps) {
            try {
                switch (step) {
                    case 0:
                        st.setId(getStringInput("Введіть id: "));
                        step++;
                        break;
                    case 1:
                        st.setName(getStringInput("Введіть ПІБ: "));
                        step++;
                        break;
                    case 2:
                        st.setDateOfBirth(getStringInput("Введіть дату народження (дд.мм.рррр): "));
                        step++;
                        break;
                    case 3:
                        st.setEmail(getStringInput("Введіть Email до @: ") + "@ukma.edu.ua");
                        step++;
                        break;
                    case 4:
                        st.setPhone("+380" + getStringInput("Введіть телефон без +380: "));
                        step++;
                        break;
                    case 5:
                        st.setIdOfRecordBook(getStringInput("Введіть номер заліковки: "));
                        step++;
                        break;
                    case 6:
                        st.setCourse(getIntInput("Введіть курс (1-6): ", 1, 6));
                        step++;
                        break;
                    case 7:
                        st.setGroup(getStringInput("Введіть групу: "));
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
            } catch (ValidatingException e) {
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
        try {
            repository.addTeacher(t);
        } catch(EntityAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    public void fillTeacherData(Teacher t) {
        int step = 1;
        int totalSteps = 11;
        while (step <= totalSteps) {
            try {
                switch (step) {
                    case 1:
                        t.setId(getStringInput("Введіть ID: "));
                        step++;
                        break;
                    case 2:
                        t.setName(getStringInput("Введіть ПІБ: "));
                        step++;
                        break;
                    case 3:
                        t.setDateOfBirth(getStringInput("Введіть дату народження: "));
                        step++;
                        break;
                    case 4:
                        t.setEmail(getStringInput("Введіть Email до @: ") + "@ukma.edu.ua");
                        step++;
                        break;
                    case 5:
                        t.setPhone("+380" + getStringInput("Введіть телефон без +380: "));
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
                        t.setDateOfentering(getStringInput("Дата прийняття: "));
                        step++;
                        break;
                    case 10:
                        t.setRate(getDoubleInput("Ставка (0.25-1.5): ", 0.25, 1.5));
                        step++;
                        break;
                    case 11:
                        t.setWorkload(Integer.parseInt(getStringInput("Навантаження: ")));
                        step++;
                        break;
                }
            } catch (ValidatingException e) {
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
            universityRepository.addFaculty(f);
            System.out.println(f.getName() + " успішно створений!");
        } catch (EntityAlreadyExistsException e) {
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
                        f.setCode(getStringInput("Введіть код факультету: "));
                        step++;
                        break;
                    case 2:
                        f.setName(getStringInput("Введіть повну назву: "));
                        step++;
                        break;
                    case 3:
                        f.setShortName(getStringInput("Введіть абревіатуру: "));
                        step++;
                        break;
                    case 4:
                        f.setContacts(getStringInput("Введіть контакти: "));
                        step++;
                        break;
                    case 5:
                        if (!repository.getTeachers().isEmpty()) {
                            Teacher t = findTeacher();
                            f.setDean(t);
                            t.setPosition(Position.Dean);
                        } else {
                            System.out.println("Вам треба буде обрати Декана після створення вчителів");
                        }
                        step++;
                        break;
                }
            } catch (ValidatingException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
        System.out.println("Дані факультету заповнено.");
    }

    public void createDepartment() {
        System.out.println("\n--- СТВОРЕННЯ КАФЕДРИ ---");
        if (universityRepository.getFaculties().isEmpty()) {
            System.out.println("Спочатку створіть факультет.");
            return;
        }
        Department d = new Department();
        fillDepartmentData(d);
        try {
            universityRepository.addDepartment(d);
            System.out.println("Кафедра " + d.getName() + " успішно створена!");
        } catch (EntityAlreadyExistsException e) {
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
                        d.setCode(getStringInput("Введіть код кафедри: "));
                        step++;
                        break;
                    case 2:
                        d.setName(getStringInput("Введіть назву: "));
                        step++;
                        break;
                    case 3:
                        System.out.println("Оберіть ФАКУЛЬТЕТ:");
                        d.setFaculty(findFaculty());
                        step++;
                        break;
                    case 4:
                        if (!repository.getTeachers().isEmpty()) {
                            Teacher t = findTeacher();
                            d.setHead(t);
                            t.setPosition(Position.Head_of_department);
                        } else {
                            System.out.println("Після створення треба буде додати завідувача");
                        }
                        step++;
                        break;
                    case 5:
                        d.setLocation(getStringInput("Введіть локацію: "));
                        step++;
                        break;
                }
            } catch (ValidatingException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    public void readFaculty() {
        System.out.println("\n--- СПИСОК ФАКУЛЬТЕТІВ ---");
        List<Faculty> all = universityRepository.getFaculties();
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
        List<Department> all = universityRepository.getDepartments();
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
            System.out.println("1) ПІБ 2) Телефон 3) Курс 4) Група 5) Статус 6) Форма 7)Додати на кафедру) Готово");
            int step = getIntInput("Оберіть поле", 0, 6);
            try {
                switch (step) {
                    case 3:
                        st.setCourse(getIntInput("Новий курс (1-6)", 1, 6));
                        break;
                    case 4:
                        st.setGroup(getStringInput("Нова група 'ІПЗ-1' "));
                        break;
                    case 5:
                        st.setStudentStatus(chooseEnum("Статус", Status.values()));
                        break;
                    case 6:
                        st.setFormOfStudy(chooseEnum("Форма Навчання", FormOfStudy.values()));
                        break;
                    case 7:
                        if (universityRepository.getDepartments().isEmpty())
                            System.out.println("Неможливо додати, бо не існує кафедр");
                        else {
                            Department d = chooseEntity(universityRepository.getDepartments());
                            d.addStudentToDepartment(st);
                        }

                    case 0:
                        updating = false;
                        break;
                    default:
                        personUpdate(st, step);
                        break;
                }
            } catch (ValidatingException e) {
                System.out.println("Помилка: " + e.getMessage());
            }catch (EntityAlreadyExistsException e) {
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
            System.out.println("1) ПІБ 2) Телефон 3) Посада 4) Ступінь 5) Звання 6) Ставка 7) Навантаження 8)Додати до кафедри 0) Готово");
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
                        t.setRate(getDoubleInput("Ставка (0.25-1.5)", 0.25, 1.5));
                        break;
                    case 7:
                        t.setWorkload(getIntInput("Навантаження (0-1000)",0,1000));
                        break;
                    case 8:
                        if (universityRepository.getDepartments().isEmpty())
                            System.out.println("Неможливо додати, бо не існує кафедр");
                        else {
                            Department d = chooseEntity(universityRepository.getDepartments());
                            d.addTeacherToDepartment(t);
                        }
                    case 0:
                        updating = false;
                        break;
                    default:
                        personUpdate(t, step);
                        break;
                }
            } catch (ValidatingException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
            catch (EntityAlreadyExistsException e) {
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
            System.out.println("1) Назву 2) Абревіатуру 3) Контакти 4) Декана 0) Готово");
            int choice = getIntInput("Вибір", 0, 5);
            try {
                switch (choice) {
                    case 1:
                        f.setName(getStringInput("Назва: "));
                        break;
                    case 2:
                        f.setShortName(getStringInput("Абревіатура: "));
                        break;
                    case 3:
                        f.setContacts(getStringInput("Контакти: "));
                        break;
                    case 4:
                        Teacher newDean = findTeacher();
                        if (newDean != null) f.setDean(newDean);
                        break;
                    case 0:
                        updating = false;
                        break;
                }
            } catch (ValidatingException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    public void updateDepartment() {
        Department d = findDepartment();
        if (d == null) return;
        while (true) {
            System.out.println("\n--- РЕДАГУВАННЯ КАФЕДРИ ---");
            System.out.println("1) Назву 2) Факультет 3) Завідувача 4) Локацію 5)Додати до Факультету 0) Готово");
            int choice = getIntInput("Вибір", 0, 4);
            if (choice == 0) break;
            try {
                switch (choice) {
                    case 1:
                        d.setName(getStringInput("Назва: "));
                        break;
                    case 2:
                        d.setFaculty(findFaculty());
                        break;
                    case 3:
                        d.setHead(findTeacher());
                        break;
                    case 4:
                        d.setLocation(getStringInput("Локація: "));
                        break;
                    case 5:
                        if (universityRepository.getFaculties().isEmpty())
                            System.out.println("Неможливо додати, бо не існує факультетів");
                        else {
                                Faculty f = chooseEntity(universityRepository.getFaculties());
                                f.addDepartmentTofaculty(d);

                        }
                }
            } catch (ValidatingException e) {
                System.out.println("Помилка: " + e.getMessage());
            } catch (EntityAlreadyExistsException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private void personUpdate(Person p, int step) {
        if (step == 2) {
            p.setPhone(getStringInput("Новий телефон без +380: "));
        } else if (step == 1) {
            p.setName(getStringInput("Новий ПІБ: "));
        }
    }

    public void deleteStudent() {
        Student s = findStudent();
        if (s != null && getIntInput("Видалити? 1-Так, 0-Ні", 0, 1) == 1 && repository.getStudentmap().get(s.getId()) != null) {
            repository.removeStudent(s);
            System.out.println("Видалення Успішне!");
        } else
            System.out.println("Видалення не було");

    }

    public void deleteTeacher() {
        Teacher t = findTeacher();
        if (t != null && getIntInput("Видалити? 1-Так, 0-Ні", 0, 1) == 1 && repository.getTeachersmap().get(t.getId())!=null) {
            repository.removeTeacher(t);
            System.out.println("Видалення Успішне!");
        } else
            System.out.println("Видалення не було");

    }

    public void deleteFaculty() {
        Faculty f = findFaculty();
        if (f != null && getIntInput("Видалити? 1-Так, 0-Ні", 0, 1) == 1 && universityRepository.getFacultiesMap().get(f.getCode())!=null) {
            universityRepository.removeFaculty(f);
            System.out.println("Видалення Успішне!");
        } else
            System.out.println("Видалення не було");
    }

    public void deleteDepartment() {
        Department d = findDepartment();
        if (d != null && getIntInput("Видалити? 1-Так, 0-Ні", 0, 1) == 1 && universityRepository.getDepartmentMap().get(d.getCode())!=null) {
            universityRepository.removeDepartment(d);
            System.out.println("Видалення Успішне!");
        } else
            System.out.println("Видалення не було");

    }

    public Student findStudent() {
        System.out.println("\n--- ПОШУК СТУДЕНТА ---");
        System.out.println("1) За ID 2) За ПІБ 3) Зі списку 0) Назад");
        int choice = getIntInput("Вибір", 0, 3);
        while (true) {
            Optional<? extends Person> maybestudent = Optional.empty();
            switch (choice) {
                case 1:
                    maybestudent = searching.findById(getStringInput("Введіть ID: "), repository.getStudentmap());
                    break;
                case 2:
                    maybestudent = searching.findByName(getStringInput("Введіть ПІБ: "), repository.getStudents());
                    break;
                case 3:
                    return chooseEntity(repository.getStudents());
                default:
                    break;
            }
            if (maybestudent.isPresent()) {
                Person p = maybestudent.get();
                if (p instanceof Student student) {
                    return student;
                }
            }
            System.out.println("Студента не знайдено, спробуйте знову");

        }
    }

    public Teacher findTeacher() {
        System.out.println("\n--- ПОШУК ВИКЛАДАЧА ---");
        System.out.println("1) За ID 2) За ПІБ 3) Зі списку 0) Назад");
        int choice = getIntInput("Вибір", 0, 3);
        while (true) {
            Optional<? extends Person> maybeteacher = Optional.empty();
            switch (choice) {
                case 1:
                    maybeteacher = searching.findById(getStringInput("Введіть ID: "), repository.getTeachersmap());
                    break;
                case 2:
                    maybeteacher = searching.findByName(getStringInput("Введіть ПІБ: "), repository.getTeachers());
                    break;
                case 3:
                    return chooseEntity(repository.getTeachers());
                default:
                    break;
            }
            if (maybeteacher.isPresent()) {
                Person p = maybeteacher.get();
                if (p instanceof Teacher teacher) {
                    return teacher;
                }
            }
            System.out.println("Викладача не знайдено, спробуйте знову");

        }
    }

    private <T> T chooseEntity(List<T> entity) {
        int i = 0;
        for (Object o : entity) {
            i++;
            System.out.println(i + ") " + o);
        }
        int choose = getIntInput("Оберіть бажанний варіант", 1, i);
        return entity.get(choose - 1);
    }

    public Faculty findFaculty() {
        System.out.println("\n--- ПОШУК ФАКУЛЬТЕТУ ---");
        System.out.println("1) За кодом 2) Зі списку 0) Назад");
        int choice = getIntInput("Вибір", 0, 2);
        switch (choice) {
            case 1:
                while (true) {
                    Optional<Faculty> maybeFaculties = searching.getFacultyByCode(getStringInput("Код: "), universityRepository.getFacultiesMap());
                    if (maybeFaculties.isPresent())
                        return maybeFaculties.get();
                    else
                        System.out.println("Факультет не знайдено, спробуйте знову");
                }
            case 2:
                return chooseEntity(universityRepository.getFaculties());
            default:
                return null;
        }
    }

    public Department findDepartment() {
        System.out.println("1) За кодом 2) Зі списку 0) Назад");
        int method = getIntInput("Вибір", 0, 2);
        while (true) {
            switch (method) {
                case 1:
                    while (true) {
                        Optional<Department> maybeDepartment = searching.getDepartmentByCode(getStringInput("Код: "), universityRepository.getDepartmentMap());
                        if (maybeDepartment.isPresent())
                            return maybeDepartment.get();
                        else
                            System.out.println("Кафедру не знайдено, спробуйте знову");
                    }
                case 2:
                    return chooseEntity(universityRepository.getDepartments());
                default:
                    break;
            }
        }
    }

    public University getUniversity() {
        return universityRepository.getUniversity();
    }

    private <T extends Enum<T>> T chooseEnum(String message, T[] values) {
        int choice = getIntInput(message, 1, values.length);
        return values[choice - 1];
    }
}