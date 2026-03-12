package DigiPackage;

import exceptions.ValidatingException;

import java.util.Objects;

public class Person {
        private String id;
        private String name;
        private String dateOfBirth;
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

        private boolean verifyCorectionOfDate(String dateOfBirth) {
            if(dateOfBirth.length()!=10)
                return false;
            for(int i = 0; i < dateOfBirth.length(); i++) {
                if(!Character.isDigit(dateOfBirth.charAt(i))&&!(i==2||i==5&&dateOfBirth.charAt(i)=='.'))
                    return false;
            }
            return corectData(dateOfBirth);
        }

    public void setId(String id) {
        this.id = id;
    }

    private boolean corectData(String date) {
            String days=date.substring(0,2);
            String months=date.substring(3,5);
            String years=date.substring(6,10);
           int day=Integer.parseInt(days);
           int month=Integer.parseInt(months);
           int year=Integer.parseInt(years);
            return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 1946;
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
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            if(verifyCorectionOfDate(dateOfBirth))
            this.dateOfBirth = dateOfBirth;
            else
                throw  new ValidatingException("Invalid date of birth");
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
