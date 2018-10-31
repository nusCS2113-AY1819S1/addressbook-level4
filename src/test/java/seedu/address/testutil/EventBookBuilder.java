package seedu.address.testutil;

import seedu.address.model.EventBook;
import seedu.address.model.event.Event;

/**
 * A utility class to help with building EventBook objects.
 * Example usage: <br>
 *     {@code EventBook ev = new EventBookBuilder().withEvent("John", "Doe").build();}
 */
public class EventBookBuilder {

    private EventBook eventBook;

    public EventBookBuilder() {
        eventBook = new EventBook();
    }

    public EventBookBuilder(EventBook eventBook) {
        this.eventBook = eventBook;
    }

    /**
     * Adds a new {@code Event} to the {@code EventBook} that we are building.
     */
    public EventBookBuilder withEvent(Event event) {
        eventBook.addEvent(event);
        return this;
    }

    public EventBook build() {
        return eventBook;
    }
}
