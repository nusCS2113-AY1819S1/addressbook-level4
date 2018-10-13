package seedu.address.model.person;

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

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String label;

    public TimeSlot(DayOfWeek day, LocalTime start, LocalTime end) {
        setStartTime(start);
        setEndTime(end);
        setDayOfWeek(day);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Checks whether this TimeSlot overlaps with toCompare
     *
     * @param toCompare TimeSlot to compare against
     * @return Whether this TimeSlot overlaps with toCompare
     */
    public boolean isOverlap(TimeSlot toCompare) {
        boolean isSameDay = this.getDayOfWeek() == toCompare.getDayOfWeek();
        boolean isOverlapTime = this.getStartTime().isBefore(toCompare.getEndTime())
                                || toCompare.getStartTime().isBefore(this.getEndTime());

        return isSameDay && isOverlapTime;
    }

    /**
     * Concatenates two overlapping TimeSlots
     * @param toConcat TimeSlot to be concatenated
     * @return Concatenated TimeSlot
     */
    public TimeSlot concatTimeSlots(TimeSlot toConcat) {
        assert isOverlap(toConcat);

        LocalTime newStart = (this.getStartTime().isBefore(toConcat.getStartTime()))
                             ? this.getStartTime() : toConcat.getStartTime();

        LocalTime newEnd = (this.getEndTime().isAfter(toConcat.getEndTime()))
                           ? this.getEndTime() : toConcat.getEndTime();

        return new TimeSlot(this.getDayOfWeek(), newStart, newEnd);
    }
}
