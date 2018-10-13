package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Represents a timetable that is associated with a person
 *
 */
public class TimeTable {
    private Collection<TimeSlot> timeSlots;

    public TimeTable() {
        timeSlots = new ArrayList<>();
    }

    public TimeTable(Collection<TimeSlot> input) {
        timeSlots = input;
    }

    /**
     * Adds a TimeSlot to the TimeTable
     *
     * @param toAdd TimeSlot to be added
     */
    public void addTimeSlot(TimeSlot toAdd) {
        Optional <TimeSlot> overlapTimeSlot = findOverlapTimeSlot(toAdd);

        if (overlapTimeSlot.isPresent()) {
            removeTimeSlot(overlapTimeSlot.get());
            timeSlots.add(overlapTimeSlot.get().concatTimeSlots(toAdd));
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
     * @return Optional containing overlap TimeSlot
     */
    private Optional <TimeSlot> findOverlapTimeSlot(TimeSlot toCheck) {
        for (TimeSlot timeSlot : timeSlots) {
            if (timeSlot.isOverlap(toCheck)) {
                return Optional.of(timeSlot);
            }
        }
        return Optional.empty();
    }
}
