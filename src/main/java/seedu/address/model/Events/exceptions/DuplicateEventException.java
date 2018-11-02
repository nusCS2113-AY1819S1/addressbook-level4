package seedu.address.model.Events.exceptions;


/**
 * Signals that the operation will result in duplicate Event (Events are considered duplicates if they have the same
 * name).
 */

public class DuplicateEventException extends RuntimeException{
    public DuplicateEventException() {
        super("Operation would result in duplicate event");
    }
}
