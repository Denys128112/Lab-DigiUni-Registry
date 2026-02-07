package DigiPackage;

public class University {
    private String fullName;
    private String shortName;
    private int foundingYear;
    private String city;
    private String address;

    public University(String fullName, String shortName, int foundingYear, String city, String address) {
        setFullName(fullName);
        setShortName(shortName);
        setFoundingYear(foundingYear);
        setCity(city);
        setAddress(address);
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) {
        if (fullName != null && !fullName.trim().isEmpty()) this.fullName = fullName;
        else throw new IllegalArgumentException("Full name cannot be empty");
    }

    public String getShortName() { return shortName; }
    public void setShortName(String shortName) {
        if (shortName != null && !shortName.trim().isEmpty()) this.shortName = shortName;
        else throw new IllegalArgumentException("Short name cannot be empty");
    }

    public int getFoundingYear() { return foundingYear; }
    public void setFoundingYear(int foundingYear) {
        if (foundingYear > 1600 && foundingYear < 2026) this.foundingYear = foundingYear;
        else throw new IllegalArgumentException("Invalid founding year");
    }

    public String getCity() { return city; }
    public void setCity(String city) {
        if (city != null && !city.trim().isEmpty()) this.city = city;
        else throw new IllegalArgumentException("City cannot be empty");
    }

    public String getAddress() { return address; }
    public void setAddress(String address) {
        if (address != null && !address.trim().isEmpty()) this.address = address;
        else throw new IllegalArgumentException("Address cannot be empty");
    }

    @Override
    public String toString() {
        return fullName + " (" + shortName + "), Founded: " + foundingYear + ", Address: " + city + ", " + address;
    }
}