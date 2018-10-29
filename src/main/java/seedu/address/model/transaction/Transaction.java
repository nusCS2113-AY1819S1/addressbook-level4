package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.drink.Date;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Price;
import seedu.address.model.drink.Quantity;

/**
 * Represents a transaction in the company.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {
    /*
    What this class does: records the transaction made by stocktaker or seller
    types of transactions: sale OR import (buying)
    what to record:
    - type of transaction
    - date of transaction
    - what drink (singular) is bought / sold
    - number of cartons bought / sold
    - amount spent / earned in this transaction
        (this is the total amount)
     */

    private TransactionType transactionType;
    private Date transactionDate;
    private Drink drinkTransacted;
    private Quantity quantityTransacted;
    private Price amountMoney;
    // TODO: create unique id based on time?

    public Transaction(TransactionType transactionType, Drink drinkTransacted,
                       Quantity quantityTransacted, Price amountMoney) {
        requireAllNonNull(transactionType, drinkTransacted, quantityTransacted, amountMoney);
        this.transactionType = transactionType;
        this.drinkTransacted = drinkTransacted;
        this.quantityTransacted = quantityTransacted;
        this.amountMoney = amountMoney;
        transactionDate = new Date();
    }

    /*
    methods to implement: (other than getters cos transactions (finance) are immutable so no setters)
    1. VIEW the transaction details
     */

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Drink getDrinkTransacted() {
        return drinkTransacted;
    }

    public Price getAmountMoney() {
        return amountMoney;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Quantity getQuantityTransacted() {
        return quantityTransacted;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Transaction that = (Transaction) other;
        return getTransactionType() == that.getTransactionType()
                && getTransactionDate().equals(that.getTransactionDate())
                && getDrinkTransacted().isSameDrink(that.getDrinkTransacted())
                && getQuantityTransacted().equals(that.getQuantityTransacted())
                && getAmountMoney().equals(that.getAmountMoney());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionType(), getTransactionDate(), getDrinkTransacted(),
                getQuantityTransacted(), getAmountMoney());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTransactionDate())
                .append(" Date: ")
                .append(getTransactionType())
                .append(" Type: ")
                .append(getDrinkTransacted())
                .append(" Drink: ")
                .append(getQuantityTransacted())
                .append(" Quantity: ")
                .append(getAmountMoney())
                .append(" Amount: ");
        return builder.toString();
    }
}
