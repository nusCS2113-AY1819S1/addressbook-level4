package seedu.address.model.person;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeMap;

import javafx.scene.paint.Color;
import seedu.address.model.person.exceptions.TimeSlotDoesNotExistException;
import seedu.address.model.person.exceptions.TimeSlotOverlapException;
import seedu.address.model.person.exceptions.TimeTableEmptyException;

/**
 * Represents a {@code TimeTable} that is associated with a {@code Person}
 */
public class TimeTable {
    private static final ArrayList<Color> COLOR_LIST = new ArrayList<>();

    protected Collection <TimeSlot> timeSlots;

    // Since Java does not have a built-in multiset, a map is used to simulate a multiset
    private TreeMap<LocalTime, Integer> earlistSet;
    private TreeMap<LocalTime, Integer> latestSet;

    static {
        COLOR_LIST.add(Color.BLUE);
        COLOR_LIST.add(Color.GREEN);
        COLOR_LIST.add(Color.YELLOW);
        COLOR_LIST.add(Color.ORANGE);
        COLOR_LIST.add(Color.PINK);
        COLOR_LIST.add(Color.PURPLE);
    }

    public TimeTable() {
        timeSlots = new HashSet<>();
        earlistSet = new TreeMap<>();
        latestSet = new TreeMap<>();
    }

    public TimeTable(Collection <TimeSlot> input) throws TimeSlotOverlapException {
        this();
        for (TimeSlot timeSlot : input) {
            try {
                addTimeSlot(timeSlot);
            } catch (TimeSlotOverlapException e) {
                throw e;
            }
        }
    }

    /**
     * Copy constructor
     * @param input {@code TimeTable} to be copied
     */
    public TimeTable(TimeTable input) {
        this();
        updateTimeTable(input);
    }

    /**
     * Adds a {@code TimeSlot} to the {@code TimeTable} without assigning it a random color
     * Only used internally, see {@code addTimeSlot} for outward-facing API
     *
     * @param toAdd {@code TimeSlot} to be added
     * @throws TimeSlotOverlapException if {@code toAdd} overlaps with an existing {@code TimeSlot}
     */
    protected void addTimeSlotWithoutColor(TimeSlot toAdd) throws TimeSlotOverlapException {
        if (hasOverlap(toAdd)) {
            throw new TimeSlotOverlapException();
        }

        timeSlots.add(toAdd);

        if (earlistSet.containsKey(toAdd.getStartTime())) {
            int currCount = earlistSet.get(toAdd.getStartTime());
            earlistSet.remove(toAdd.getStartTime());
            earlistSet.put(toAdd.getStartTime(), currCount + 1);
        } else {
            earlistSet.put(toAdd.getStartTime(), 1);
        }

        if (latestSet.containsKey(toAdd.getEndTime())) {
            int currCount = latestSet.get(toAdd.getEndTime());
            latestSet.remove(toAdd.getEndTime());
            latestSet.put(toAdd.getEndTime(), currCount + 1);
        } else {
            latestSet.put(toAdd.getEndTime(), 1);
        }
    }

    public Collection <TimeSlot> getTimeSlots() {
        Collection <TimeSlot> toReturn = new HashSet<>();
        toReturn.addAll(timeSlots);
        return toReturn;
    }

    public LocalTime getEarliest() throws TimeTableEmptyException {
        if (earlistSet.isEmpty()) {
            throw new TimeTableEmptyException();
        } else {
            return earlistSet.firstKey();
        }
    }

    public LocalTime getLatest() throws TimeTableEmptyException {
        if (latestSet.isEmpty()) {
            throw new TimeTableEmptyException();
        } else {
            return latestSet.lastKey();
        }
    }

    // TODO: Make it such that it doesn't repeat colors too often
    public Color getRandomColor() {
        return COLOR_LIST.get((int) (Math.random() * COLOR_LIST.size()));
    }

    /**
     * Overwrites this {@code TimeTable} with {@code toReplace}
     * @param toReplace {@code TimeTable} to be copied
     */
    public void updateTimeTable(TimeTable toReplace) {
        clear();
        for (TimeSlot timeSlot : toReplace.getTimeSlots()) {
            addTimeSlotWithoutColor(timeSlot);
        }
    }

    /**
     * Adds a {@code TimeSlot} to the {@code TimeTable}
     *
     * @param toAdd {@code TimeSlot} to be added
     * @throws TimeSlotOverlapException if {@code toAdd} overlaps with an existing {@code TimeSlot}
     */
    public void addTimeSlot(TimeSlot toAdd) throws TimeSlotOverlapException {
        toAdd.setColor(getRandomColor());
        addTimeSlotWithoutColor(toAdd);
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

        int currCountEarlist = earlistSet.get(toRemove.getStartTime());
        earlistSet.remove(toRemove.getStartTime());

        if (currCountEarlist != 1) {
            earlistSet.put(toRemove.getStartTime(), currCountEarlist - 1);
        }

        int currCountLatest = latestSet.get(toRemove.getEndTime());
        latestSet.remove(toRemove.getEndTime());

        if (currCountLatest != 1) {
            latestSet.put(toRemove.getEndTime(), currCountLatest - 1);
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

    public boolean isEmpty() {
        return timeSlots.isEmpty();
    }

    /**
     * Clears this [@code TimeTable} object
     */
    public void clear() {
        timeSlots = new HashSet<>();
        earlistSet = new TreeMap<>();
        latestSet = new TreeMap<>();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimeTable)) {
            return false;
        }

        TimeTable otherTimeTable = (TimeTable) other;

        return otherTimeTable.getTimeSlots().equals(this.getTimeSlots());
    }
}
