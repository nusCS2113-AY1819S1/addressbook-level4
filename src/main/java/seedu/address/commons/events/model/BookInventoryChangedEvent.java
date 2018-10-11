package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookInventory;
// import seedu.address.model.request.ReadOnlyRequests;

/** Indicates the BookInventory in the model has changed*/
public class BookInventoryChangedEvent extends BaseEvent {

    public final ReadOnlyBookInventory data;

    public BookInventoryChangedEvent(ReadOnlyBookInventory data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of books " + data.getBookList().size();
    }
}
