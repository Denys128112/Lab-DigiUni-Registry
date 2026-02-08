package DigiPackage;

public class UniversityService {
    private University university;

    // Використання масивів (вимога партнера)
    private Faculty[] faculties = new Faculty[50];
    private int facultyCount = 0;

    private Department[] departments = new Department[100];
    private int departmentCount = 0;

    public UniversityService() {
    }

    public void createUniversity(String fullName, String shortName, String city, String address) {
        this.university = new University(fullName, shortName, city, address);
    }

    public University getUniversity() {
        return university;
    }

    public void addFaculty(Faculty f) {
        if (f == null) return;
        if (facultyCount < faculties.length) {
            faculties[facultyCount] = f;
            facultyCount++;
        } else {
            System.out.println("Error: Faculty storage is full.");
        }
    }

    public Faculty[] getFaculties() {
        Faculty[] result = new Faculty[facultyCount];
        for (int i = 0; i < facultyCount; i++) {
            result[i] = faculties[i];
        }
        return result;
    }

    public void addDepartment(Department d) {
        if (d == null) return;
        if (departmentCount < departments.length) {
            departments[departmentCount] = d;
            departmentCount++;
        } else {
            System.out.println("Error: Department storage is full.");
        }
    }

    public Department[] getDepartments() {
        Department[] result = new Department[departmentCount];
        for (int i = 0; i < departmentCount; i++) {
            result[i] = departments[i];
        }
        return result;
    }
}