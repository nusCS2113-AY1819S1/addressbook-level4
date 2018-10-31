package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Time;
import seedu.address.model.eventContacts.EventContacts;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_NAME = "Watch Movie";
    public static final String DEFAULT_DATE = "11112011";
    public static final String DEFAULT_TIME = "1111";

    private EventName eventName;
    private Date eventDate;
    private Time eventTime;
    private Set<EventContacts> eventContacts;

    public EventBuilder() {

        eventName = new EventName(DEFAULT_EVENT_NAME);
        eventDate = new Date(DEFAULT_DATE);
        eventTime = new Time(DEFAULT_TIME);
        eventContacts = new HashSet<>();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {

        eventName = eventToCopy.getEventName();
        eventDate = eventToCopy.getEventDate();
        eventTime = eventToCopy.getEventTime();
        eventContacts = new HashSet<>(eventToCopy.getEventContacts());
    }

    /**
     * Sets the {@code EventCategory} of the {@code Event} that we are building.
     */
    public EventBuilder withEventCategory(String eventName) {
        this.eventName = new EventName(eventName);
        return this;
    }

    /**
     * Parses the {@code eventContacts} into a {@code Set<EventContacts>} and set it to the {@code Event}
     * that we are building.
     */
    public EventBuilder withTags(String ... tags) {
        this.eventContacts = SampleDataUtil.getEventContactSet(tags);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Event} that we are building.
     */
    public EventBuilder withEventDate(String eventDate) {
        this.eventDate = new Date(eventDate);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Event} that we are building.
     */
    public EventBuilder withEventTime(String eventTime) {
        this.eventTime = new Time(eventTime);
        return this;
    }

    public Event build() {
        return new Event(eventName, eventDate, eventTime, eventContacts);
    }

}
