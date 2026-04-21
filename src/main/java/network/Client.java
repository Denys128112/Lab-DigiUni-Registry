package network;

import DigiPackage.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Client {
    Logger log = LoggerFactory.getLogger(Server.class);
    public void accept(){
    ObjectMapper mapper = new ObjectMapper();

        try (Socket socket = new Socket("localhost", 5555);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            String studentsJson = in.readLine();
            String teachersJson = in.readLine();
            String facultiesJson = in.readLine();
            String departmentsJson = in.readLine();

            List<Student> students = mapper.readValue(studentsJson, new TypeReference<List<Student>>() {});
            List<Teacher> teachers = mapper.readValue(teachersJson, new TypeReference<List<Teacher>>() {});
            List<Faculty> faculties = mapper.readValue(facultiesJson, new TypeReference<List<Faculty>>() {});
            List<Department> departments = mapper.readValue(departmentsJson, new TypeReference<List<Department>>() {});

            mapper.writeValue(Files.newOutputStream(Path.of(".\\resources\\Students.json"),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), students);
            mapper.writeValue(Files.newOutputStream(Path.of(".\\resources\\Teachers.json"),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), teachers);
            mapper.writeValue(Files.newOutputStream(Path.of(".\\resources\\Faculties.json"),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), faculties);
            mapper.writeValue(Files.newOutputStream(Path.of(".\\resources\\Departments.json"),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING), departments);

        } catch (Exception e) {
           log.error("Error while accepting data from server {}",e.getMessage());
        }
    }
}
