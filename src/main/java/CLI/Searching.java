package CLI;



import DigiPackage.*;



import java.util.Optional;



public interface Searching {

     static Optional<Person> findByEmail(String email, Person[] people) {

        for (Person p : people) {

            if (p != null && p.getEmail().equals(email)) {

                return Optional.of(p);

            }

        }

        return Optional.empty();

    }

   static Optional<Person> findByPhone(String phone, Person[] people) {

        for (Person p : people) {

            if (p != null && p.getPhone().equals(phone)) {

                return Optional.of(p);

            }

        }

        return Optional.empty();

    }

    static Optional<Person> findByName(String name, Person[] people) {

        for (Person p : people) {

            if (p != null && p.getName().equals(name)) {

                return Optional.of(p);

            }

        }

        return Optional.empty();

    }

    static Student[] findByGroup(String group, Student[] students) {

        Student[] groupOfStudents=new Student[100];

        int studentCounter=0;

        for (Student s : students) {

            if (s != null && s.getGroup().equals(group)) {

                groupOfStudents[studentCounter]=s;

                studentCounter++;

            }

        }

        return groupOfStudents;

    }

    static Student[] findByCourse(int course, Student[] students) {

        Student[] groupOfStudents=new Student[100];

        int studentCounter=0;

        for (Student s : students) {

            if (s != null && s.getCourse()==course) {

                groupOfStudents[studentCounter]=s;

                studentCounter++;

            }

        }

        return groupOfStudents;

    }

}