package seedu.address.model.fooditem.exceptions;

/**
 * Signals that the operation will result in duplicate Food Items (Food Items are considered duplicates if
 * they have the same identity).
 */

public class DuplicateFoodItemException extends RuntimeException {
    public DuplicateFoodItemException() {
        super("Operation would result in duplicate food items");
    }
}
