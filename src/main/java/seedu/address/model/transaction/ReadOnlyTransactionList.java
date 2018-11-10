package seedu.address.model.transaction;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a transaction list.
 */
public interface ReadOnlyTransactionList {
    /**
     * Returns an unmodifiable view of the transaction list.
     */
    ObservableList<Transaction> getTransactionList();
}
