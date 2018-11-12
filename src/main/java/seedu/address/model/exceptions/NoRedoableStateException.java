package seedu.address.model.exceptions;

/**
 * Thrown when trying to {@code redo()} in VersionedAddressBook or VersionedEventList but can't.
 */
public class NoRedoableStateException extends RuntimeException {
    public NoRedoableStateException() {
        super("Current state pointer at end of state history list, unable to redo.");
    }
}
