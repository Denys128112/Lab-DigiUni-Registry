package DigiPackage;

public enum UserRole {
    GUEST(1),
    STUDENT(1 | 2),
    MANAGER(1 | 2 | 4 | 8 | 16),
    ADMIN(1 | 2 | 4 | 8 | 16 | 32);

    private final int mask;

    UserRole(int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }

    public static final int CAN_READ = 1;
    public static final int STUDENT_FEATURES = 2;       // Доступ до звітів/пошуку
    public static final int WRITE_PEOPLE = 4;           // Створення/редагування студентів і викладачів
    public static final int DELETE_PEOPLE = 8;          // Видалення студентів і викладачів
    public static final int WRITE_STRUCTURE = 16;       // Створення/редагування кафедр і факультетів
    public static final int DELETE_STRUCTURE = 32;      // Видалення кафедр і факультетів
}
