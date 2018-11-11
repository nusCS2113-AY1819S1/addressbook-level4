package seedu.address.testutil;

import seedu.address.model.EventList;
import seedu.address.model.event.Event;

/**
 * A utility class to help with building Eventlist objects.
 * Example usage: <br>
 * {@code Eventlist eventList = new EventListBuilder().withEvent(MEETING_1).build();}
 */
public class EventListBuilder {

    private EventList eventList;

    public EventListBuilder() {
        eventList = new EventList();
    }

    public EventListBuilder(EventList eventList) {
        this.eventList = eventList;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public EventListBuilder withEvent(Event event) {
        eventList.addEvent(event);
        return this;
    }

    public EventList build() {
        return eventList;
    }
}
