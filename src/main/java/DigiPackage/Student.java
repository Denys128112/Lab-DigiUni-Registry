package DigiPackage;

public class Student extends Person {
    private String idOfRecordBook;
    private int course;
    private String group;
    private int yearOfentering;
    private FormOfStudy formOfStudy;
    private Status studentStatus;
    public Student(){

    }
    public Student(String id, String name, String dateOfBirth, String email, String phone, String idOfRecordBook, int course, String group, int yearOfentering, FormOfStudy formOfStudy, Status studentStatus) {
        super(id, name, dateOfBirth, email, phone);
        setIdOfRecordBook(idOfRecordBook);
        setCourse(course);
        setGroup(group);
        setYearOfentering(yearOfentering);
        this.formOfStudy = formOfStudy;
        this.studentStatus = studentStatus;
    }

    private boolean verifyidOfGroup(String group) {
        // 1. Базова перевірка на null та мінімальну довжину (напр. "А-1" - це 3 символи)
        if (group == null || group.length() < 3) return false;
        int len = group.length();
        if (!Character.isDigit(group.charAt(len - 1))) return false;
        int hyphenIndex = group.lastIndexOf('-');
        if (hyphenIndex <= 0 || hyphenIndex == len - 1) return false;
        for (int i = 0; i < hyphenIndex; i++) {
            if (!Character.isLetter(group.charAt(i))) return false;
        }
        for (int i = hyphenIndex + 1; i < len; i++) {
            if (!Character.isDigit(group.charAt(i))) return false;
        }
        return true;
    }

    private boolean verifyidOfRecordBook(String rb) {
        if(rb==null || rb.length() != 11)
            return false;
        if (!Character.isLetter(rb.charAt(0))) return false;
        if (rb.charAt(1) != ' ') return false;
        if (rb.charAt(5) != '/') return false;
        if (rb.charAt(8) != ' ') return false;
        char type = rb.charAt(9);
        if (type != 'б' && type != 'м') return false;

        if (rb.charAt(10) != 'п') return false;
        for (int i : new int[]{2, 3, 4, 6, 7}) {
            if (!Character.isDigit(rb.charAt(i))) return false;
        }

        return true;
    }

    public String getIdOfRecordBook() {
        return idOfRecordBook;
    }

    public void setIdOfRecordBook(String idOfRecordBook) {
        if(verifyidOfRecordBook(idOfRecordBook))
        this.idOfRecordBook = idOfRecordBook;
        else throw new IllegalArgumentException("Invalid id of record book");
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        if(course>0&&course<=6)
        this.course = course;
        else throw new IllegalArgumentException("course must be between 1 and 6");
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        if(verifyidOfGroup(group))
        this.group = group;
        else throw new IllegalArgumentException("group incorect");
    }

    public int getYearOfentering() {
        return yearOfentering;
    }

    public void setYearOfentering(int yearOfentering) {
        if(yearOfentering>=2019 && yearOfentering<2026)
        this.yearOfentering = yearOfentering;
        else throw new IllegalArgumentException("yearOfentering must be between 2019 and 2026");
    }

    public String getFormOfStudy() {
        switch (formOfStudy) {
            case Budget:
                return "Бюджет";
            case Contract:
                return "Контракт";
            default:
                return null;
        }
    }

    public void setFormOfStudy(FormOfStudy formOfStudy) {
        this.formOfStudy = formOfStudy;
    }

    public String getStudentStatus() {
        switch (studentStatus) {
            case Studying:
                return "Навчається";
            case Academic_vacation:
                return "Академічна відпустка";
            case Expelled:
                return "Відрахований";
            default:
                return null;
        }
    }

    public void setStudentStatus(Status studentStatus) {
        this.studentStatus = studentStatus;
    }

    @Override
    public String toString() {
        return "Student" + super.toString() +
                "idOfRecordBook:'" + idOfRecordBook + '\'' +
                ", course:" + course +
                ", group:'" + group + '\'' +
                ", yearOfentering:" + yearOfentering +
                ", formOfStudy:" + getFormOfStudy() +
                ", studentStatus:" + getStudentStatus();
    }
}
