package CLI;

import DigiPackage.Person;
import DigiPackage.Student;
import DigiPackage.Teacher;
import exceptions.EntityAlreadyExistsException;

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

    public void addStudent(Student student) {
        if (studentmap.containsKey(student.getId())) {
            throw new EntityAlreadyExistsException("Student with id " + student.getId() + " already exists");
        }
        students.add(student);
        persons.add(student);
        studentmap.put(student.getId(), student);
        personmap.put(student.getId(), student);
    }
    public void removeStudent(Student student) {
        students.remove(student);
        persons.remove(student);
        studentmap.remove(student.getId());
        personmap.remove(student.getId());
    }
    public void  removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
        persons.remove(teacher);
        teachersmap.remove(teacher.getId());
        personmap.remove(teacher.getId());

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
        if (teachersmap.containsKey(teacher.getId())) {
            throw new EntityAlreadyExistsException("Student with id " +teacher.getId() + " already exists");
        }
        teachers.add(teacher);
        persons.add(teacher);
        teachersmap.put(teacher.getId(),teacher);
        personmap.put(teacher.getId(), teacher);
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
