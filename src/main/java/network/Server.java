package network;


import CLI.Repository;
import CLI.UniversityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {
    Logger log = LoggerFactory.getLogger(Server.class);
    public void startServer(Repository repo, UniversityRepository uniRepo) throws IOException {
        log.debug("Starting Server");
        System.out.println("Ви почали сервер");
        ObjectMapper tcpMapper = new ObjectMapper();
        try (ServerSocket server = new ServerSocket(5555)) {
            while (true) {
                try (Socket socket = server.accept();
                     BufferedWriter out = new BufferedWriter(
                             new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                    log.info("Клієнт підключився: {}", socket.getInetAddress());

                    String studentsJson = tcpMapper.writeValueAsString(repo.getStudents());
                    String teachersJson = tcpMapper.writeValueAsString(repo.getTeachers());
                    String facultiesJson = tcpMapper.writeValueAsString(uniRepo.getFaculties());
                    String departmentsJson = tcpMapper.writeValueAsString(uniRepo.getDepartments());

                    out.write(studentsJson + "\n");
                    out.write(teachersJson + "\n");
                    out.write(facultiesJson + "\n");
                    out.write(departmentsJson + "\n");

                    out.flush();
                    log.info("All for 4 jsons was sent to client");
                } catch (Exception e) {
                    log.error("Error : {}", e.getMessage());
                }
            }
        }
    }}


