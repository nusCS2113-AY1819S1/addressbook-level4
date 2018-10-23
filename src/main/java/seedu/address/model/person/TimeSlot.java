package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

import seedu.address.model.person.exceptions.TimeSlotNotOverlapException;

/**
 * Represents a single TimeSlot in TimeTable Class
 *
 */
public class TimeSlot {
    // TODO: Make constraint message more descriptive
    public static final String MESSAGE_GENERAL_CONSTRAINTS = "Does not fit constraints!";
    public static final String MESSAGE_CANNOT_PARSE_DAY = "Accepted day format: MONDAY";
    public static final String MESSAGE_CANNOT_PARSE_TIME = "Accepted time format: 8-10";
    public static final String MESSAGE_INVALID_TIME_SLOT = "Invalid TimeSlot";

    // TODO: Change to accept times with non 0 minutes
    public static final String VALIDATION_REGEX =
            "\\w+(\\s*)([0-9]|[0-1][0-9]|[2][0-3])[:]?[0]?[0]?(\\s*)[-](\\s*)([0-9]|[0-1][0-9]|[2][0-3])[:]?[0]?[0]?";

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String label;

    public TimeSlot(DayOfWeek day, LocalTime start, LocalTime end) {
        requireAllNonNull(day, start, end);

        checkArgument(isValidTimeSlot(start, end), MESSAGE_INVALID_TIME_SLOT);

        dayOfWeek = day;
        startTime = start;
        endTime = end;
    }

    public TimeSlot(TimeSlot input) {
        dayOfWeek = input.dayOfWeek;
        startTime = input.startTime;
        endTime = input.endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

    public String getLabel() {
        return label;
    }

    public static boolean isValidTimeSlot(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean isValidTimeSlot(LocalTime start, LocalTime end) {
        return !(start.equals(end) || start.isAfter(end));
    }

    /**
     * Checks whether this {@code TimeSlot} overlaps with {@code toCompare}
     *
     * @param toCompare {@code TimeSlot} to compare against
     * @return Whether this {@code TimeSlot} overlaps with {@code toCompare}
     */
    public boolean isOverlap(TimeSlot toCompare) {
        boolean isNotOverlapTime = (this.endTime.isBefore(toCompare.startTime)
                || toCompare.endTime.isBefore(this.startTime)
                || isAdjacent(toCompare));

        return isSameDay(toCompare) && !isNotOverlapTime;
    }

    /**
     * Checks whether this {@code TimeSlot} is adjacent to {@code toCompare}
     *
     * @param toCompare {@code TimeSlot} to compare against
     * @return Whether this {@code TimeSlot} is adjacent to {@code toCompare}
     */
    public boolean isAdjacent(TimeSlot toCompare) {
        return isSameDay(toCompare)
                && (this.endTime.equals(toCompare.startTime) || this.startTime.equals(toCompare.endTime));
    }

    public boolean isSameDay(TimeSlot toCompare) {
        return this.dayOfWeek == toCompare.dayOfWeek;
    }

    /**
     * Merges {@code toMerge} into this {@code TimeSlot}
     * This {@code TimeSlot} must overlap or be adjacent with {@code toMerge}
     *
     * @param toMerge {@code TimeSlot} to be merged
     * @throws TimeSlotNotOverlapException if {@code toMerge} does not overlap with this {@code TimeSlot}
     */
    public void mergeInto(TimeSlot toMerge) throws TimeSlotNotOverlapException {
        TimeSlot merged;

        try {
            merged = merge(toMerge);
        } catch (TimeSlotNotOverlapException e) {
            throw e;
        }

        this.startTime = merged.startTime;
        this.endTime = merged.endTime;
        this.dayOfWeek = merged.dayOfWeek;
    }

    /**
     * Returns a {@code TimeSlot} with {@code toMerge} merged with this {@code TimeSlot}
     * This {@code TimeSlot} must overlap or be adjacent with {@code toMerge}
     *
     * @param toMerge {@code TimeSlot} to be merged
     * @return Merged {@code TimeSlot}
     * @throws TimeSlotNotOverlapException if {@code toMerge} does not overlap with this {@code TimeSlot}
     */
    public TimeSlot merge(TimeSlot toMerge) throws TimeSlotNotOverlapException {
        if (!isOverlap(toMerge) && !isAdjacent(toMerge)) {
            throw new TimeSlotNotOverlapException();
        }

        LocalTime toReturnStart = this.startTime.isBefore(toMerge.startTime) ? this.startTime : toMerge.startTime;
        LocalTime toReturnEnd = this.endTime.isAfter(toMerge.endTime) ? this.endTime : toMerge.endTime;
        DayOfWeek toReturnDay = this.dayOfWeek;

        return new TimeSlot(toReturnDay, toReturnStart, toReturnEnd);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH))
                .append(" ")
                .append(startTime)
                .append(" - ")
                .append(endTime);
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimeSlot)) {
            return false;
        }

        TimeSlot otherTimeSlot = (TimeSlot) other;

        return otherTimeSlot.getDayOfWeek().equals(getDayOfWeek())
                && otherTimeSlot.startTime.equals(startTime)
                && otherTimeSlot.endTime.equals(endTime);
    }

    /**
     * converts DayOfWeek object into the corresponding abbreviation.
     * Possible outputs:
     * MO, TU, WE, TH, FR, SA, SU
     */
    public String getAbbreviationFromDayOfWeek() {
        String dayString = dayOfWeek.getDisplayName(TextStyle.FULL , Locale.ENGLISH);
        return dayString.substring(0, 2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, startTime, endTime);
    }

}
