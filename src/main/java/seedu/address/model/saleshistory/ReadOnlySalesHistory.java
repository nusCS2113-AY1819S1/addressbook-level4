package seedu.address.model.saleshistory;

import javafx.collections.ObservableList;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.Transaction;

/**
 * Unmodifiable view of the {@link SalesHistory}
 */
public interface ReadOnlySalesHistory {

    /**
     * Returns an unmodifiable view of the transactionRecord and reminderRecord
     */
    ObservableList<Transaction> getTransactionsAsObservableList();

    ObservableList<Reminder> getRemindersAsObservableList();
}
