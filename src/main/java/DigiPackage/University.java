package DigiPackage;

public class University {
    private String name;
    private String shortName;
    private int foundingYear;

    public University(String name, String shortName, int foundingYear) {
        setName(name);
        setShortName(shortName);
        setFoundingYear(foundingYear);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Назва університету не може бути порожньою");
        }
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        if (shortName != null && !shortName.trim().isEmpty()) {
            this.shortName = shortName;
        } else {
            throw new IllegalArgumentException("Абревіатура не може бути порожньою");
        }
    }

    public int getFoundingYear() {
        return foundingYear;
    }

    public void setFoundingYear(int foundingYear) {
        if (foundingYear > 1600 && foundingYear < 2026) {
            this.foundingYear = foundingYear;
        } else {
            throw new IllegalArgumentException("Некоректний рік заснування");
        }
    }

    @Override
    public String toString() {
        return "University: " + name + " (" + shortName + "), Засновано: " + foundingYear;
    }
}
