import DigiPackage.UserRole;
import DigiPackage.UserSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTest {

    @Test
    public void testUserSessionRecord() {
        UserRole role = UserRole.MANAGER;
        UserSession session = new UserSession("Менеджер Тест", role);

        assertEquals("Менеджер Тест", session.username(), "Ім'я користувача не збігається");
        assertEquals(UserRole.MANAGER, session.role(), "Роль не збігається");

        assertNotNull(session.loginTime(), "Час логіну не повинен бути пустим");
    }

    @Test
    public void testBitwiseRoles() {
        int studentMask = UserRole.STUDENT.getMask();

        assertTrue((studentMask & UserRole.CAN_READ) != 0, "Студент повинен мати право читання");
        assertTrue((studentMask & UserRole.STUDENT_FEATURES) != 0, "Студент повинен мати студентські фічі");

        assertFalse((studentMask & UserRole.WRITE_PEOPLE) != 0, "Студент не повинен мати право запису людей");

        int adminMask = UserRole.ADMIN.getMask();
        assertTrue((adminMask & UserRole.DELETE_STRUCTURE) != 0, "Адмін повинен мати право видаляти структуру");
    }

    @Test
    public void testGuestAccessDenied() {
        int guestMask = UserRole.GUEST.getMask();

        boolean canDeletePeople = (guestMask & UserRole.DELETE_PEOPLE) != 0;

        assertFalse(canDeletePeople, "Гість не може видаляти людей!");
    }

    @Test
    public void testManagerPermissions() {
        int managerMask = UserRole.MANAGER.getMask();

        assertTrue((managerMask & UserRole.WRITE_STRUCTURE) != 0, "Менеджер повинен мати право створювати кафедри");

        assertFalse((managerMask & UserRole.DELETE_STRUCTURE) != 0, "Менеджер НЕ повинен мати право видаляти кафедри");

        assertTrue((managerMask & UserRole.DELETE_PEOPLE) != 0, "Менеджер повинен могти видаляти студентів");
    }

    @Test
    public void testAdminHasAllPermissions() {
        int adminMask = UserRole.ADMIN.getMask();

        assertTrue((adminMask & UserRole.CAN_READ) != 0);
        assertTrue((adminMask & UserRole.STUDENT_FEATURES) != 0);
        assertTrue((adminMask & UserRole.WRITE_PEOPLE) != 0);
        assertTrue((adminMask & UserRole.DELETE_PEOPLE) != 0);
        assertTrue((adminMask & UserRole.WRITE_STRUCTURE) != 0);
        assertTrue((adminMask & UserRole.DELETE_STRUCTURE) != 0, "Адмін - єдиний, хто може видаляти структуру");
    }

    @Test
    public void testStudentCannotWrite() {
        int studentMask = UserRole.STUDENT.getMask();

        assertFalse((studentMask & UserRole.WRITE_PEOPLE) != 0, "Студент не може створювати людей");
        assertFalse((studentMask & UserRole.WRITE_STRUCTURE) != 0, "Студент не може створювати факультети");
        assertFalse((studentMask & UserRole.DELETE_PEOPLE) != 0, "Студент не може видаляти");
    }
}