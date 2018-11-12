package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Attendees;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.StartTime;

//@@author: IcedCoffeeBoy

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String eventName;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String location;
    @XmlElement
    private List<String> attendees = new ArrayList<>();

    /* @XmlElement
      private List<XmlAttendees> attendees = new ArrayList<>();*/

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedEvent(String eventName, String description, String date,
                           String startTime, String endTime, String location) {
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.attendees = new ArrayList<>();
    }

    //TODO: Test for this
    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details with attendees.
     */
    public XmlAdaptedEvent(String eventName, String description, String date,
                           String startTime, String endTime, String location, List<String> attendees) {
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        if (attendees != null) {
            this.attendees = new ArrayList<>(attendees);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedEvent(Event source) {
        eventName = source.getEventName().fullName;
        description = source.getDescription().value;
        date = source.getDate().toString();
        startTime = source.getStartTime().time;
        endTime = source.getEndTime().time;
        location = source.getLocation().value;
        attendees = source.getAttendees().getAttendeesSet().stream()
                .collect(Collectors.toList());

        /* attendees = source.getAttendees().stream()
                .map(XmlAttendees::new)
                .collect(Collectors.toList());*/
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Event toModelType() throws IllegalValueException {
        final List<String> eventAttendees = new ArrayList<>();
        for (String name : attendees) {
            eventAttendees.add(name);
        }

        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventName.class.getSimpleName()));
        }
        if (!EventName.isValidName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_EVENT_CONSTRAINTS);
        }
        final EventName modelEventName = new EventName(eventName);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!EventDate.isValidDate(date)) {
            throw new IllegalValueException(EventDate.MESSAGE_DATE_CONSTRAINTS);
        }
        final EventDate modelDate = new EventDate(date);

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartTime.class.getSimpleName()));
        }
        if (!StartTime.isValidTime(startTime)) {
            throw new IllegalValueException(StartTime.MESSAGE_TIME_CONSTRAINTS);
        }
        final StartTime modelStartTime = new StartTime(startTime);


        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, EndTime.class.getSimpleName()));
        }
        if (!EndTime.isValidTime(endTime)) {
            throw new IllegalValueException(EndTime.MESSAGE_TIME_CONSTRAINTS);
        }
        final EndTime modelEndTime = new EndTime(endTime);


        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        Set<String> attendees = new HashSet<>(eventAttendees);
        final Attendees modelAttendees = new Attendees(attendees);
        return new Event(modelEventName, modelDescription, modelDate, modelStartTime,
                modelEndTime, modelLocation, modelAttendees);
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
                && Objects.equals(description, otherEvent.description)
                && Objects.equals(location, otherEvent.location);

    }

}
