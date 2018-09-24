package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookInventory;

/** Indicates the BookInventoryInventory in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyBookInventory data;

    public AddressBookChangedEvent(ReadOnlyBookInventory data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
