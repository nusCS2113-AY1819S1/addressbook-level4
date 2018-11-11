package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.transaction.ReadOnlyTransactionList;

/**
 * Indicates the InventoryList in the model has changed
 */
public class TransactionListChangedEvent extends BaseEvent {

    public final ReadOnlyTransactionList data;

    public TransactionListChangedEvent(ReadOnlyTransactionList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of transactions: " + data.getTransactionList().size();
    }
}
