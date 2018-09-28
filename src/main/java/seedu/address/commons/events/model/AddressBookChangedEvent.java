package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyStockList;

/** Indicates the StockList in the model has changed*/
public class StockListChangedEvent extends BaseEvent {

    public final ReadOnlyStockList data;

    public StockListChangedEvent(ReadOnlyStockList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of items " + data.getItemList().size();
    }
}
