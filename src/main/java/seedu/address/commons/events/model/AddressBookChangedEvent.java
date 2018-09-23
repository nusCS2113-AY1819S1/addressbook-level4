package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyEventManager;

/** Indicates the EventManager in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyEventManager data;

    public AddressBookChangedEvent(ReadOnlyEventManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getEventList().size();
    }
}
