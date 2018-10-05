package seedu.address.model.joboffer.exceptions;

/**
 * Signals that the operation will result in duplicate job offers
 * (Job offers are considered duplicates if they are from the same company offering the same job).
 */

public class DuplicateJobException extends RuntimeException {
    public DuplicateJobException() {
        super("Operation would result in duplicate job offers");
    }
}
