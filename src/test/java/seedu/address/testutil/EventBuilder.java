package seedu.address.testutil;

import java.time.LocalDate;
import java.util.Set;

import seedu.address.model.event.Attendees;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.StartTime;


/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "CS2113 Meeting";
    public static final String DEFAULT_DESCRIPTION = "Weekly Meetup";
    public static final String DEFAULT_DATE = "2018-10-10";
    public static final String DEFAULT_START_TIME = "12:00";
    public static final String DEFAULT_END_TIME = "16:00";
    public static final String DEFAULT_LOCATION = "LT15";

    private EventName eventName;
    private Description description;
    private LocalDate date;
    private StartTime startTime;
    private EndTime endTime;
    private Location location;
    private Attendees attendees;

    public EventBuilder() {
        eventName = new EventName(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        date = LocalDate.parse(DEFAULT_DATE);
        startTime = new StartTime(DEFAULT_START_TIME);
        endTime = new EndTime(DEFAULT_END_TIME);
        location = new Location(DEFAULT_LOCATION);
        attendees = new Attendees();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventName = eventToCopy.getEventName();
        description = eventToCopy.getDescription();
        startTime = eventToCopy.getStartTime();
        endTime = eventToCopy.getEndTime();
        location = eventToCopy.getLocation();
        attendees = eventToCopy.getAttendees();
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withEventName(String name) {
        this.eventName = new EventName(name);
        return this;
    }


    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = LocalDate.parse(date);
        return this;
    }

    /**
     * Sets the {@code LocalDate} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(String startTime) {
        this.startTime = new StartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code LocalDate} of the {@code Event} that we are building.
     */
    public EventBuilder withEndTime(String endTime) {
        this.endTime = new EndTime(endTime);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Attendees} of the {@code Event} that we are building.
     */
    public EventBuilder withAttendee(Set<String> attendeesSet) {
        this.attendees = new Attendees(attendeesSet);
        return this;
    }

    public Event build() {
        return new Event(eventName, description, date, startTime, endTime, location, attendees);
    }


}
