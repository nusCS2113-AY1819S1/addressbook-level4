package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyStockList;

/** Indicates the command to save current stock list version*/
public class SaveStockListVersionEvent extends BaseEvent {

    public final ReadOnlyStockList data;

    public SaveStockListVersionEvent(ReadOnlyStockList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of items " + data.getItemList().size();
    }
}
