package DigiPackage;

public class Department {
    private String code;
    private String name;
    private Faculty faculty;
    private Teacher head;
    private String location;

    public Department(String code, String name, Faculty faculty, Teacher head, String location) {
        setCode(code);
        setName(name);
        setFaculty(faculty);
        setHead(head);
        setLocation(location);
    }

    public String getCode() { return code; }
    public void setCode(String code) {
        if (code != null && !code.trim().isEmpty()) this.code = code;
        else throw new IllegalArgumentException("Department code cannot be empty");
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) this.name = name;
        else throw new IllegalArgumentException("Department name cannot be empty");
    }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) {
        if (faculty != null) this.faculty = faculty;
        else throw new IllegalArgumentException("Faculty cannot be null");
    }

    public Teacher getHead() { return head; }
    public void setHead(Teacher head) {
        if (head != null) this.head = head;
        else throw new IllegalArgumentException("Head of department cannot be null");
    }

    public String getLocation() { return location; }
    public void setLocation(String location) {
        if (location != null && !location.trim().isEmpty()) this.location = location;
        else throw new IllegalArgumentException("Location cannot be empty");
    }

    @Override
    public String toString() {
        return "Department: " + name + " (" + code + "), Faculty: " + faculty.getShortName() + ", Head: " + head.getName();
    }
}