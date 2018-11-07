//@@author ian-tjahjono
package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Time;
import seedu.address.model.eventContacts.EventContacts;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String eventName;
    @XmlElement(required = true)
    private String eventDate;
    @XmlElement(required = true)
    private String eventTime;

    @XmlElement
    private List<XmlAdaptedEventContact> ec = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */
    public XmlAdaptedEvent(String eventName, String eventDate, String eventTime, List<XmlAdaptedEventContact> ec) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        if (ec != null) {
            this.ec = new ArrayList<>(ec);
        }
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        eventName = source.getEventName().fullName;
        eventDate = source.getEventDate().value;
        eventTime = source.getEventTime().value;
        ec = source.getEventContacts().stream()
                .map(XmlAdaptedEventContact::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted Event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Event
     */
    public Event toModelType() throws IllegalValueException {
        final List<EventContacts> eventContacts = new ArrayList<>();
        for (XmlAdaptedEventContact tag : ec) {
            eventContacts.add(tag.toModelType());
        }

        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventName.class.getSimpleName()));
        }
        if (!EventName.isValidName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
        }
        final EventName modelEventName = new EventName(eventName);

        if (eventDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(eventDate)) {
            throw new IllegalValueException(Date.MESSAGE_EVENT_DATE_CONSTRAINTS);
        }
        final Date modelDate = new Date(eventDate);

        if (eventTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(eventTime)) {
            throw new IllegalValueException(Time.MESSAGE_EVENT_TIME_CONSTRAINTS);
        }
        final Time modelTime = new Time(eventTime);

        final Set<EventContacts> modelEventContacts = new HashSet<>(eventContacts);
        return new Event(modelEventName, modelDate, modelTime, modelEventContacts);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEvent)) {
            return false;
        }

        XmlAdaptedEvent otherEvent = (XmlAdaptedEvent) other;
        return Objects.equals(eventName, otherEvent.eventName)
                && Objects.equals(eventDate, otherEvent.eventDate)
                && Objects.equals(eventTime, otherEvent.eventTime)
                && ec.equals(otherEvent.ec);
    }
}
