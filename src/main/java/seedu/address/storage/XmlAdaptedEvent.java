package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Events.Description;
import seedu.address.model.Events.Event;
import seedu.address.model.Events.EventDate;
import seedu.address.model.Events.EventName;
import seedu.address.model.Events.Venue;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String eventName;
    @XmlElement(required = true)
    private String venue;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String eventDate;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */
    public XmlAdaptedEvent(String eventName, String venue, String description, String eventDate) {
        this.eventName = eventName;
        this.venue = venue;
        this.description = description;
        this.eventDate = eventDate;
    }

    /**
     * Converts a given Event into this class for JAXB use.
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        eventName = source.getEventName().ThisName;
        venue = source.getVenue().ThisVenue;
        description = source.getDescription().ThisDescription;
        eventDate = source.getEventDate().ThisDate;
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventName.class.getSimpleName()));
        }
        if (!EventName.checkValid(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_EVENTNAME_CONSTRAINTS);
        }
        final EventName modelEventName = new EventName(eventName);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Venue.class.getSimpleName()));
        }
        if (!Venue.checkValid(venue)) {
            throw new IllegalValueException(Venue.MESSAGE_VENUE_CONSTRAINT);
        }
        final Venue modelVenue = new Venue(venue);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.checkValid(description)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CCONSTRAINT);
        }
        final Description modelDescription = new Description(description);

        if (eventDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventDate.class.getSimpleName()));
        }
        if (!EventDate.checkValid(eventDate)) {
            throw new IllegalValueException(EventDate.MESSAGE_EVENTDATE_CONSTRAINTS);
        }
        final EventDate modelEventDate = new EventDate(eventDate);
        return new Event(modelEventName, modelVenue, modelDescription, modelEventDate);
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
                && Objects.equals(venue, otherEvent.venue)
                && Objects.equals(description, otherEvent.description)
                && Objects.equals(eventDate, otherEvent.eventDate);
    }
}
