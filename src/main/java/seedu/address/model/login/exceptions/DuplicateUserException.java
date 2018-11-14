package seedu.address.model.login.exceptions;

/**
 * Signals that the operation will result in duplicate User objects.
 */
public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("Operation would result in duplicate users");
    }
}
