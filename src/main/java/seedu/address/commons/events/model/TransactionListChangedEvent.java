package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.transaction.Transaction;

/**
 * Indicates the InventoryList in the model has changed
 */
public class TransactionListChangedEvent extends BaseEvent {

    public final ObservableList<Transaction> data;

    public TransactionListChangedEvent(ObservableList<Transaction> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of transactions: " + data.size();
    }

    public ObservableList<Transaction> getData() {
        return data;
    }
}
