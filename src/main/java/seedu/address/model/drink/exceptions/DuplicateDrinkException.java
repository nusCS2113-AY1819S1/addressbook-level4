package seedu.address.model.drink.exceptions;

/**
 * Signals that the operation will result in duplicate Drinks (Drinks are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDrinkException extends RuntimeException {
    public DuplicateDrinkException() {
        super("Operation would result in duplicate drinks");
    }
}

