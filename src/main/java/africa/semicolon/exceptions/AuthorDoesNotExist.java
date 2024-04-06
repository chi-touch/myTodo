package africa.semicolon.exceptions;

public class AuthorDoesNotExist extends RuntimeException {
    public AuthorDoesNotExist(String message) {
        super(message);
    }
}
