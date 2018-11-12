package seedu.address.model.exceptions;

/**
 * Thrown when trying to access a history state index in {@code StateHistoryList} that is out of bounds.
 */
public class HistoryStateOutOfBoundsException extends RuntimeException {
    public HistoryStateOutOfBoundsException(String message) {
        super(message);
    }
}
