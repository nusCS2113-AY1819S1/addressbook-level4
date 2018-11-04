package seedu.address.model.drink.exceptions;

/**
 * Signals that the operation cannot be performed as there is insufficient stock
 */
public class InsufficientQuantityException extends Exception {
    public InsufficientQuantityException() {
        super("Insufficient quantity in stock to perform operation");
    }

}
