package DigiPackage;

import java.util.ArrayList;
import java.util.List;

public class UniversityService {
    private University university;
    private List<Faculty> faculties;
    private List<Department> departments;

    public UniversityService() {
        this.faculties = new ArrayList<>();
        this.departments = new ArrayList<>();
    }

    // --- Університет ---
    public void createUniversity(String name, String shortName, int year) {
        this.university = new University(name, shortName, year);
    }

    public University getUniversity() {
        return university;
    }

    // --- Факультети ---
    public void addFaculty(Faculty faculty) {
        if (faculty != null) {
            faculties.add(faculty);
        }
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public Faculty getFacultyByName(String name) {
        for (Faculty f : faculties) {
            if (f.getName().equalsIgnoreCase(name)) {
                return f;
            }
        }
        return null;
    }

    public boolean removeFaculty(Faculty faculty) {
        return faculties.remove(faculty);
    }

    // --- Кафедри ---
    public void addDepartment(Department department) {
        if (department != null) {
            departments.add(department);
        }
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public Department getDepartmentByName(String name) {
        for (Department d : departments) {
            if (d.getName().equalsIgnoreCase(name)) {
                return d;
            }
        }
        return null;
    }

    public boolean removeDepartment(Department department) {
        return departments.remove(department);
    }
}