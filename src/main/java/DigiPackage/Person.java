package DigiPackage;

    public class Person {
        private String id;
        private String name;
        private String dateOfBirth;
        private String email;
        private String phone;

        public Person(String id, String name, String dateOfBirth, String email, String phone) {
            this.id = id;
            if(verifyCorectionOfname(name))
            this.name = name;
            if(verifyCorectionOfBirth(dateOfBirth))
            this.dateOfBirth = dateOfBirth;
            if(verifyCorectionOfEmail(email))
            this.email = email;
            if(verifyCorectionOfPhone(phone))
            this.phone = phone;
        }

        private boolean verifyCorectionOfname(String name) {
            if(name==null || name.isEmpty() || name.charAt(0)==' ')
                return false;
            int spaceCount = 0;
            for(int i = 0; i < name.length(); i++) {
                if(name.charAt(i)==' ')
                spaceCount++;
                if(!Character.isLetter(name.charAt(i))&& name.charAt(i)!=' ')
                    return false;
            }
            if(spaceCount !=2)
                return false;
            return true;
        }

        private boolean verifyCorectionOfBirth(String dateOfBirth) {
            return true;
        }

        private boolean verifyCorectionOfEmail(String email) {
            return true;
        }

        private boolean verifyCorectionOfPhone(String phone) {
            return true;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", dateOfBirth='" + dateOfBirth + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }
