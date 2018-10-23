package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collection;

import seedu.address.model.person.exceptions.TimeSlotDoesNotExistException;
import seedu.address.model.person.exceptions.TimeSlotOverlapException;

/**
 * Represents a timetable that is associated with a person
 *
 */
public class TimeTable {
    private Collection <TimeSlot> timeSlots;

    public TimeTable() {
        timeSlots = new ArrayList<>();
    }

    public TimeTable(Collection <TimeSlot> input) {
        timeSlots = input;
    }

    public Collection <TimeSlot> getTimeSlots() {
        Collection <TimeSlot> toReturn = new ArrayList<>();
        toReturn.addAll(timeSlots);
        return toReturn;
    }

    /**
     * Returns if the TimeTable is empty.
     */
    public boolean isEmpty() {
        if (timeSlots.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a TimeSlot to the TimeTable
     *
     * @param toAdd TimeSlot to be added
     * @throws TimeSlotOverlapException if toAdd overlaps with an existing TimeSlot in the TimeTable
     */
    public void addTimeSlot(TimeSlot toAdd) throws TimeSlotOverlapException {
        if (!findOverlapTimeSlots(toAdd).isEmpty()) {
            throw new TimeSlotOverlapException();
        } else {
            timeSlots.add(toAdd);
        }
    }

    /**
     * Removes a TimeSlot from the TimeTable
     *
     * @param toRemove TimeSlot to be removed
     * @throws TimeSlotDoesNotExistException if toRemove does not exist in the TimeTable
     */
    public void removeTimeSlot (TimeSlot toRemove) throws TimeSlotDoesNotExistException {
        if (!timeSlots.remove(toRemove)) {
            throw new TimeSlotDoesNotExistException();
        }
    }

    /**
     * Checks whether toCheck overlaps with any TimeSlot in the existing timetable
     *
     * @param toCheck the TimeSlot to be checked against
     * @return Optional containing overlapping TimeSlot
     */
    public Collection <TimeSlot> findOverlapTimeSlots(TimeSlot toCheck) {
        Collection <TimeSlot> toReturn = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots) {
            if (timeSlot.isOverlap(toCheck)) {
                toReturn.add(timeSlot);
            }
        }
        return toReturn;
    }

    public void updateTimeTable(TimeTable toReplace) {
        this.timeSlots = toReplace.getTimeSlots();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeTable // instanceof handles nulls
                && timeSlots.equals(((TimeTable) other).getTimeSlots())); // state check
    }

}
