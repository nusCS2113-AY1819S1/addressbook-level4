package seedu.address.model.gradebook.exceptions;

public class DuplicateComponentException extends RuntimeException {
    public DuplicateComponentException() {
        super("Operation would result in duplicate components");
    }
}
