package DigiPackage;

import exceptions.ValidatingException;

import java.util.Objects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import exceptions.ValidatingException;

public class Teacher extends Person{
    private Position position;
    private ScientificDegree scientificDegree;
    private AcademicTitle academicTitle;
    private LocalDate dateOfentering;
    private double rate;
    private int workload; //години
    private String departmentCode;

    public void setDateOfentering(LocalDate dateOfentering) {
        this.dateOfentering = dateOfentering;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

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

    public String PositionString() {
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

    public String ScientificDegreeString() {
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

    public String AcademicTitleString() {
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
                throw new ValidatingException("Rate must be between 0.25 and 1.5");
            }
            this.rate = rate;
        }


    public String getDateOfentering() {
        return dateOfentering != null ? dateOfentering.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : null;
    }

    public void setDateOfentering(String dateOfentering) {
        try {
            LocalDate parsedDate = LocalDate.parse(dateOfentering, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            if (parsedDate.getYear() < 1992 || parsedDate.isAfter(LocalDate.now())) {
                throw new ValidatingException("Date of entering is incorrect (must be after 1992)");
            }
            this.dateOfentering = parsedDate;
        } catch (DateTimeParseException e) {
            throw new ValidatingException("Invalid date format for entering. Use dd.MM.yyyy");
        }
    }

    public Position getPosition() {
        return position;
    }

    public ScientificDegree getScientificDegree() {
        return scientificDegree;
    }

    public AcademicTitle getAcademicTitle() {
        return academicTitle;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        if (workload < 0 || workload > 1000) {
            throw new ValidatingException("Workload must be between 0 and 1000 hours");
        }
        this.workload = workload;
    }

    @Override
    public String toString() {
        if (departmentCode == null) {
    departmentCode="none";
        }
        return "Teacher" + super.toString()+
                "Department:" + departmentCode +
                "position:" + PositionString() +
                ", scientificDegree:" + ScientificDegreeString() +
                ", academicTitle:" + AcademicTitleString() +
                ", dateOfentering:'" + dateOfentering + '\'' +
                ", rate:" + rate +
                ", workload:" + workload;
    }
}
