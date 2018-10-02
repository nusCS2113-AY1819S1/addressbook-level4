package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookInventory;

/** Indicates the BookInventory in the model has changed*/
public class BookInventoryChangedEvent extends BaseEvent {

    public final ReadOnlyBookInventory data;

    public BookInventoryChangedEvent(ReadOnlyBookInventory data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getBookList().size();
    }
}
