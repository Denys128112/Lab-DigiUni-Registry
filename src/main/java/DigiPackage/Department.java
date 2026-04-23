package DigiPackage;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import exceptions.EntityAlreadyExistsException;
import exceptions.ValidatingException;

import java.util.*;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "code")
public class Department {
    private String code;
    private String name;
    private Faculty faculty;
    private Teacher head;
    private String location;
    @com.fasterxml.jackson.annotation.JsonIgnore
    private final Set<Person> personsOfDepartment=new HashSet<>();
    @com.fasterxml.jackson.annotation.JsonIgnore
    private final Set<Teacher> teachersOfDepartment=new HashSet<>();
    @com.fasterxml.jackson.annotation.JsonIgnore
    private final Set<Student> studentsOfDepartment=new HashSet<>();
    public Department(){

    }
    public Department(String code, String name, Faculty faculty, Teacher head, String location) {
        setCode(code);
        setName(name);
        setFaculty(faculty);
        setHead(head);
        setLocation(location);
    }

    public String getCode() { return code; }
    public void setCode(String code) {
        if (code != null && !code.trim().isEmpty() && code.matches("^[A-Z]\\d{2}$")) this.code = code;
        else throw new ValidatingException("Department code cannot be empty");
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) this.name = name;
        else throw new ValidatingException("Department name cannot be empty");
    }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) {
        if (faculty != null) {
            this.faculty = faculty;
            faculty.addDepartmentTofaculty(this);
        }
        else throw new ValidatingException("Faculty cannot be null");
    }

    public Teacher getHead() { return head; }
    public void setHead(Teacher head) {
        if (head != null) this.head = head;
        else throw new ValidatingException("Head of department cannot be null");
    }

    public String getLocation() { return location; }
    public void setLocation(String location) {
        if (location != null && !location.trim().isEmpty()) this.location = location;
        else throw new ValidatingException("Location cannot be empty");
    }

    @Override
    public String toString() {
        return "Department: " + name + " (" + code + "), Faculty: " + faculty.getShortName() + ", Head: " + head.getName()+ ", Location: " + location;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (o == this) return true;
        Department that = (Department) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    public List<Person> getPersonsOfDepartment() {
        return new ArrayList<>(personsOfDepartment);
    }

    public List<Teacher> getTeachersOfDepartment() {
        return new ArrayList<>(teachersOfDepartment);
    }

    public List<Student> getStudentsOfDepartment() {
        return new ArrayList<>(studentsOfDepartment);
    }
    public void addTeacherToDepartment(Teacher t){
        if(teachersOfDepartment.contains(t))
            throw new EntityAlreadyExistsException("Вчитель вже існує");
        teachersOfDepartment.add(t);
        personsOfDepartment.add(t);
    }
    public void addStudentToDepartment(Student s){
        if(studentsOfDepartment.contains(s))
            throw new EntityAlreadyExistsException("Студент вже існує");
        studentsOfDepartment.add(s);
        personsOfDepartment.add(s);
    }

    public void deleteTeacherFromDepartment(Teacher t) {
        teachersOfDepartment.remove(t);
        personsOfDepartment.remove(t);
    }
    public void deleteStudentFromDepartment(Student s){
        studentsOfDepartment.remove(s);
        personsOfDepartment.remove(s);
    }
}