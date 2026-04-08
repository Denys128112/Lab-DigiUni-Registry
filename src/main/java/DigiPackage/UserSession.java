package DigiPackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public record UserSession(String username, UserRole role, String loginTime) {
    public UserSession(String username, UserRole role) {
        this(username, role, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
    }
}
