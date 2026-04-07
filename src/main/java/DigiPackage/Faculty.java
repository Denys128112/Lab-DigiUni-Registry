package DigiPackage;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import exceptions.EntityAlreadyExistsException;
import exceptions.ValidatingException;

import java.util.*;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "code")
public class Faculty {
    private String code;
    private String name;
    private String shortName;
    private Teacher dean;
    private String contacts;
    private final Set<Department> departmentsOfFaculty=new HashSet<>();

    public Faculty(){

    }
    public Faculty(String code, String name, String shortName, Teacher dean, String contacts) {
        setCode(code);
        setName(name);
        setShortName(shortName);
        setDean(dean);
        setContacts(contacts);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code != null && !code.trim().isEmpty()) this.code = code;
        else throw new ValidatingException("Faculty code cannot be empty");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) this.name = name;
        else throw new ValidatingException("Faculty name cannot be empty");
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        if (shortName != null && !shortName.trim().isEmpty()) this.shortName = shortName;
        else throw new ValidatingException("Faculty short name cannot be empty");
    }

    public Teacher getDean() {
        return dean;
    }

    public void setDean(Teacher dean) {
        if (dean != null) this.dean = dean;
        else throw new ValidatingException("Dean cannot be null");
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        if (contacts != null && !contacts.trim().isEmpty()) this.contacts = contacts;
        else throw new ValidatingException("Contacts cannot be empty");
    }

    @Override
    public String toString() {
        return "Faculty [" + code + "]: " + name + " (" + shortName + "), Dean: " + dean.getName()+"Contacts: "+contacts+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (o == this) return true;
        Faculty faculty = (Faculty) o;
        return Objects.equals(code, faculty.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    public List<Student> getStudentsOfFaculty() {
        List<Student> studentsOfFaculty=new ArrayList<>();
        for(Department d: departmentsOfFaculty){
            studentsOfFaculty.addAll(d.getStudentsOfDepartment());
        }
        return studentsOfFaculty;
    }
    public List<Teacher> getTeachersOfFaculty() {
        List<Teacher> teachersOfFaculty=new ArrayList<>();
        for(Department d: departmentsOfFaculty){
            teachersOfFaculty.addAll(d.getTeachersOfDepartment());
        }
        return teachersOfFaculty;
    }
    public List<Person> getPersonsOfFaculty() {
        List<Person> personsOfFaculty=new ArrayList<>();
        for(Department d: departmentsOfFaculty){
            personsOfFaculty.addAll(d.getTeachersOfDepartment());
        }
        return personsOfFaculty;
    }
    public void addDepartmentTofaculty(Department d) {
        if(departmentsOfFaculty.contains(d))
            throw new EntityAlreadyExistsException("Кафедра вже існує");
       departmentsOfFaculty.add(d);
    }
}