package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.eventContacts.EventContacts;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    private final EventName eventName;
    private final Date date;
    private final Time time;
    private final Set<EventContacts> eventContacts = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Event(EventName eventName, Date date, Time time, Set<EventContacts> eventContacts) {
        requireAllNonNull(eventName, date, time, eventContacts);
        this.eventName = eventName;
        this.date = date;
        this.time = time;
        this.eventContacts.addAll(eventContacts);
    }

    public EventName getEventName() {
        return eventName;
    }

    public Date getEventDate() {
        return date;
    }

    public Time getEventTime() {
        return time;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<EventContacts> getEventContacts() {
        return Collections.unmodifiableSet(eventContacts);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, date, time, eventContacts);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Event Date: ")
                .append(getEventDate())
                .append(" Event Time: ")
                .append(getEventTime())
                .append(" Contacts: ");
        getEventContacts().forEach(builder::append);
        return builder.toString();
    }
}
