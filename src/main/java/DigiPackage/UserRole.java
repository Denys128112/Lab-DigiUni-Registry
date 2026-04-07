package DigiPackage;

public enum UserRole {
    GUEST(1),                  // читання
    MANAGER(1 | 2),            // читання + запис
    ADMIN(1 | 2 | 4);          // читання + запис + видалення

    private final int mask;

    UserRole(int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }

    public static final int CAN_READ = 1;
    public static final int CAN_WRITE = 2;
    public static final int CAN_DELETE = 4;
}