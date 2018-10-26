package seedu.address.model.person;

import java.util.Collection;
import java.util.HashSet;

import seedu.address.model.person.exceptions.TimeSlotNotOverlapException;

/**
 * Represents a deconflicted {@code TimeTable}
 *
 * Unlike {@code TimeTable}, any {@code TimeSlot}s added to {@code DeconflictTimeTable} will merge with any
 * {@code TimeSlot}s that it overlaps with.
 *
 * As such, the overwritten method {@code addTimeSlot()} does not throw {@code TimeSlotOverlapException}
 */
public class DeconflictTimeTable extends TimeTable {

    public DeconflictTimeTable() {
        super();
    }

    public DeconflictTimeTable(TimeTable input) {
        this();
        addTimeTable(input);
    }

    /**
     * Adds {@code toAdd} to the {@code DeconflictTimeTable}
     * @param toAdd {@code TimeTable} to add
     */
    public void addTimeTable(TimeTable toAdd) {
        for (TimeSlot timeSlot : toAdd.getTimeSlots()) {
            addTimeSlot(timeSlot);
        }
    }

    @Override
    /**
     * Adds {@code toAdd} to the {@code DeconflictTimeTable}
     * @param toAdd {@code TimeSlot} to add
     */
    public void addTimeSlot(TimeSlot toAdd) {
        TimeSlot merged = new TimeSlot(toAdd);

        for (TimeSlot overlap : findOverlapOrAdjacent(toAdd)) {
            try {
                merged.mergeInto(overlap);
            } catch (TimeSlotNotOverlapException e) {
                // This should not happen since findOverlapOrAdjacent() only returns timeslots that can be merged
            }
            removeTimeSlot(overlap);
        }

        super.addTimeSlot(merged);
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
