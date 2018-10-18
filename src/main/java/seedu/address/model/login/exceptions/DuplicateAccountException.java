package seedu.address.model.login.exceptions;

/**
 * Signals that the operation will result in duplicate LoginDetails (LoginDetails are considered duplicates if they
 * have the same user ID).
 */
public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException() {
        super("Operation would result in duplicate accounts");
    }
}
