package exceptions;

public class ValidatingException extends RuntimeException {
    public ValidatingException(String message) {
        super(message);
    }
}
