package CLI;

import DigiPackage.Person;
import DigiPackage.Student;
import exceptions.EmptySearchResultException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SearchOperations {
    Scanner sc = new Scanner(System.in);
    Searching searching = new Searching();
    protected   void searchName(List<? extends Person> humans) {
        System.out.println("Введіть ПІБ у форматі ПРІЗВИЩЕ ІМ'Я ПО-БАТЬКОВІ");
        String name = sc.nextLine();

        Optional<? extends Person> maybePerson = searching.findByName(name, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new EmptySearchResultException("Користувача з таким іменем не знайдено"));
            System.out.println(person);
        } catch (EmptySearchResultException e) {
            System.out.println(e.getMessage());

        }
    }

    protected   void searchEmail(List<? extends Person> humans) {
        System.out.println("Введіть email:");
        String email = sc.nextLine();

        Optional<? extends Person> maybePerson = searching.findByEmail(email, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new EmptySearchResultException("Користувача з таким email не знайдено"));
            System.out.println(person);
        } catch (EmptySearchResultException e) {
            System.out.println(e.getMessage());
        }
    }

    protected   void searchPhone(List<? extends Person> humans) {
        System.out.println("Введіть номер телефону без +380:");
        String phone = "+380"+sc.nextLine();

        Optional<? extends Person> maybePerson = searching.findByPhone(phone, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new EmptySearchResultException("Користувача з таким телефоном не знайдено"));
            System.out.println(person);
        } catch (EmptySearchResultException e) {
            System.out.println(e.getMessage());
        }
    }

    protected   void searchGroup(Repository repository) {
        System.out.println("Введіть групу(наприклад ІПЗ-1):");
        String group = sc.nextLine();
        try{
        List<Student> maybeStudents = searching.findByGroup(group, repository) ;

            System.out.println("На групі " + group + " студентів знайдено:");
            for (Student s : maybeStudents) {
                 System.out.println(s);
            }
        } catch (EmptySearchResultException e) {
            System.out.println(e.getMessage());
        }
    }

   protected   void searchCourse(Repository repository) {
        System.out.println("Введіть курс:");
        int course = sc.nextInt();
        sc.nextLine();
       try{
        List<Student> maybeStudents = searching.findByCourse(course, repository);
            System.out.println("На  " + course + " курсі студентів знайдено:");
            for (Student s : maybeStudents) {
               System.out.println(s);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    public void searchById(List<? extends Person> humans) {

    }

    public void findById(List<Student> students) {
    }
}

