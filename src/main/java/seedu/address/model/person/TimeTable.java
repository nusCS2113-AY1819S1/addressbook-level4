package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collection;

import seedu.address.model.person.exceptions.TimeSlotDoesNotExistException;
import seedu.address.model.person.exceptions.TimeSlotOverlapException;

/**
 * Represents a timetable that is associated with a person
 */
public class TimeTable {
    protected Collection <TimeSlot> timeSlots;

    public TimeTable() {
        timeSlots = new ArrayList<>();
    }

    public TimeTable(Collection <TimeSlot> input) {
        this();
        timeSlots.addAll(input);
    }

    public TimeTable(TimeTable input) {
        this(input.getTimeSlots());
    }

    public Collection <TimeSlot> getTimeSlots() {
        Collection <TimeSlot> toReturn = new ArrayList<>();
        toReturn.addAll(timeSlots);
        return toReturn;
    }

    /**
     * Overwrites this {@code TimeTable} with {@code toReplace}
     */
    public void updateTimeTable(TimeTable toReplace) {
        timeSlots.clear();
        timeSlots.addAll(toReplace.getTimeSlots());
    }

    /**
     * Adds a {@code TimeSlot} to the {@code TimeTable}
     *
     * @param toAdd {@code TimeSlot} to be added
     * @throws TimeSlotOverlapException if {@code toAdd} overlaps with an existing {@code TimeSlot}
     */
    public void addTimeSlot(TimeSlot toAdd) throws TimeSlotOverlapException {
        if (hasOverlap(toAdd)) {
            throw new TimeSlotOverlapException();
        } else {
            timeSlots.add(toAdd);
        }
    }

    /**
     * Removes a {@code TimeSlot} from the {@code TimeTable}
     *
     * @param toRemove {@code TimeSlot} to be removed
     * @throws TimeSlotDoesNotExistException if {@code toRemove} does not exist in the {@code TimeTable}
     */
    public void removeTimeSlot (TimeSlot toRemove) throws TimeSlotDoesNotExistException {
        if (!timeSlots.remove(toRemove)) {
            throw new TimeSlotDoesNotExistException();
        }
    }

    /**
     * Checks whether {@code toCheck} overlaps with any {@code TimeSlot}s in this {@code TimeTable}
     * @param toCheck {@code TimeSlot} to be checked
     * @return Whether an overlapping {@code TimeSlot} exists in this {@code TimeTable}
     */
    public boolean hasOverlap(TimeSlot toCheck) {
        for (TimeSlot timeSlot : timeSlots) {
            if (timeSlot.isOverlap(toCheck)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeTable // instanceof handles nulls
                && timeSlots.equals(((TimeTable) other).getTimeSlots())); // state check
    }

}
