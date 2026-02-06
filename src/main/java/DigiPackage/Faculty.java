package DigiPackage;

public class Faculty {
    private String name;
    private Teacher dean;

    public Faculty(String name, Teacher dean) {
        setName(name);
        setDean(dean);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Назва факультету не може бути порожньою");
        }
    }

    public Teacher getDean() {
        return dean;
    }

    public void setDean(Teacher dean) {
        if (dean != null) {
            this.dean = dean;
        } else {
            throw new IllegalArgumentException("Факультет повинен мати декана (Teacher не може бути null)");
        }
    }

    @Override
    public String toString() {
        return "Факультет: " + name + ", Декан: " + dean.getName();
    }
}