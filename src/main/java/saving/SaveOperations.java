package saving;

import CLI.Repository;
import CLI.UniversityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SaveOperations {
    private static final Path SAVE_FILE = Path.of(".\\resources\\SaveFile.json");
    private final ObjectMapper mapper;

    public SaveOperations() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            if (Files.notExists(SAVE_FILE.getParent())) {
                Files.createDirectories(SAVE_FILE.getParent());
            }
        } catch (IOException e) {
            System.err.println("Помилка створення папки: " + e.getMessage());
        }
    }

    public void saveDatabase(Repository repo, UniversityRepository uniRepo) {
        SnapshotService snapshot = new SnapshotService(repo.getStudents(),repo.getTeachers(),repo.getPersons(),uniRepo.getFaculties(),uniRepo.getDepartments());

        try {
            mapper.writeValue(Files.newOutputStream(SAVE_FILE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), snapshot);
            System.out.println("Базу даних успішно збережено у " + SAVE_FILE);
        } catch (IOException e) {
            System.err.println("Помилка запису JSON: " + e.getMessage());
        }
    }

    public void loadDatabase(Repository repo, UniversityRepository uniRepo) throws IOException {
        if (Files.notExists(SAVE_FILE)) {
            Files.createFile(SAVE_FILE);
        } else {
            try {
                SnapshotService snapshot = mapper.readValue(Files.newInputStream(SAVE_FILE), SnapshotService.class);

                if (snapshot.students != null) snapshot.students.forEach(repo::addStudent);
                if (snapshot.teachers != null) snapshot.teachers.forEach(repo::addTeacher);
                if (snapshot.faculties != null) snapshot.faculties.forEach(uniRepo::addFaculty);
                if (snapshot.departments != null) snapshot.departments.forEach(uniRepo::addDepartment);

                System.out.println("Дані успішно завантажено з JSON!");
            } catch (Exception e) {
                System.err.println("Помилка читання JSON: " + e.getMessage());
            }
        }
    }
}