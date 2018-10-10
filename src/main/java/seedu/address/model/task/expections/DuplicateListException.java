package seedu.address.model.task.expections;

/**
 * Signals that the operation will result in duplicate Tasks (Tasks are considered duplicates if they have the same
 * identity).
 */
public class DuplicateListException extends RuntimeException {
    public DuplicateListException() {
        super("Operation would result in duplicate tasks");
    }
}
