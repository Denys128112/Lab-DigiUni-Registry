import CLI.Repository;
import CLI.Searching;
import CLI.UniversityRepository;
import DigiPackage.*;
import exceptions.ValidatingException;
import org.junit.jupiter.api.Test;

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
        assertThrows(IllegalArgumentException.class,()->searching.findByCourse(3,repository) );
        assertThrows(IllegalArgumentException.class,()->searching.findByGroup("ІПЗ-6",repository) );
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
    @Test
    public void validationTest(){
        Student st = new Student();
        Teacher t = new Teacher();
        Faculty f = new Faculty();
        Department d = new Department();

        assertThrows(ValidatingException.class, () -> st.setName("Іванов Іван"));
        assertThrows(ValidatingException.class, () -> st.setDateOfBirth("15-05-2000"));
        assertThrows(ValidatingException.class, () -> st.setEmail("testemail.com"));
        assertThrows(ValidatingException.class, () -> st.setPhone("+38050123456"));

        assertThrows(ValidatingException.class, () -> st.setIdOfRecordBook("12345678901"));
        assertThrows(ValidatingException.class, () -> st.setCourse(7));
        assertThrows(ValidatingException.class, () -> st.setGroup("КН92"));
        assertThrows(ValidatingException.class, () -> st.setYearOfentering(2018));

        assertThrows(ValidatingException.class, () -> t.setRate(2.0));
        assertThrows(ValidatingException.class, () -> t.setDateOfentering("01.09.1990"));
        assertThrows(ValidatingException.class, () -> t.setWorkload(-10));

        assertThrows(ValidatingException.class, () -> f.setCode(""));
        assertThrows(ValidatingException.class, () -> f.setName(null));
        assertThrows(ValidatingException.class, () -> f.setShortName("   "));
        assertThrows(ValidatingException.class, () -> f.setDean(null));
        assertThrows(ValidatingException.class, () -> f.setContacts(""));

        assertThrows(ValidatingException.class, () -> d.setCode(null));
        assertThrows(ValidatingException.class, () -> d.setName("   "));
        assertThrows(ValidatingException.class, () -> d.setFaculty(null));
        assertThrows(ValidatingException.class, () -> d.setHead(null));
        assertThrows(ValidatingException.class, () -> d.setLocation(""));
       
       
       
       
       
    }
}
