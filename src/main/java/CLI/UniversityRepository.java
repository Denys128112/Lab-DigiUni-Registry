package CLI;
import DigiPackage.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniversityRepository {
        private final University university=new University("Національний університет «Києво-Могилянська академія","Наукма","Київ","вулиця Григорія Сковороди, 2");
        private final List<Faculty> faculties=new ArrayList<>();
        private final List<Department> departments=new ArrayList<>();
        private final Map<String,Department> departmentMap=new HashMap<>();
        private final Map<String,Faculty> facultiesMap=new HashMap<>();

        public University getUniversity() {
            return university;
        }

        // --- Факультети ---
        public void addFaculty(Faculty faculty) {
            if (faculty != null) {
                faculties.add(faculty);
                facultiesMap.put(faculty.getCode(), faculty);
            }
        }

        public List<Faculty> getFaculties() {
            return faculties;
        }


        public void removeFaculty(Faculty faculty) {
           faculties.remove(faculty);
           facultiesMap.remove(faculty.getCode());
        }
        public void addDepartment(Department department) {
            if (department != null) {
                departments.add(department);
                departmentMap.put(department.getCode(), department);
            }
        }

        public List<Department> getDepartments() {
            return departments;
        }


    public Map<String, Faculty> getFacultiesMap() {
        return facultiesMap;
    }

    public Map<String, Department> getDepartmentMap() {
        return departmentMap;
    }

    public void removeDepartment(Department department) {
            departments.remove(department);
            departmentMap.remove(department.getCode());
        }
    }

