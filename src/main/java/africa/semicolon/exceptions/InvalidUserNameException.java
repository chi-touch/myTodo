package africa.semicolon.exceptions;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException(String message) {
        super(message);
    }
}
