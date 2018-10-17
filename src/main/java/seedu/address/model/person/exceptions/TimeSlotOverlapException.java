package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in overlapping TimeSlots being added.
 */
public class TimeSlotOverlapException extends RuntimeException {
    public TimeSlotOverlapException() {
        super("The added timeslot overlaps with existing timeslots!");
    }
}
