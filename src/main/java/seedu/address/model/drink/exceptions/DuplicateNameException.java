package seedu.address.model.drink.exceptions;

/**
 * Signals that the operation will result in duplicate Drink Names.
 */
public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException() {
        super("Operation would result in a duplicate name");
    }
}
