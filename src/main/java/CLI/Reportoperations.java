package CLI;

import DigiPackage.Student;
import java.util.Comparator;
import java.util.List;

public class Reportoperations {

    public void studentByCourse(List<Student> students) {
        if (students == null || students.isEmpty()) {
            System.out.println("Список студентів порожній.");
            return;
        }

        System.out.println("\n--- Студенти відсортовані за курсом ---");

        students.stream()
                .sorted(Comparator.comparingInt(Student::getCourse))
                .forEach(s -> System.out.println("Курс: " + s.getCourse() + " | " + s.getName() + " | Група: " + s.getGroup()));
    }
}
