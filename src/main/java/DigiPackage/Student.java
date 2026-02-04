package DigiPackage;

public class Student extends Person {
    private String idOfRecordBook;
    private int course;
    private int group;
    private int yearOfentering;
    private FormOfStudy formOfStudy;
    private Status studentStatus;

    public Student(String id, String name, String middleName, String surname, String email, String phone, String idOfRecordBook, int course, int group, int yearOfentering, FormOfStudy formOfStudy, Status studentStatus) {
        super(id, name, email, phone);
        this.idOfRecordBook = idOfRecordBook;
        this.course = course;
        this.group = group;
        this.yearOfentering = yearOfentering;
        this.formOfStudy = formOfStudy;
        this.studentStatus = studentStatus;
    }
}
