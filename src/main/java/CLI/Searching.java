package CLI;



import DigiPackage.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;



public class Searching {

     public Optional<Person> findByEmail(String email, List<? extends Person> people) {

        for (Person p : people) {

            if (p != null && p.getEmail().equals(email)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

   public  Optional<Person> findByPhone(String phone, List<? extends Person> people) {

        for (Person p : people) {
            if (p != null && p.getPhone().equals(phone)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();

    }

    public Optional<Person> findByName(String name,  List<? extends Person> people) {
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
            throw new IllegalArgumentException("No student with group "+group);
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
            throw new IllegalArgumentException("No student with course "+course);
        return result;
    }
}