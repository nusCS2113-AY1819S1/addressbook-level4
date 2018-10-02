package seedu.address.testutil;

import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;

import java.time.LocalDate;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "CS2113 Meeting";
    public static final String DEFAULT_DESCRIPTION = "Weekly Meetup";
    public static final String DEFAULT_START_DATE = "2018-10-28";
    public static final String DEFAULT_END_DATE = "2018-10-29";
    public static final String DEFAULT_LOCATION = "LT15";

    private EventName eventName;
    private Description description;
    private LocalDate startTime; // date format: "2007-12-03"
    private LocalDate endTime; // date format: "2007-12-03"
    private Location location;

    public EventBuilder() {
        eventName = new EventName(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        startTime = LocalDate.parse(DEFAULT_START_DATE);
        endTime = LocalDate.parse(DEFAULT_END_DATE);
        location = new Location(DEFAULT_LOCATION);
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
     * Sets the {@code LocalDate} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(String date) {
        this.startTime = LocalDate.parse(date);
        return this;
    }

    /**
     * Sets the {@code LocalDate} of the {@code Event} that we are building.
     */
    public EventBuilder withEndTime(String date) {
        this.endTime = LocalDate.parse(date);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    public Event build() {
        return new Event(eventName, description, startTime, endTime, location);
    }


}
