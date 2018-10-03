package seedu.address.model.gradebook.exceptions;

/**
 * Signals that the operation will result in duplicate Components (Components are considered duplicates if they have
 * the same identity).
 */
public class DuplicateComponentException extends RuntimeException {
    public DuplicateComponentException() {
        super("Operation would result in duplicate components");
    }
}
