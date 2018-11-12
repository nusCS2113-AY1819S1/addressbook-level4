package seedu.address.testutil;

import java.util.Set;

import seedu.address.model.event.Attendees;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.StartTime;
import seedu.address.model.util.SampleDataUtil;


/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "CS2113 Practical Exam";
    public static final String DEFAULT_DESCRIPTION = "Fun";
    public static final String DEFAULT_DATE = "2018-09-18";
    public static final String DEFAULT_START_TIME = "12:00";
    public static final String DEFAULT_END_TIME = "16:00";
    public static final String DEFAULT_LOCATION = "LT15";

    private EventName eventName;
    private Description description;
    private EventDate date;
    private StartTime startTime;
    private EndTime endTime;
    private Location location;
    private Attendees attendees;

    public EventBuilder() {
        eventName = new EventName(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        date = new EventDate(DEFAULT_DATE);
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
        date = eventToCopy.getDate();
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
     * Sets the {@code Date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = new EventDate(date);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(String startTime) {
        this.startTime = new StartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code Event} that we are building.
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

    /**
     * Sets the {@code Attendees} of the {@code Event} that we are building with emails.
     */
    public EventBuilder withAttendee(String... strings) {
        Set<String> set = SampleDataUtil.getAttendeeSet(strings);
        this.attendees = new Attendees(set);
        return this;
    }

    public Event build() {
        return new Event(eventName, description, date, startTime, endTime, location, attendees);
    }


}
