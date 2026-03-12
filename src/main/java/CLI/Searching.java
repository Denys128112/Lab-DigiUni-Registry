package CLI;



import DigiPackage.*;
import exceptions.EmptySearchResultException;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;



public class Searching {

     public Optional<?extends Person> findByEmail(String email, List<? extends Person> people) {

        for (Person p : people) {

            if (p != null && p.getEmail().equals(email)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

   public  Optional<? extends Person> findByPhone(String phone, List<? extends Person> people) {

        for (Person p : people) {
            if (p != null && p.getPhone().equals(phone)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();

    }

    public Optional<? extends Person> findByName(String name,  List<? extends Person> people) {
        for (Person p : people) {
            if (p != null && p.getName().equals(name)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public List<Student> findByGroup(String group, Repository repository) {
        List<Student> students= repository.getStudents();
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getGroup().equals(group)) {
                result.add(s);
            }
        }
        if(result.isEmpty())
            throw new EmptySearchResultException("No student with group "+group);
        return result;
    }

    public List<Student> findByCourse(int course, Repository repository) {
         List<Student> students= repository.getStudents();
         List<Student> result = new ArrayList<>();

        for (Student s : students) {
            if (s.getCourse() == course) {
                result.add(s);
            }
        }
        if(result.isEmpty())
            throw new EmptySearchResultException("No student with course "+course);
        return result;
    }

    public Optional<? extends Person> findById(String s, Map<String,? extends Person> personsbyid) {
         return Optional.ofNullable(personsbyid.get(s));
    }
    public Optional<Department> getDepartmentByCode(String code,Map<String,Department> departments) {
       return Optional.ofNullable(departments.get(code));
    }
    public Optional<Faculty> getFacultyByCode(String code,Map<String,Faculty> faculties) {
         return Optional.ofNullable(faculties.get(code));
    }
    }

