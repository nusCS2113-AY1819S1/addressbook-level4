package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Represents a single timeslot in TimeTable Class
 *
 */
public class TimeSlot {
    public static final String MESSAGE_NOT_ENOUGH_ARGUMENTS = "Accepted argument example: Monday 8-10";
    public static final String MESSAGE_CANNOT_PARSE_DAY = "Accepted day format: MONDAY";
    public static final String MESSAGE_CANNOT_PARSE_TIME = "Accepted time format: 8-10";
    public static final String MESSAGE_INVALID_TIMESLOT = "Invalid TimnSlot";

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String label;

    public TimeSlot(DayOfWeek day, LocalTime start, LocalTime end) {
        requireNonNull(day);
        requireNonNull(start);
        requireNonNull(end);

        checkArgument(isValidTimeSlot(start, end), MESSAGE_INVALID_TIMESLOT);

        dayOfWeek = day;
        startTime = start;
        endTime = end;
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

    public String getLabel() {
        return label;
    }

    public static boolean isValidTimeSlot(LocalTime start, LocalTime end) {
        return !(start.equals(end) || start.isAfter(end));
    }

    /**
     * Returns a printable string representing this TimeSlot
     *
     * @return Printable string representing this TimeSlot
     */
    public String getPrintableString() {
        String toReturn = new String();

        toReturn += this.getLabel();
        toReturn += " (";

        toReturn += this.getDayOfWeek().toString();
        toReturn += " ";

        toReturn += this.getStartTime().toString();
        toReturn += "-";

        toReturn += this.getEndTime().toString();
        toReturn += ")";

        return toReturn;
    }

    /**
     * Checks whether this TimeSlot overlaps with toCompare
     *
     * @param toCompare TimeSlot to compare against
     * @return Whether this TimeSlot overlaps with toCompare
     */
    public boolean isOverlap(TimeSlot toCompare) {
        boolean isSameDay = this.getDayOfWeek() == toCompare.getDayOfWeek();
        boolean isNotOverlapTime = (this.getEndTime().isBefore(toCompare.getStartTime())
                                    || this.getEndTime().equals(toCompare.getStartTime())
                                    || toCompare.getEndTime().isBefore(this.getStartTime())
                                    || toCompare.getEndTime().equals(this.getStartTime()));

        return isSameDay && !isNotOverlapTime;
    }
}
