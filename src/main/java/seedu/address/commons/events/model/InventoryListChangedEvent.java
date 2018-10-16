package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyInventoryList;

/**
 * Indicates the InventoryList in the model has changed
 */
public class InventoryListChangedEvent extends BaseEvent {

    public final ReadOnlyInventoryList data;

    public InventoryListChangedEvent(ReadOnlyInventoryList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of drinks " + data.getDrinkList().size();
    }
}
