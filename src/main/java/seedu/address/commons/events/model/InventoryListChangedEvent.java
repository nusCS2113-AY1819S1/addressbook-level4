package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyInventoryList;

/**
 * Indicates the inventoryList in the model has changed
 */
public class InventoryListChangedEvent extends BaseEvent {

    public final ReadOnlyInventoryList data;

    public InventoryListChangedEvent(ReadOnlyInventoryList input) {
        this.data = input;
    }

    @Override
    public String toString() {
        return "number of drinks " + data.getDrinkList().size();
    }
}
