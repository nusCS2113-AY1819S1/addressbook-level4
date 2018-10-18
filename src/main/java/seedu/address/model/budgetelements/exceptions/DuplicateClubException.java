package seedu.address.model.budgetelements.exceptions;

/**
 * Signals that the operation will result in duplicate Clubs (Clubs are considered duplicates if they have the same
 * identity).
 */
public class DuplicateClubException extends RuntimeException {
    public DuplicateClubException() {
        super("Operation would result in duplicate clubs");
    }
}
