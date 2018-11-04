package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.drink.Date;

/**
 * Represents a list of transactions for (currently) eternity, until cleared.
 */
public class TransactionList {

    private List<Transaction> transactions;
    private Date lastUpdateDate;

    public TransactionList() {
        transactions = new ArrayList<>();
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

        setTransactions(newData.getTransactions());
    }

    /**
     * Replaces the contents of the inventory list with {@code drinks}.
     * {@code drinks} must not contain duplicate drinks.
     */
    public void setTransactions(List<Transaction> transactions) {
        requireAllNonNull(transactions);

        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Adds {@code transaction} to the list of transactions
     * Guarantees: transaction is accurately generated.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        updateLastUpdateDate();
    }

    /**
     * Updates the {@code lastUpdateDate} depending on last sale or import.
     */
    private void updateLastUpdateDate() {
        lastUpdateDate = new Date();
        // TODO: STUB
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        transactions.forEach(builder::append);
        return builder.toString();
    }
}
