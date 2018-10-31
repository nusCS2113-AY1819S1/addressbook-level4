package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.saleshistory.ReadOnlySalesHistory;
import seedu.address.model.saleshistory.SalesHistory;

/**
 * Indicates a change in the {@link SalesHistory}
 */
public class SalesHistoryChangedEvent extends BaseEvent {

    public final ReadOnlySalesHistory data;

    public SalesHistoryChangedEvent(ReadOnlySalesHistory data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of transactions: " + data.getTransactionsAsObservableList().size()
                + " number of reminders: " + data.getRemindersAsObservableList().size();
    }
}
