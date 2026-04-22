import CLI.Reportoperations;
import CLI.Repository;
import CLI.Searching;
import CLI.UniversityRepository;
import DigiPackage.*;
import exceptions.EmptySearchResultException;
import exceptions.ValidatingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CLITest {
    @Test
    public void searchTest(){
        Repository repository = new Repository();
        Searching searching = new Searching();
        Student student = new Student("2026-01", "Денис Колісник Максимович", "11.03.2007", "d.kolisnyk@ukma.edu.ua", "+380991234567", "К 123/45 бп", 1, "ІПЗ-1", 2025, FormOfStudy.Budget, Status.Studying);
        Student nullstudent = null;
        List<Student> students=List.of(student);
        repository.addStudent(student);
        assertEquals(Optional.of(student),searching.findById("2026-01",repository.getStudentmap()));
        assertEquals(Optional.empty(),searching.findById("2026-00",repository.getStudentmap()));
        assertEquals(Optional.of(student),searching.findByName("Денис Колісник Максимович",repository.getStudents()));
        assertEquals(Optional.of(student),searching.findByEmail("d.kolisnyk@ukma.edu.ua",repository.getStudents()));
        assertEquals(students,searching.findByCourse(1,repository));
        assertEquals(students,searching.findByGroup("ІПЗ-1",repository));
        assertThrows(EmptySearchResultException.class,()->searching.findByCourse(3,repository) );
        assertThrows(EmptySearchResultException.class,()->searching.findByGroup("ІПЗ-6",repository) );
        assertEquals(Optional.of(student), searching.findByPhone("+380991234567", repository.getStudents()));
    }
    @Test
    public void removeTest(){
        Repository repository = new Repository();
        Student student = new Student("2026-01", "Денис Колісник Максимович", "11.03.2007", "d.kolisnyk@ukma.edu.ua", "+380991234567", "К 123/45 бп", 1, "ІПЗ-1", 2025, FormOfStudy.Budget, Status.Studying);
        Teacher testTeacher = new Teacher("T-2026-01", "Олександр Науменко Максимович", "15.05.1975", "naumenko.o@ukma.edu.ua", "+380501234567", Position.Profesor, ScientificDegree.PHD, AcademicTitle.Profesor, "01.09.2010", 1.0, 600);
        Faculty testFaculty = new Faculty("FI-01", "Факультет інформатики", "ФІ", testTeacher, "fi@ukma.edu.ua");
        Department testDept = new Department("DI-202", "Кафедра інформатики", testFaculty, testTeacher, "4-й корпус, 202 ауд.");
        Searching searching = new Searching();
        UniversityRepository universityRepository = new UniversityRepository();
        universityRepository.addDepartment(testDept);
        universityRepository.addFaculty(testFaculty);
        universityRepository.removeDepartment(testDept);
        universityRepository.removeFaculty(testFaculty);
        assertEquals(Optional.empty(),searching.getFacultyByCode("FI-01",universityRepository.getFacultiesMap()));
        assertTrue(universityRepository.getFaculties().isEmpty());
        assertEquals(Optional.empty(),searching.getDepartmentByCode("DI-202",universityRepository.getDepartmentMap()));
        assertTrue(universityRepository.getDepartments().isEmpty());
        assertTrue(repository.getTeachers().isEmpty());
        repository.addTeacher(testTeacher);
        repository.addStudent(student);
        repository.removeTeacher(testTeacher);
        assertEquals(Optional.empty(),searching.findById("Т-2026-01",repository.getTeachersmap()));
        assertTrue(repository.getTeachers().isEmpty());
        repository.removeStudent(student);
        assertEquals(Optional.empty(), searching.findById("2026-01", repository.getStudentmap()));
        assertTrue(repository.getStudents().isEmpty());
    }
    @ParameterizedTest
    @ValueSource(strings = {"Іванов Іван", "Іванов", "Іванов Іван Іванович Петрович", "   "})
    @NullAndEmptySource
    public void studentInvalidNameValidationTest(String invalidName) {
        Student st = new Student();
        assertThrows(ValidatingException.class, () -> st.setName(invalidName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"15-05-2000", "2000.05.15", "01/01/2005", "Сьогодні"})
    public void studentInvalidDatesValidationTest(String invalidDate) {
        Student st = new Student();
        assertThrows(ValidatingException.class, () -> st.setDateOfBirth(invalidDate));
    }

    @ParameterizedTest
    @ValueSource(strings = {"testemail.com", "user@domain", "@ukma.edu.ua", "   "})
    public void studentInvalidContactsValidationTest(String invalidContact) {
        Student st = new Student();
        assertThrows(ValidatingException.class, () -> st.setEmail(invalidContact));
        assertThrows(ValidatingException.class, () -> st.setPhone("+38050123456")); // Твій старий приклад
        assertThrows(ValidatingException.class, () -> st.setPhone("0991234567"));   // Без +38
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 7, -1, 100})
    public void studentInvalidNumericFieldsTest(int invalidCourse) {
        Student st = new Student();
        assertThrows(ValidatingException.class, () -> st.setCourse(invalidCourse));
        assertThrows(ValidatingException.class, () -> st.setYearOfentering(2018)); // Твоя логіка занадто старого року
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678901", "АБВГД", "КН92"})
    public void studentInvalidAcademicInfoTest(String invalidData) {
        Student st = new Student();
        assertThrows(ValidatingException.class, () -> st.setIdOfRecordBook(invalidData));
        assertThrows(ValidatingException.class, () -> st.setGroup(invalidData));
    }
    @Test
    public void javaTimeApiTest() {
        Student student = new Student();

        assertThrows(ValidatingException.class, () -> student.setDateOfBirth("01.01.2099"));
        assertThrows(ValidatingException.class, () -> student.setDateOfBirth("31.12.1945"));
        assertDoesNotThrow(() -> student.setDateOfBirth("01.01.2005"));
        assertEquals("01.01.2005", student.getDateOfBirth());

        Teacher teacher = new Teacher();

        assertThrows(ValidatingException.class, () -> teacher.setDateOfentering("31.12.1991"));
        assertDoesNotThrow(() -> teacher.setDateOfentering("15.08.2020"));
        assertEquals("15.08.2020", teacher.getDateOfentering());
    }
    @ParameterizedTest
    @ValueSource(doubles = {2.0, 3.5, -1.0, 0.0})
    public void teacherInvalidRateTest(double invalidRate) {
        Teacher t = new Teacher();
        assertThrows(ValidatingException.class, () -> t.setRate(invalidRate));
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 1001})
    public void teacherInvalidWorkloadTest(int invalidWorkload) {
        Teacher t = new Teacher();
        assertThrows(ValidatingException.class, () -> t.setWorkload(invalidWorkload));
    }
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "}) // Додаємо перевірку на рядок, що складається лише з пробілів
    public void facultyInvalidStringsTest(String invalidString) {
        Faculty f = new Faculty();
        assertThrows(ValidatingException.class, () -> f.setCode(invalidString));
        assertThrows(ValidatingException.class, () -> f.setName(invalidString));
        assertThrows(ValidatingException.class, () -> f.setShortName(invalidString));
        assertThrows(ValidatingException.class, () -> f.setContacts(invalidString));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    public void departmentInvalidStringsTest(String invalidString) {
        Department d = new Department();
        assertThrows(ValidatingException.class, () -> d.setCode(invalidString));
        assertThrows(ValidatingException.class, () -> d.setName(invalidString));
        assertThrows(ValidatingException.class, () -> d.setLocation(invalidString));
    }

    @Test
    public void nullObjectReferencesTest() {
        Faculty f = new Faculty();
        Department d = new Department();

        assertThrows(ValidatingException.class, () -> f.setDean(null));
        assertThrows(ValidatingException.class, () -> d.setHead(null));
        assertThrows(ValidatingException.class, () -> d.setFaculty(null));
    }


    @Test
    public void repositoryCollectionsTest() {
        Repository repo = new Repository();

        assertTrue(repo.getStudents().isEmpty());
        assertTrue(repo.getStudentmap().isEmpty());

        Student s1 = new Student("ID1", "Іван Іванов Іванович", "01.01.2000", "ivan@ukma.edu.ua", "+380501111111", "К 111/11 бп", 1, "ІПЗ-1", 2023, FormOfStudy.Budget, Status.Studying);
        Student s2 = new Student("ID2", "Петро Петров Петрович", "02.02.2001", "petro@ukma.edu.ua", "+380502222222", "К 222/22 бп", 2, "ІПЗ-2", 2022, FormOfStudy.Contract, Status.Studying);

        repo.addStudent(s1);
        repo.addStudent(s2);

        assertEquals(2, repo.getStudents().size());
        assertEquals(2, repo.getStudentmap().size());

        assertEquals(s1, repo.getStudentmap().get("ID1"));
        assertEquals(s2, repo.getStudentmap().get("ID2"));
    }
    @Test
    public void addToFacultyTest() {
        Faculty f = new Faculty();
        Department d = new Department();
        Student s1 = new Student("ID1", "Іван Іванов Іванович", "01.01.2000", "ivan@ukma.edu.ua", "+380501111111", "К 111/11 бп", 1, "ІПЗ-1", 2023, FormOfStudy.Budget, Status.Studying);
        f.addDepartmentTofaculty(d);
        d.addStudentToDepartment(s1);
        List <Student> students =f.getStudentsOfFaculty();
        assertEquals(s1.getName(), students.get(0).getName());
    }
    @Test
    public void sortPersonsByAlphabetTest() {
        Reportoperations reports = new Reportoperations();
        Student s1 = new Student(); s1.setName("Яковлєв Яків Якович");
        Student s2 = new Student(); s2.setName("Андрієнко Андрій Андрійович");
        Student s3 = new Student(); s3.setName("Борисенко Борис Борисович");

        List<Person> unsorted = List.of(s1, s2, s3);
        List<Person> sorted = reports.personsByAlpabhet(unsorted);

        assertEquals("Андрієнко Андрій Андрійович", sorted.get(0).getName());
        assertEquals("Борисенко Борис Борисович", sorted.get(1).getName());
        assertEquals("Яковлєв Яків Якович", sorted.get(2).getName());
    }
    @Test
    public void sortStudentsByCoursesTest() {
        Reportoperations reports = new Reportoperations();
        Student s1 = new Student();
        s1.setName("Яковлєв Яків Якович");
        Student s2 = new Student();
        s2.setName("Андрієнко Андрій Андрійович");
        Student s3 = new Student();
        s3.setName("Борисенко Борис Борисович");
        s1.setCourse(2);
        s2.setCourse(1);
        s3.setCourse(3);
        List<Student> unsorted = List.of(s1, s2, s3);
        List<Student> sorted = reports.studentByCourse(unsorted);

        assertEquals("Андрієнко Андрій Андрійович", sorted.get(0).getName());
        assertEquals("Борисенко Борис Борисович", sorted.get(2).getName());
        assertEquals("Яковлєв Яків Якович", sorted.get(1).getName());
    }
    @Test
    public void filterStudentsByCoursesTest() {
        Reportoperations reports = new Reportoperations();
        Student s1 = new Student();
        s1.setName("Яковлєв Яків Якович");
        Student s2 = new Student();
        s2.setName("Андрієнко Андрій Андрійович");
        Student s3 = new Student();
        s3.setName("Борисенко Борис Борисович");
        s1.setCourse(2);
        s2.setCourse(1);
        s3.setCourse(3);
        List<Student> unsorted = List.of(s1, s2, s3);
        List<Student> filter1 = reports.filterStudentsByCourse(unsorted,1);
        List<Student> filter2 = reports.filterStudentsByCourse(unsorted,2);
        List<Student> filter3 = reports.filterStudentsByCourse(unsorted,3);

        assertEquals("Андрієнко Андрій Андрійович", filter1.get(0).getName());
        assertEquals("Борисенко Борис Борисович", filter3.get(0).getName());
        assertEquals("Яковлєв Яків Якович", filter2.get(0).getName());
    }

}
