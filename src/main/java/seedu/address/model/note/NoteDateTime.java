package seedu.address.model.note;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a combined date and time of a note.
 */
public class NoteDateTime {

    private final LocalDate date;
    private final LocalTime time;

    public NoteDateTime(NoteDate date, NoteTime time) {
        this.date = LocalDate.of(
                date.getDate().getYear(),
                date.getDate().getMonthValue(),
                date.getDate().getDayOfMonth());

        this.time = LocalTime.of(time.getTime().getHour(), time.getTime().getMinute());
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    /**
     * This method compares a start date&time and end date&time.
     *
     * Returns true if start date&time is earlier than or equal to end date&time.
     * Return false otherwise.
     */
    public static boolean hasValidDateTimeDifference(NoteDateTime startDateTime, NoteDateTime endDateTime) {
        LocalDateTime start = LocalDateTime.of(startDateTime.getDate(), startDateTime.getTime());
        LocalDateTime end = LocalDateTime.of(endDateTime.getDate(), endDateTime.getTime());

        // result = 0, equal, valid
        // result > 0, end > start, valid
        // result < 0, end < start, invalid
        int result = end.compareTo(start);

        return result >= 0;
    }
}
