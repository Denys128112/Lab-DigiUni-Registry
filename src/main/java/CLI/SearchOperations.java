package CLI;

import DigiPackage.Person;
import DigiPackage.Student;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SearchOperations {
    Scanner sc = new Scanner(System.in);
    Searching searching = new Searching();
    private  void searchName(List<? extends Person> humans) {
        System.out.println("Введіть ПІБ у форматі ПРІЗВИЩЕ ІМ'Я ПО-БАТЬКОВІ");
        String name = sc.nextLine();

        Optional<Person> maybePerson = searching.findByName(name, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new IllegalArgumentException("Користувача з таким іменем не знайдено"));
            System.out.println(person);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
    }

    private  void searchEmail(List<? extends Person> humans) {
        System.out.println("Введіть email:");
        String email = sc.nextLine();

        Optional<Person> maybePerson = searching.findByEmail(email, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new IllegalArgumentException("Користувача з таким email не знайдено"));
            System.out.println(person);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private  void searchPhone(List<? extends Person> humans) {
        System.out.println("Введіть номер телефону без +380:");
        String phone = "+380"+sc.nextLine();

        Optional<Person> maybePerson = searching.findByPhone(phone, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new IllegalArgumentException("Користувача з таким телефоном не знайдено"));
            System.out.println(person);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private  void searchGroup(Repository repository) {
        System.out.println("Введіть групу(наприклад ІПЗ-1):");
        String group = sc.nextLine();

        List<Student> maybeStudents = searching.findByGroup(group, repository) ;
        try{
            if (maybeStudents.isEmpty()) {
                throw new IllegalArgumentException("На групі " + group + " студентів не знайдено або введенні дані не коректні");
            }
            System.out.println("На групі " + group + " студентів знайдено:");
            for (Student s : maybeStudents) {
                if (s != null) System.out.println(s);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private  void searchCourse(Repository repository) {
        System.out.println("Введіть курс:");
        int course = sc.nextInt();
        sc.nextLine();
        List<Student> maybeStudents = searching.findByCourse(course, repository);
        try{
            if (maybeStudents.isEmpty()) {
                throw new IllegalArgumentException("На " + course + " курсі студентів не знайдено або введенні дані не коректні");
            }
            System.out.println("На  " + course + " курсі студентів знайдено:");
            for (Student s : maybeStudents) {
               System.out.println(s);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


}

