package CLI;

import DigiPackage.Person;
import DigiPackage.Student;
import DigiPackage.Teacher;
import exceptions.EntityAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
    public  final List<Student> students = new ArrayList<>();
    public  final List<Person> persons = new ArrayList<>();
    public final List<Teacher> teachers = new ArrayList<>();
    private final Map<String,Teacher> teachersmap = new HashMap<>();
    private final Map<String,Student> studentmap = new HashMap<>();
    private final Map<String,Person> personmap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(Repository.class);
    public void addStudent(Student student) {
        log.debug("Adding student");
        if (studentmap.containsKey(student.getId())) {
            throw new EntityAlreadyExistsException("Student with id " + student.getId() + " already exists");
        }
        students.add(student);
        persons.add(student);
        studentmap.put(student.getId(), student);
        personmap.put(student.getId(), student);
        log.info("Added student with id " + student.getId());
    }
    public void removeStudent(Student student) {
        log.debug("Removing student");
        students.remove(student);
        persons.remove(student);
        studentmap.remove(student.getId());
        personmap.remove(student.getId());
        log.info("Removed student with id " + student.getId());
    }
    public void  removeTeacher(Teacher teacher) {
        log.debug("Removing teacher");
        teachers.remove(teacher);
        persons.remove(teacher);
        teachersmap.remove(teacher.getId());
        personmap.remove(teacher.getId());
        log.info("Removed teacher with id " + teacher.getId());

    }

    public Map<String, Teacher> getTeachersmap() {
        return teachersmap;
    }

    public Map<String, Student> getStudentmap() {
        return studentmap;
    }

    public Map<String, Person> getPersonmap() {
        return personmap;
    }

    public void  addTeacher(Teacher teacher) {
        log.debug("Adding teacher");
        if (teachersmap.containsKey(teacher.getId())) {
            throw new EntityAlreadyExistsException("Student with id " +teacher.getId() + " already exists");
        }
        teachers.add(teacher);
        persons.add(teacher);
        teachersmap.put(teacher.getId(),teacher);
        personmap.put(teacher.getId(), teacher);
        log.info("Added teacher with id " + teacher.getId());
    }
    public List<Student> getStudents() {
        return students;
    }
    public List<Teacher> getTeachers() {
        return teachers;
    }
    public List<Person> getPersons() {
        return persons;
    }


}
