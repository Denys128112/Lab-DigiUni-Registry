package CLI;
import DigiPackage.*;
import exceptions.EntityAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger log = LoggerFactory.getLogger(UniversityRepository.class);
        public University getUniversity() {
            log.debug("University read");
            return university;
        }

        public void addFaculty(Faculty faculty) {
            log.debug("add Faculty {}",faculty);
            if(facultiesMap.containsKey(faculty.getCode()))
                throw new EntityAlreadyExistsException(faculty.getCode());
            if (faculty != null) {
                faculties.add(faculty);
                facultiesMap.put(faculty.getCode(), faculty);
                log.info("faculty added {}",faculty);
            }else  {
                log.warn("faculty is null");
            }
        }

        public List<Faculty> getFaculties() {
            return faculties;
        }


        public void removeFaculty(Faculty faculty) {
            log.debug("remove Faculty {}",faculty);
           faculties.remove(faculty);
           facultiesMap.remove(faculty.getCode());
           log.info("faculty removed {}",faculty);
        }
        public void addDepartment(Department department) {
            log.debug("add Department {}",department);
            if(departmentMap.containsKey(department.getCode()))
                throw new EntityAlreadyExistsException(department.getCode());
            if (department != null ) {
                departments.add(department);
                departmentMap.put(department.getCode(), department);
                log.info("department added {}",department);
            }
            else  {
                log.warn("department is already in use or null");
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
            log.debug("remove Department {}",department);
            departments.remove(department);
            departmentMap.remove(department.getCode());
            log.info("department removed {}",department);
        }
    }

