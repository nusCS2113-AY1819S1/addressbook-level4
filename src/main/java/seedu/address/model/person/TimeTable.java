package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collection;

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

    /**
     * Adds a TimeSlot to the TimeTable
     *
     * @param toAdd TimeSlot to be added
     */
    public void addTimeSlot(TimeSlot toAdd) throws TimeSlotOverlapException {
        Collection <TimeSlot> overlapTimeSlot = findOverlapTimeSlot(toAdd);

        if (overlapTimeSlot.isEmpty()) {
            throw new TimeSlotOverlapException();
        } else {
            timeSlots.add(toAdd);
        }
    }

    public void removeTimeSlot (TimeSlot toRemove) {
        timeSlots.remove(toRemove);
    }

    /**
     * Checks whether toCheck overlaps with any TimeSlot in the existing timetable
     *
     * @param toCheck the TimeSlot to be checked against
     * @return Optional containing overlapping TimeSlot
     */
    private Collection <TimeSlot> findOverlapTimeSlot(TimeSlot toCheck) {
        Collection <TimeSlot> toReturn = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots) {
            if (timeSlot.isOverlap(toCheck)) {
                toReturn.add(timeSlot);
            }
        }
        return toReturn;
    }
}
