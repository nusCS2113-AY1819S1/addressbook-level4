package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyStockList;

/** Indicates the command to save current stock list version*/
public class SaveStockListVersionEvent extends BaseEvent {

    public final ReadOnlyStockList data;
    public final String fileName;

    public SaveStockListVersionEvent(ReadOnlyStockList data, String fileName) {
        this.data = data;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "number of items " + data.getItemList().size();
    }
}
