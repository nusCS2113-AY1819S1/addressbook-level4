package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;

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
