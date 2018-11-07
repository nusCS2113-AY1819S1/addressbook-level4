package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyProductDatabase;

/** Indicates the ProductDatabase in the model has changed*/
public class ProductDatabaseChangedEvent extends BaseEvent {

    public final ReadOnlyProductDatabase data;

    public ProductDatabaseChangedEvent(ReadOnlyProductDatabase data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of products " + data.getProductList().size();
    }
}
