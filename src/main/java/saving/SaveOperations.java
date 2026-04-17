package saving;

import CLI.Repository;
import CLI.UniversityRepository;
import DigiPackage.Department;
import DigiPackage.Faculty;
import DigiPackage.Student;
import DigiPackage.Teacher;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class SaveOperations {
    private static final Path STUDENTS_SAVE = Path.of(".\\resources\\Students.json");
    private static final Path TEACHERS_SAVE = Path.of(".\\resources\\Teachers.json");
    private static final Path FACULTIES_SAVE = Path.of(".\\resources\\Faculties.json");
    private static final Path DEPARMENTS_SAVE = Path.of(".\\resources\\Deparments.json");
    private final ObjectMapper mapper;

    public SaveOperations() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            if (Files.notExists(STUDENTS_SAVE.getParent()))
                Files.createDirectories(STUDENTS_SAVE.getParent());
            if (Files.notExists(TEACHERS_SAVE.getParent()))
                Files.createDirectories(TEACHERS_SAVE.getParent());
            if (Files.notExists(FACULTIES_SAVE.getParent()))
                Files.createDirectories(FACULTIES_SAVE.getParent());
            if (Files.notExists(DEPARMENTS_SAVE.getParent()))
                Files.createDirectories(DEPARMENTS_SAVE.getParent());
        } catch (IOException e) {
            System.err.println("Помилка створення папки: " + e.getMessage());
        }
    }

    public void saveDatabase(Repository repo, UniversityRepository uniRepo) {

        try {
            mapper.writeValue(Files.newOutputStream(STUDENTS_SAVE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), repo.getStudents());
            System.out.println("Базу даних успішно збережено у " + STUDENTS_SAVE);
        } catch (IOException e) {
            System.err.println("Помилка запису JSON: " + e.getMessage());
        }
        try {
            mapper.writeValue(Files.newOutputStream(TEACHERS_SAVE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), repo.getTeachers());
            System.out.println("Базу даних успішно збережено у " + TEACHERS_SAVE);
        } catch (IOException e) {
            System.err.println("Помилка запису JSON: " + e.getMessage());
        }
        try {
            mapper.writeValue(Files.newOutputStream(FACULTIES_SAVE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), uniRepo.getFaculties());
            System.out.println("Базу даних успішно збережено у " + FACULTIES_SAVE);
        } catch (IOException e) {
            System.err.println("Помилка запису JSON: " + e.getMessage());
        }
        try {
            mapper.writeValue(Files.newOutputStream(DEPARMENTS_SAVE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), uniRepo.getDepartments());
            System.out.println("Базу даних успішно збережено у " + DEPARMENTS_SAVE);
        } catch (IOException e) {
            System.err.println("Помилка запису JSON: " + e.getMessage());
        }
    }

    public void loadDatabase(Repository repo, UniversityRepository uniRepo) throws IOException {
        if (Files.notExists(STUDENTS_SAVE))
            Files.createFile(STUDENTS_SAVE);
        if(Files.notExists(TEACHERS_SAVE))
            Files.createFile(TEACHERS_SAVE);
        if (Files.notExists(FACULTIES_SAVE))
            Files.createFile(FACULTIES_SAVE);
        if (Files.notExists(DEPARMENTS_SAVE))
            Files.createFile(DEPARMENTS_SAVE);
        else {
            try {
                List<Student> students = mapper.readValue(STUDENTS_SAVE.toFile(), new TypeReference<List<Student>>() {});
                for (Student s : students) {
                    repo.addStudent(s);
                }
                System.out.println("Дані Студентів успішно завантажено з JSON!");
                List<Teacher> teachers = mapper.readValue(TEACHERS_SAVE.toFile(), new TypeReference<List<Teacher>>() {});
                for (Teacher t : teachers) {
                    repo.addTeacher(t);
                }
                System.out.println("Дані Викладачів успішно завантажено з JSON!");
                List<Faculty> faculties = mapper.readValue(FACULTIES_SAVE.toFile(), new TypeReference<List<Faculty>>() {});
                for (Faculty f : faculties) {
                    uniRepo.addFaculty(f);
                }
                System.out.println("Дані Факультетів успішно завантажено з JSON!");
                List<Department> deparments = mapper.readValue(DEPARMENTS_SAVE.toFile(), new TypeReference<List<Department>>() {});
                for (Department d : deparments) {
                    uniRepo.addDepartment(d);
                }
                System.out.println("Дані Кафедр успішно завантажено з JSON!");
            } catch (Exception e) {
                System.err.println("Помилка читання JSON: " + e.getMessage());
            }
        }
    }
}