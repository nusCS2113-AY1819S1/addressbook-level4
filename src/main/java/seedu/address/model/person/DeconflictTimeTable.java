package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a deconflicted {@code TimeTable}
 *
 * Unlike {@code TimeTable}, supports adding overlapping {@code TimeSlot}s by merging conflicting {@code TimeSlot}s
 */
public class DeconflictTimeTable extends TimeTable {
    /**
     * Determines which {@code TimeSlot}s in this {@code TimeTable} overlaps with {@code toCheck}
     *
     * @param toCheck the {@code TimeSlot} to be checked against
     * @return {@code Collection} containing overlapping {@code TimeSlot}s
     */
    public Collection<TimeSlot> findOverlapTimeSlots(TimeSlot toCheck) {
        Collection <TimeSlot> toReturn = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots) {
            if (timeSlot.isOverlap(toCheck)) {
                toReturn.add(timeSlot);
            }
        }
        return toReturn;
    }
}
