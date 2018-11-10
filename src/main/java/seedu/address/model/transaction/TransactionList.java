package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.drink.Date;

/**
 * Represents a list of transactions for (currently) eternity, until cleared.
 */
public class TransactionList implements ReadOnlyTransactionList {

    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private Date lastUpdateDate;

    public TransactionList() {
        lastUpdateDate = new Date();
    }

    /**
     * Creates a TransactionList using the transactions in the {@code toBeCopied}
     */
    public TransactionList(TransactionList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code TransactionList} with {@code newData}.
     */
    public void resetData(TransactionList newData) {
        requireNonNull(newData);

        setTransactions(newData.getTransactionList());
    }

    /**
     * Replaces the contents of the inventory list with {@code drinks}.
     * {@code drinks} must not contain duplicate drinks.
     */
    public void setTransactions(ObservableList<Transaction> transactions) {
        requireAllNonNull(transactions);

        this.transactions = transactions;
    }


    /**
     * Adds {@code transaction} to the list of transactions
     */
    public void addTransaction(Transaction transaction) {
        requireNonNull(transaction);
        transactions.add(transaction);
        updateLastUpdateDate();
    }

    /**
     * Updates the {@code lastUpdateDate} depending on last sale or import.
     */
    private void updateLastUpdateDate() {
        lastUpdateDate = new Date();
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public String toString() {
        if (transactions.size() == 1) {
            return transactions.size() + " transaction";
        } else {
            return transactions.size() + " transactions";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionList // instanceof handles nulls
                && transactions.equals(((TransactionList) other).transactions));
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions;
    }
}
