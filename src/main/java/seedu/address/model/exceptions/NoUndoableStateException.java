package seedu.address.model.exceptions;

/**
 * Thrown when trying to {@code undo()} in VersionedAddressBook or VersionedEventList but can't.
 */
public class NoUndoableStateException extends RuntimeException {
    public NoUndoableStateException() {
        super("Current state pointer at start of state history list, unable to undo.");
    }
}
