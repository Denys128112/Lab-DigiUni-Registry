package exceptions;

public class EmptySearchResultException extends RuntimeException {
    public EmptySearchResultException(String message) {
        super(message);
    }
}
