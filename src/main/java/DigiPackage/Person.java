package DigiPackage;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import exceptions.ValidatingException;

import java.util.Objects;

import java.time.Period;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import exceptions.ValidatingException;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Person {
        private String id;
        private String name;
        private LocalDate dateOfBirth;
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        private String email;
        private String phone;
        public Person(){

        }
        public Person(String id, String name, String dateOfBirth, String email, String phone) {
            this.id = id;
            setName(name);
            setDateOfBirth(dateOfBirth);
            setEmail(email);
            setPhone(phone);
        }

        private boolean verifyCorectionOfname(String name) {
            if(name==null || name.isEmpty() || name.charAt(0)==' ')
                return false;
            int spaceCount = 0;
            char prch=name.charAt(0);
            for(int i = 0; i < name.length(); i++) {
                if(name.charAt(i)==' ')
                spaceCount++;
                if((!Character.isLetter(name.charAt(i))&& name.charAt(i)!=' ')||(name.charAt(i)==' ' && prch==name.charAt(i)))
                    return false;
                prch=name.charAt(i);
            }
            return spaceCount == 2;
        }

    public void setId(String id) {
        this.id = id;
    }


        private boolean verifyCorectionOfEmail(String email) {
            if(email==null || email.isEmpty())
                return false;
            int dogIndex = email.indexOf('@');
            int lastDotIndex = email.lastIndexOf('.');
            if (dogIndex < 1 || dogIndex == email.length() - 1 ||
                    dogIndex != email.lastIndexOf('@') ||
                    lastDotIndex <= dogIndex + 1 ||
                    lastDotIndex > email.length() - 3 ||
                    email.contains(" "))
                return false;
            return true;
        }

        private boolean verifyCorectionOfPhone(String phone) {
            if(phone==null ||phone.length()!=13)
                return false;
            for (int i = 1; i < phone.length(); i++) {
                if(!Character.isDigit(phone.charAt(i))){
                    return false;
                }
            }
            return true;
        }

    public String getDateOfBirth() {
        return dateOfBirth != null ? dateOfBirth.format(DATE_FORMATTER) : null;
    }

    public void setDateOfBirth(String dateOfBirth) {
        try {
            LocalDate parsedDate = LocalDate.parse(dateOfBirth, DATE_FORMATTER);

            int age = Period.between(parsedDate, LocalDate.now()).getYears();

            if (parsedDate.getYear() < 1946) {
                throw new ValidatingException("Invalid date of birth (must be after 1945)");
            }
            if (age < 16) {
                throw new ValidatingException("Invalid date of birth: Person must be at least 16 years old!");
            }

            this.dateOfBirth = parsedDate;
        } catch (DateTimeParseException e) {
            throw new ValidatingException("Invalid date format. Use dd.MM.yyyy");
        }
    }

        public String getId() {
            return id;
        }

        public String getName() {

            return name;

        }

        public void setName(String name) {
            if(verifyCorectionOfname(name))
            this.name = name;
            else
                throw  new ValidatingException("Invalid name of person");
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            if(verifyCorectionOfEmail(email))
            this.email = email;
            else
                throw  new ValidatingException("Invalid email of person");
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            if(verifyCorectionOfPhone(phone))
            this.phone = phone;
            else
                throw  new ValidatingException("Invalid phone number of person");
        }

        @Override
        public String toString() {
            return
                    "id:'" + id + '\'' +
                    ", name:'" + name + '\'' +
                    ", dateOfBirth:'" + dateOfBirth + '\'' +
                    ", email:'" + email + '\'' +
                    ", phone:'" + phone;
        }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if(o==this) return true;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
