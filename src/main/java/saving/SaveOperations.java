package saving;

import CLI.ConsoleProgram;
import CLI.Repository;
import CLI.UniversityRepository;
import DigiPackage.Department;
import DigiPackage.Faculty;
import DigiPackage.Student;
import DigiPackage.Teacher;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class SaveOperations {
    private static final String PATH=".\\resources\\";
    private static final Path STUDENTS_SAVE = Path.of(PATH+"Students.json");
    private static final Path TEACHERS_SAVE = Path.of(PATH+"Teachers.json");
    private static final Path FACULTIES_SAVE = Path.of(PATH+"Faculties.json");
    private static final Path DEPARTMENTS_SAVE = Path.of(PATH+"Departments.json");
    private static final Path STUDENTS_SAVE_TEMP = Path.of(PATH+"temp_"+"Students.json");
    private static final Path TEACHERS_SAVE_TEMP = Path.of(PATH+"temp_"+"Teachers.json");
    private static final Path FACULTIES_SAVE_TEMP = Path.of(PATH+"temp_"+"Faculties.json");
    private static final Path DEPARTMENTS_SAVE_TEMP = Path.of(PATH+"temp_"+"Departments.json");
    private final ObjectMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(SaveOperations.class);
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
            if (Files.notExists(DEPARTMENTS_SAVE.getParent()))
                Files.createDirectories(DEPARTMENTS_SAVE.getParent());
        } catch (IOException e) {
           log.error("problem with create directory{}",e.getMessage());
        }
    }

    public void saveTempDatabase(Repository repo, UniversityRepository uniRepo) {
        log.debug("save database");
        try {
            mapper.writeValue(Files.newOutputStream(STUDENTS_SAVE_TEMP,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), repo.getStudents());
            log.trace("students was saved by thread");
        } catch (IOException e) {
            log.error("error write students to JSON: {}",e.getMessage());
        }
        try {
            mapper.writeValue(Files.newOutputStream(TEACHERS_SAVE_TEMP,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), repo.getTeachers());
            log.trace("Teachers was saved by thread");
        } catch (IOException e) {
            log.error("error write Teachers to JSON: {}",e.getMessage());
        }
        try {
            mapper.writeValue(Files.newOutputStream(FACULTIES_SAVE_TEMP,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), uniRepo.getFaculties());
            log.trace("Faculties was saved by thread");
        } catch (IOException e) {
            log.error("error write Faculties to JSON: {}",e.getMessage());
        }
        try {
            mapper.writeValue(Files.newOutputStream(DEPARTMENTS_SAVE_TEMP,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), uniRepo.getDepartments());
            log.trace("Departments was saved by thread");
        } catch (IOException e) {
            log.error("error write Departments to JSON: {}",e.getMessage());
        }
        log.info("database saved");
    }
    public void commitChanges() {
        log.debug("commit changes was called by User");
        try {
            String[] entities = {"Students", "Teachers", "Faculties", "Departments"};

            for (String entity : entities) {
                Path tempPath = Path.of(".\\resources\\temp_" + entity + ".json");
                Path mainPath = Path.of(".\\resources\\" + entity + ".json");
                if (Files.exists(tempPath)) {
                    Files.move(tempPath, mainPath, StandardCopyOption.REPLACE_EXISTING);
                    Files.deleteIfExists(tempPath);
                }
                log.info("temp file was moved to: " + tempPath);
            }
            System.out.println("Зміни успішно застосовані до бази даних!");
        } catch (IOException e) {
            log.error("error when move tmp files: {}", e.getMessage());
        }
    }
    public void loadDatabase(Repository repo, UniversityRepository uniRepo) throws IOException {
        log.debug("load database");
        if (Files.notExists(STUDENTS_SAVE)) {
            Files.createFile(STUDENTS_SAVE);
            log.trace("students file was created");
        }
        if(Files.notExists(TEACHERS_SAVE)) {
            Files.createFile(TEACHERS_SAVE);
            log.trace("Teachers file was created");
        }
        if (Files.notExists(FACULTIES_SAVE)) {
            Files.createFile(FACULTIES_SAVE);
            log.trace("Faculties file was created");
        }
        if (Files.notExists(DEPARTMENTS_SAVE)) {
            Files.createFile(DEPARTMENTS_SAVE);
            log.trace("Department file was created");
        }
        else {
            try {
                List<Student> students = mapper.readValue(STUDENTS_SAVE.toFile(), new TypeReference<List<Student>>() {});
                for (Student s : students) {
                    repo.addStudent(s);
                }
                log.trace("students loaded successfully");
                List<Teacher> teachers = mapper.readValue(TEACHERS_SAVE.toFile(), new TypeReference<List<Teacher>>() {});
                for (Teacher t : teachers) {
                    repo.addTeacher(t);
                }
                log.trace("Teachers loaded successfully");

                List<Faculty> faculties = mapper.readValue(FACULTIES_SAVE.toFile(), new TypeReference<List<Faculty>>() {});
                for (Faculty f : faculties) {
                    uniRepo.addFaculty(f);
                }
                log.trace("Faculties loaded successfully");

                List<Department> deparments = mapper.readValue(DEPARTMENTS_SAVE.toFile(), new TypeReference<List<Department>>() {});
                for (Department d : deparments) {
                    uniRepo.addDepartment(d);
                }
                log.trace("Department loaded successfully");
                for (Department d : uniRepo.getDepartments()) {
                    if (d.getFaculty() != null && d.getFaculty().getCode() != null) {
                        Faculty realFaculty = uniRepo.getFacultiesMap().get(d.getFaculty().getCode());
                        if (realFaculty != null) {
                            d.setFaculty(realFaculty);
                        }
                    }
                }
                log.trace("Departments loaded successfully to faculties");
                for (Student s : repo.getStudents()) {
                    if (s.getDepartmentCode() != null) {
                        Department d = uniRepo.getDepartmentMap().get(s.getDepartmentCode());
                        if (d != null) {
                            d.addStudentToDepartment(s);
                        }
                    }
                }
                log.trace("Students loaded successfully to departments");
                for (Teacher t : repo.getTeachers()) {
                    if (t.getDepartmentCode() != null) {
                        Department d = uniRepo.getDepartmentMap().get(t.getDepartmentCode());
                        if (d != null) {
                            d.addTeacherToDepartment(t);
                        }
                    }
                }
                log.trace("Teachers loaded successfully to departments");
                log.info("load database successfully");
            } catch (Exception e) {
                log.error("load database error: {}",e.getMessage());
            }
        }
    }

    public void deleteTempFiles() {
        log.debug("commit changes was called by User");
        try {
            String[] entities = {"Students", "Teachers", "Faculties", "Departments"};

            for (String entity : entities) {
                Path tempPath = Path.of(".\\resources\\temp_" + entity + ".json");
                Files.deleteIfExists(tempPath);
                log.info("temp file was succesfuly deleted: " + tempPath);
            }
        } catch (IOException e) {
            log.error("error when delete tmp files: {}", e.getMessage());
        }
    }
}