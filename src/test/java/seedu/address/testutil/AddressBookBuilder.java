package seedu.address.testutil;

import seedu.address.model.EventManager;
import seedu.address.model.event.Event;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code EventManager ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private EventManager eventManager;

    public AddressBookBuilder() {
        eventManager = new EventManager();
    }

    public AddressBookBuilder(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Adds a new {@code Event} to the {@code EventManager} that we are building.
     */
    public AddressBookBuilder withPerson(Event event) {
        eventManager.addEvent(event);
        return this;
    }

    public EventManager build() {
        return eventManager;
    }
}
