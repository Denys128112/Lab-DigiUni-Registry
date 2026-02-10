package DigiPackage;

public class Teacher extends Person{
    private Position position;
    private ScientificDegree scientificDegree;
    private AcademicTitle academicTitle;
    private String dateOfentering;
    private double rate;
    private int workload; //години

    public Teacher(){

    }
    public Teacher(String id, String name, String dateOfBirth, String email, String phone,
                   Position position, ScientificDegree scientificDegree, AcademicTitle academicTitle,
                   String dateOfentering, double rate, int workload) {
        super(id, name, dateOfBirth, email, phone);
        this.position = position;
        this.scientificDegree = scientificDegree;
        this.academicTitle = academicTitle;
        setDateOfentering(dateOfentering);
        setRate(rate);
        setWorkload(workload);
    }

    public String getPosition() {
        switch (position) {
            case Assistant:
                return "Асистент";
            case Docent:
                return "Доцент";
            case Lecturer:
                return "Викладач";
            case Senior_lecturer:
                return "Старшиий викладач";
            case Profesor:
                return "Професор";
            case Head_of_department:
                return "Завкафедри";
            case Dean:
                return "Декан";
                default:
                    return null;
        }
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getScientificDegree() {
        switch (scientificDegree) {
            case PHD:
                return "Доктор філософії (Phd)";
            case Candidate:
                return "Кандидат наук";
            case Doctor_of_science:
                return "Доктор наук";
            case None:
                return "Немає";
            default:
                return null;
        }
    }

    public void setScientificDegree(ScientificDegree scientificDegree) {
        this.scientificDegree = scientificDegree;
    }

    public String getAcademicTitle() {
        switch (academicTitle) {
            case None:
                return "Немає";
            case Profesor:
                return "Професор";
            case Docent:
                return "Доцент";
            case Senior_researcher:
                return "Старший дослідник";
            default:
                return null;
        }
    }

    public void setAcademicTitle(AcademicTitle academicTitle) {
        this.academicTitle = academicTitle;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
            if (rate < 0.25 || rate > 1.5) {
                throw new IllegalArgumentException("Rate must be between 0.25 and 1.5");
            }
            this.rate = rate;
        }


    public String getDateOfentering() {
        return dateOfentering;
    }

    public void setDateOfentering(String dateOfentering) {
        if (verifyCorectionOfDate(dateOfentering)) {
            this.dateOfentering = dateOfentering;
        } else {
            throw new IllegalArgumentException("Date of entering is incorrect (must be DD.MM.YYYY and after 1992)");
        }
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        if (workload < 0 || workload > 1000) {
            throw new IllegalArgumentException("Workload must be between 0 and 1000 hours");
        }
        this.workload = workload;
    }

     @Override
     protected boolean corectData(String date) {
        String days=date.substring(0,2);
        String months=date.substring(3,5);
        String years=date.substring(6,10);
        int day=Integer.parseInt(days);
        int month=Integer.parseInt(months);
        int year=Integer.parseInt(years);
        return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 1992 && (month != 2 || day <= 28);
    }

    @Override
    public String toString() {
        return "Teacher" +
                "position:" + getPosition() +
                ", scientificDegree:" + getScientificDegree() +
                ", academicTitle:" +getAcademicTitle() +
                ", dateOfentering:'" + dateOfentering + '\'' +
                ", rate:" + rate +
                ", workload:" + workload;
    }
}
