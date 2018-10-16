package seedu.address.model.transaction;

import java.util.Objects;

import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Price;

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
    // private Date transactionDate;    // TODO: will be added when XH's code is merged
    private Drink drinkTransacted;
    // private Quantity quantityTransacted;
    private Price amountMoney;

    public Transaction() {}

    public Transaction(TransactionType transactionType, Drink drinkTransacted, Price amountMoney) {
        this.transactionType = transactionType;
        this.drinkTransacted = drinkTransacted;
        this.amountMoney = amountMoney;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return getTransactionType() == that.getTransactionType()
                && getDrinkTransacted().isSameDrink(that.getDrinkTransacted())
                && getAmountMoney().equals(that.getAmountMoney());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionType(), getDrinkTransacted(), getAmountMoney());
    }
}
