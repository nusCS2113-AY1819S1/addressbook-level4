package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Represents a Event in the event list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    // Identity fields
    private final EventName eventName;
    private final Description description;
    private final LocalDate startTime; // date format: "2007-12-03"
    private final LocalDate endTime; // date format: "2007-12-03"
    private final Location location;

    // TODO: WILL BE IMPLEMENT IN THE NEXT VERSION FOR ADDING OF EMPOLYEES
    private final Set<Person> attendees = new HashSet<>();

    /**
     * Every field must be present not null
     */
    public Event(EventName eventName, Description description, LocalDate startTime, LocalDate endTime, Location location) {
        requireAllNonNull(eventName);
        this.eventName = eventName;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }



    public EventName getEventName() {
        return eventName;
    }

    public Description getDescription() {
        return description;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public Location getLocation() {
        return location;
    }

    public Set<Person> getAttendees() {
        return attendees;
    }


    /**
     * Returns true if both event of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event event) {
        if (event == this) {
            return true;
        }
        return event != null
                && event.getEventName().equals(getEventName())
                && (event.getDescription().equals(getDescription())
                || event.getLocation().equals(getLocation())
                || event.getStartTime().equals(getStartTime())
                || event.getEndTime().equals(getEndTime()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Desciption: ")
                .append(getDescription())
                .append(" Location: ")
                .append(getLocation());
        if (startTime != null) {
            builder.append(" Start time: ")
                    .append(getStartTime());
        }
        if (endTime != null) {
            builder.append(" End time: ")
                    .append(getEndTime());
        }
        return builder.toString();
    }
}
