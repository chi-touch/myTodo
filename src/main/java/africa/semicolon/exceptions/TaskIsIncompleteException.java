package africa.semicolon.exceptions;

public class TaskIsIncompleteException extends RuntimeException {
    public TaskIsIncompleteException(String message) {
        super(message);
    }
}
