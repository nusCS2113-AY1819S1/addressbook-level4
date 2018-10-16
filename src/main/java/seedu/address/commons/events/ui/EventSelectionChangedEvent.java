package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;

/**
 * Represents a selection change in the Event List Panel
 */
public class EventSelectionChangedEvent extends BaseEvent {


    private final Event newSelection;

    public EventSelectionChangedEvent(Event newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Event getNewSelection() {
        return newSelection;
    }
}
