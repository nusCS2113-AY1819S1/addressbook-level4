package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyEventList;

/** Indicates the EventList in the model has changed*/
public class EventListChangedEvent extends BaseEvent {

    public final ReadOnlyEventList data;

    public EventListChangedEvent(ReadOnlyEventList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size();
    }
}
