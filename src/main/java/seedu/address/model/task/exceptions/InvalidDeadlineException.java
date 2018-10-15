package seedu.address.model.task.exceptions;

/**
 * Signals that an invalid deadline is being entered.
 */

public class InvalidDeadlineException extends RuntimeException{
    public InvalidDeadlineException() {
        super("Invalid deadline entered");
    }
}
