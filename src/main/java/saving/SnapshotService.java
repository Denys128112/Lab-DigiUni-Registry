package saving;
import DigiPackage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnapshotService {
    public List<Student> students;
    public List<Teacher> teachers;
    public List<Person> persons;
    public List<Faculty> faculties;
    public List<Department> departments;

    public SnapshotService () {
    }

    public SnapshotService(List<Student> students, List<Teacher> teachers, List<Person> persons, List<Faculty> faculties, List<Department> departments) {
        this.students = students;
        this.teachers = teachers;
        this.persons = persons;
        this.faculties = faculties;
        this.departments = departments;

    }
}