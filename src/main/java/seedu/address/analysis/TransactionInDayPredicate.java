package seedu.address.analysis;

import seedu.address.model.drink.Date;
import seedu.address.model.transaction.Transaction;

/**
 * Tests that a {@code Transaction} occurred in the current day.
 */
public class TransactionInDayPredicate extends TransactionPeriodPredicate {
    @Override
    public boolean test(Transaction transaction) {
        return transaction.getTransactionDate().compareTo(new Date()) == 0;
    }
}
