package seedu.address.model.person;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.scene.paint.Color;
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
    public static final Color DECONFLICT_TIMESLOT_COLOR = Color.RED;
    public static final Color DECONFLICT_INVERSE_TIMESLOT_COLOR = Color.LIGHTGREEN;
    public static final LocalTime DEFAULT_START = LocalTime.parse("10:00");
    public static final LocalTime DEFAULT_END = LocalTime.parse("19:00");

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

        merged.setColor(DECONFLICT_TIMESLOT_COLOR);
        addTimeSlotWithoutColor(merged);
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

    /**
     * Produces an inverse of the current {@code DeconflictTimeTable}, i.e. a {@code DeconflictTimeTable} with
     * {@code TimeSlot}s corresponding to the blanks in this {@code DeconflictTimeTable}
     *
     * Used for showing green slots in the UI
     *
     * @return Inverse of the current {@code DeconflictTimeTable}
     */
    public DeconflictTimeTable getInverse() {
        DeconflictTimeTable toReturn = new DeconflictTimeTable();

        TreeMap<DayOfWeek, TreeSet <TimeSlot>> splitTimeSlots = splitByDay();

        LocalTime currStart = getEarliest().isBefore(DEFAULT_START) ? getEarliest() : DEFAULT_START;
        LocalTime currEnd = getLatest().isAfter(DEFAULT_END) ? getLatest() : DEFAULT_END;

        for (DayOfWeek day : splitTimeSlots.keySet()) {
            if (splitTimeSlots.get(day).isEmpty()) {
                toReturn.addTimeSlotWithoutColor(
                        new TimeSlot(day, currStart, currEnd, DECONFLICT_INVERSE_TIMESLOT_COLOR));
            } else {
                TreeSet<TimeSlot> currSet = splitTimeSlots.get(day);

                if (!currStart.equals(currSet.first().getStartTime())) {
                    toReturn.addTimeSlotWithoutColor(
                            new TimeSlot(day, currStart, currSet.first().getStartTime(),
                                    DECONFLICT_INVERSE_TIMESLOT_COLOR));
                }

                for (TimeSlot curr : currSet) {
                    if (currSet.higher(curr) != null) {
                        TimeSlot next = currSet.higher(curr);
                        toReturn.addTimeSlotWithoutColor(
                                new TimeSlot(day, curr.getEndTime(), next.getStartTime(),
                                        DECONFLICT_INVERSE_TIMESLOT_COLOR));
                    }
                }

                if (!currEnd.equals(currSet.last().getEndTime())) {
                    toReturn.addTimeSlotWithoutColor(
                            new TimeSlot(day, currSet.last().getEndTime(), currEnd,
                                    DECONFLICT_INVERSE_TIMESLOT_COLOR));
                }
            }
        }

        return toReturn;
    }

    /**
     * Splits up the {@code TimeSlot}s in this {@code DeconflictTimeTable} by day, and sorts the {@code TimeSlot}s
     * @return {@code TreeMap} containing the {@code TimeSlot}s split by day
     */
    private TreeMap<DayOfWeek, TreeSet <TimeSlot>> splitByDay() {
        TreeMap<DayOfWeek, TreeSet<TimeSlot>> toReturn = new TreeMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            toReturn.put(day, new TreeSet<>());
        }

        for (TimeSlot timeSlot : timeSlots) {
            toReturn.get(timeSlot.getDayOfWeek()).add(timeSlot);
        }

        return toReturn;
    }
}
