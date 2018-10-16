package seedu.address.model.drink.exceptions;

/**
 * Signals that the operation will result in duplicate Batches (Batches are considered duplicates if they have the same
 * identity).
 */
public class DuplicateBatchException extends RuntimeException {
    public DuplicateBatchException() {
        super("Operation would result in duplicate batches");
    }
}
