package DigiPackage;

public class Department {
    private String name;
    private Faculty faculty;
    private Teacher head; // Завідувач кафедри

    public Department(String name, Faculty faculty, Teacher head) {
        setName(name);
        setFaculty(faculty);
        setHead(head);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Назва кафедри не може бути порожньою");
        }
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        if (faculty != null) {
            this.faculty = faculty;
        } else {
            throw new IllegalArgumentException("Кафедра повинна належати до факультету");
        }
    }

    public Teacher getHead() {
        return head;
    }

    public void setHead(Teacher head) {
        if (head != null) {
            this.head = head;
        } else {
            throw new IllegalArgumentException("Кафедра повинна мати завідувача");
        }
    }

    @Override
    public String toString() {
        return "Кафедра: " + name + " (Факультет: " + faculty.getName() + "), Завідувач: " + head.getName();
    }
}