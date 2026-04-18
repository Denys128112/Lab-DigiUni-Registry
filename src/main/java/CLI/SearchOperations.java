package CLI;

import DigiPackage.Person;
import DigiPackage.Student;
import exceptions.EmptySearchResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SearchOperations {
    Scanner sc = new Scanner(System.in);
    Searching searching = new Searching();
    private static final Logger log = LoggerFactory.getLogger(SearchOperations.class);
    protected   void searchName(List<? extends Person> humans) {
        log.debug("search by name");
        System.out.println("Введіть ПІБ у форматі ПРІЗВИЩЕ ІМ'Я ПО-БАТЬКОВІ");
        String name = sc.nextLine();
        Optional<? extends Person> maybePerson = searching.findByName(name, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new EmptySearchResultException("Користувача з таким іменем не знайдено"));
            System.out.println(person);
            log.info("search completed: {}",person);
        } catch (EmptySearchResultException e) {
            System.out.println(e.getMessage());
            log.warn("search is empty");
        }
    }

    protected   void searchEmail(List<? extends Person> humans) {
        log.debug("search by email");
        System.out.println("Введіть email:");
        String email = sc.nextLine();

        Optional<? extends Person> maybePerson = searching.findByEmail(email, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new EmptySearchResultException("Користувача з таким email не знайдено"));
            System.out.println(person);
            log.info("search completed: {}",person);
        } catch (EmptySearchResultException e) {
            System.out.println(e.getMessage());
            log.warn("search is empty");
        }
    }

    protected   void searchPhone(List<? extends Person> humans) {
        log.debug("search by phone");
        System.out.println("Введіть номер телефону без +380:");
        String phone = "+380"+sc.nextLine();

        Optional<? extends Person> maybePerson = searching.findByPhone(phone, humans);
        try {
            Person person = maybePerson.orElseThrow(() -> new EmptySearchResultException("Користувача з таким телефоном не знайдено"));
            System.out.println(person);
            log.info("search completed: {}",person);
        } catch (EmptySearchResultException e) {
            System.out.println(e.getMessage());
            log.warn("search is empty");
        }
    }

    protected   void searchGroup(Repository repository) {
        log.debug("search by group");
        System.out.println("Введіть групу(наприклад ІПЗ-1):");
        String group = sc.nextLine();
        try{
        List<Student> maybeStudents = searching.findByGroup(group, repository) ;

            System.out.println("На групі " + group + " студентів знайдено:");
            for (Student s : maybeStudents) {
                 System.out.println(s);
            }
            log.info("search completed: {}",maybeStudents);
        } catch (EmptySearchResultException e) {
            System.out.println(e.getMessage());
            log.warn("search is empty");
        }
    }

   protected   void searchCourse(Repository repository) {
        log.debug("search by course");
        System.out.println("Введіть курс:");
        int course = sc.nextInt();
        sc.nextLine();
       try{
        List<Student> maybeStudents = searching.findByCourse(course, repository);
            System.out.println("На  " + course + " курсі студентів знайдено:");
            for (Student s : maybeStudents) {
               System.out.println(s);
            }
            log.info("search completed: {}",maybeStudents);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            log.warn("search is empty");
        }
    }



}

