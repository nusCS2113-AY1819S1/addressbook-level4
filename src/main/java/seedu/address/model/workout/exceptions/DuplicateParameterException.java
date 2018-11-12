package seedu.address.model.workout.exceptions;

/**
 * Signals that the operation will result in duplicate Parameters
 */
public class DuplicateParameterException extends RuntimeException {
    public DuplicateParameterException() {
        super("Operation would result in duplicate parameters");
    }
}
