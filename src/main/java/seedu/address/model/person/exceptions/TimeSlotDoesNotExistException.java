package seedu.address.model.person.exceptions;

/**
 * Signals that the TimeSlot to be removed does not exist in the TimeTable.
 */
public class TimeSlotDoesNotExistException extends RuntimeException {
    public TimeSlotDoesNotExistException() {
        super("The timeslot to be removed does not exist!");
    }
}
