package seedu.address.model.person.exceptions;

/**
 * Signals that the {@code TimeSlot} to be merged cannot be merged
 */
public class TimeSlotNotOverlapException extends RuntimeException {
    public TimeSlotNotOverlapException() {
        super("The timeslot to be merged does not overlap with the current timeslot!");
    }
}
