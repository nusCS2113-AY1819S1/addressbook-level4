package seedu.address.model;

import seedu.address.model.event.Event;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an event manager
 */
public interface ReadOnlyEventManager {

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

}
