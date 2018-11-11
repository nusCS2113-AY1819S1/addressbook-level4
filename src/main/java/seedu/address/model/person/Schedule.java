package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Schedule {
    public static final String MESSAGE_START_END_CONSTRAINTS = "Event end time must be after start time.";

    public final String schedulePrint;

    // Date fields
    private final TheDate date;

    // Time fields
    private final Time startTime;
    private final Time endTime;

    // Event Name field
    private final EventName eventName;

    /**
     * Every field must be present and not null.
     */
    public Schedule(TheDate date, Time startTime, Time endTime, EventName eventName) {
        requireAllNonNull(date, startTime, endTime, eventName);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = eventName;
        this.schedulePrint = this.toString();
    }

    /**
     * Returns true if startTime is before endTime.
     */
    public static boolean isValidStartEnd (String startTime, String endTime) {
        int startT = Integer.parseInt(startTime);
        int endT = Integer.parseInt(endTime);
        return (startT >= endT);
    }

    public TheDate getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public EventName getEventName() {
        return eventName;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, startTime, endTime, eventName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDate())
                .append(" StartTime: ")
                .append(getStartTime())
                .append(" EndTime: ")
                .append(getEndTime())
                .append(" EventName: ")
                .append(getEventName());
        return builder.toString();
    }

}
