package DigiPackage;

public class Faculty {
    private String code;
    private String name;
    private String shortName;
    private Teacher dean;
    private String contacts;

    public Faculty(String code, String name, String shortName, Teacher dean, String contacts) {
        setCode(code);
        setName(name);
        setShortName(shortName);
        setDean(dean);
        setContacts(contacts);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code != null && !code.trim().isEmpty()) this.code = code;
        else throw new IllegalArgumentException("Faculty code cannot be empty");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) this.name = name;
        else throw new IllegalArgumentException("Faculty name cannot be empty");
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        if (shortName != null && !shortName.trim().isEmpty()) this.shortName = shortName;
        else throw new IllegalArgumentException("Faculty short name cannot be empty");
    }

    public Teacher getDean() {
        return dean;
    }

    public void setDean(Teacher dean) {
        if (dean != null) this.dean = dean;
        else throw new IllegalArgumentException("Dean cannot be null");
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        if (contacts != null && !contacts.trim().isEmpty()) this.contacts = contacts;
        else throw new IllegalArgumentException("Contacts cannot be empty");
    }

    @Override
    public String toString() {
        return "Faculty [" + code + "]: " + name + " (" + shortName + "), Dean: " + dean.getName();
    }
}