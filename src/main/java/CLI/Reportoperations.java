package CLI;

import DigiPackage.Person;
import DigiPackage.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static CLI.InputHelper.getIntInput;

public class Reportoperations {
    private static final Logger log = LoggerFactory.getLogger(Reportoperations.class);
    public void studentByCourse(List<Student> students) {
        log.debug("sort studentByCourse");
        if (students == null || students.isEmpty()) {
            System.out.println("Список студентів порожній.");
            log.warn("list is empty");
            return;
        }

        System.out.println("\n--- Студенти відсортовані за курсом ---");

        students.stream()
                .sorted(Comparator.comparingInt(Student::getCourse))
                .forEach(s -> System.out.println("Курс: " + s.getCourse() + " | " + s.getName() + " | Група: " + s.getGroup()));
        log.info("sorted studentByCourse");
    }
    public List<Person> personsByAlpabhet(List<? extends Person> personList){
        log.debug("sort personsByAlpabhet");
        if (personList == null || personList.isEmpty()) {
            System.out.println("Список порожній.");
            log.warn("list is empty");
            return null;
        }
        List<Person> personList1 = new ArrayList<>(personList);
        personList1.sort((p1,p2)->p1.getName().compareTo(p2.getName()));
        log.info("sorted personsByAlpabhet");
        return personList1;
    }
    public void filterStudentsByCourse(List<Student> students){
        if (students == null || students.isEmpty()) {
            System.out.println("Список студентів порожній.");
            log.warn("list is empty");
        }
        else {
            int course = getIntInput("Оберіть курс1",1,4);
            students.stream()
                    .filter(s->s.getCourse()==course).forEach(System.out::println);
        }
    }
}
