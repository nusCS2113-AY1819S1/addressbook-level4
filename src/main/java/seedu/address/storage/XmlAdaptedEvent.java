package seedu.address.storage;

import java.time.LocalDate;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.StartTime;


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

    //TODO: IMPLEMENT LIST OF ATTENDEES
    //TODO: CREATE XMLATTENDEES

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
        /*if (attendees != null) {
            this.attendees = new ArrayList<>(attendees);
        }*/
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
        startTime = source.getStartTime().startTime;
        endTime = source.getEndTime().endTime;
        location = source.getLocation().value;
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
        /*final List<Person> eventAttendees = new ArrayList<>();
        for (XmlAttendees attendee : attendees) {
            eventAttendees.add(attendee.toModelType());
        }*/

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
            throw new IllegalValueException("Wrong date format");
        }

        final LocalDate modelDate = LocalDate.parse(date);


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

        //final Set<Person> modelAttendees = new HashSet<>(eventAttendees);
        return new Event(modelEventName, modelDescription, modelDate, modelStartTime, modelEndTime, modelLocation);
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
