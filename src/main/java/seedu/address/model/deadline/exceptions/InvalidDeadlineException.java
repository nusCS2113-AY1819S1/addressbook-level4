package seedu.address.model.deadline.exceptions;

/**
 * Signals that an invalid deadline is being entered.
 */

public class InvalidDeadlineException extends RuntimeException{
    public InvalidDeadlineException() {
        super("Invalid deadline entered");
    }
}
