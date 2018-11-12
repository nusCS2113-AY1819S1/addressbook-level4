package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;

/**
 * Unmodifiable view of event list
 */
public interface ReadOnlyEventList {
    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

    void sortByName();

    void sortByStartTime();

    void sortByEndTime();

    void sortByDate();

}
