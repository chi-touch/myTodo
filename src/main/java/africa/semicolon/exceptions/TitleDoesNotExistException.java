package africa.semicolon.exceptions;

public class TitleDoesNotExistException extends RuntimeException {
    public TitleDoesNotExistException(String message) {
        super(message);
    }
}
