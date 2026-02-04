package DigiPackage;

public class Teacher extends Person{
    private Position position;
    private ScientificDegree scientificDegree;
    private AcademicTitle academicTitle;
    private String dateOfentering;
    private double rate;
    private int workload; //години

    public Teacher(String id, String name, String middleName, String surname, String email, String phone, Position position, ScientificDegree scientificDegree, AcademicTitle academicTitle, String dateOfentering, double rate, int workload) {
        super(id, name, email, phone);
        this.position = position;
        this.scientificDegree = scientificDegree;
        this.academicTitle = academicTitle;
        this.dateOfentering = dateOfentering;
        this.rate = rate;
        this.workload = workload;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ScientificDegree getScientificDegree() {
        return scientificDegree;
    }

    public void setScientificDegree(ScientificDegree scientificDegree) {
        this.scientificDegree = scientificDegree;
    }

    public AcademicTitle getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(AcademicTitle academicTitle) {
        this.academicTitle = academicTitle;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDateOfentering() {
        return dateOfentering;
    }

    public void setDateOfentering(String dateOfentering) {
        this.dateOfentering = dateOfentering;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "position=" + position +
                ", scientificDegree=" + scientificDegree +
                ", academicTitle=" + academicTitle +
                ", dateOfentering='" + dateOfentering + '\'' +
                ", rate=" + rate +
                ", workload=" + workload +
                '}';
    }
}
