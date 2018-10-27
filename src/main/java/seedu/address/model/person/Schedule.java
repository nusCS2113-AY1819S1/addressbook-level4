package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Schedule {

    //Name fields
    private final Name name;

    // Date fields
    private final Date date;

    // Time fields
    private final Time startTime;
    private final Time endTime;

    // Event Name field
    private final EventName eventName;

    /**
     * Every field must be present and not null.
     */
    public Schedule(Name name, Date date, Time startTime, Time endTime, EventName eventName) {
        requireAllNonNull(name, date, startTime, endTime, eventName);
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = eventName;
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public EventName getEventName() { return eventName; }
//
//
//    /**
//     * Returns true if another event has the same start time.
//     * This prevents one person from adding the same event at the same start time.
//     */
//    public boolean isSameStartTime(Time otherStartTime) {
//        if (otherStartTime == this) {
//            return true;
//        }
//
//        return otherStartTime != null
//                && otherStartTime.getStartTime().equals(getStartTime())
//                && (otherEndTime.getEndTime().equals(getEndTime()));
//    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, startTime, endTime, eventName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
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