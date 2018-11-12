package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents a Schedule of a Person in address book.
 */
public class Schedule {
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Start Time and End Time should not be the same";

    private TimeStart timeStart;
    private TimeEnd timeEnd;
    private Venue venue;

    public Schedule(TimeStart timeStart, TimeEnd timeEnd, Venue venue) throws CommandException {
        requireAllNonNull(timeStart, timeEnd, venue);
        if (!isValidSchedule(timeStart, timeEnd)) {
            throw new CommandException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.venue = venue;
    }

    public TimeStart getTimeStart() {
        return timeStart;
    }

    public TimeEnd getTimeEnd() {
        return timeEnd;
    }

    public Venue getVenue() {
        return venue;
    }

    public static boolean isValidSchedule(TimeStart timeStart, TimeEnd timeEnd) {
        return !timeStart.toString().equals(timeEnd.toString());
    }

    public boolean isNextDay() {
        return (Integer.parseInt(this.timeStart.toString()) > Integer.parseInt(this.timeEnd.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return otherSchedule.getTimeStart().equals(getTimeStart())
                && otherSchedule.getTimeEnd().equals(getTimeEnd())
                && otherSchedule.getVenue().equals(getVenue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStart, timeEnd, venue);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Start Time:\t")
                .append(getTimeStart())
                .append("\nEnd Time:\t\t")
                .append(getTimeEnd());
        if (isNextDay()) {
            builder.append(" (next day)");
        }
        builder.append("\nVenue:\t\t")
                .append(getVenue());
        return builder.toString();
    }

}
