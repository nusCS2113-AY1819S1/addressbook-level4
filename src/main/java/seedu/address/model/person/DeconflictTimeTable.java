package seedu.address.model.person;

import java.util.Collection;
import java.util.HashSet;

import seedu.address.model.person.exceptions.TimeSlotNotOverlapException;

/**
 * Represents a deconflicted {@code TimeTable}
 *
 * Unlike {@code TimeTable}, supports adding overlapping {@code TimeSlot}s by merging conflicting {@code TimeSlot}s
 */
public class DeconflictTimeTable extends TimeTable {

    public DeconflictTimeTable() {
        super();
    }

    public DeconflictTimeTable(TimeTable input) {
        super(input);
    }

    /**
     * Merges {@code toMerge} with all {@code TimeSlot}s that overlap with it
     * @param toMerge {@code TimeSlot} to merge
     */
    public void mergeOverlap(TimeSlot toMerge) {
        TimeSlot toAdd = new TimeSlot(toMerge);

        for (TimeSlot overlap : findOverlapOrAdjacent(toMerge)) {
            try {
                toAdd.mergeInto(overlap);
            } catch (TimeSlotNotOverlapException e) {
                // This should not happen since findOverlapOrAdjacent() only returns valid timeslots to be merged
            }
            removeTimeSlot(overlap);
        }

        addTimeSlot(toAdd);
    }

    /**
     * Determines which {@code TimeSlot}s in this {@code TimeTable} overlaps or is adjacent with {@code toCheck}
     *
     * @param toCheck the {@code TimeSlot} to be checked against
     * @return {@code Collection} containing overlapping {@code TimeSlot}s
     */
    public Collection<TimeSlot> findOverlapOrAdjacent(TimeSlot toCheck) {
        Collection <TimeSlot> toReturn = new HashSet<>();

        for (TimeSlot timeSlot : timeSlots) {
            if (timeSlot.isOverlap(toCheck) || timeSlot.isAdjacent(toCheck)) {
                toReturn.add(timeSlot);
            }
        }
        return toReturn;
    }
}
