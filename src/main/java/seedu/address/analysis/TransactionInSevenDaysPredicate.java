package seedu.address.analysis;

import java.util.function.Predicate;

import seedu.address.model.drink.Date;
import seedu.address.model.transaction.Transaction;

/**
 * Tests that a {@code Transaction} occurred in the last 7 days.
 */
public class TransactionInSevenDaysPredicate implements Predicate<Transaction> {
    @Override
    public boolean test(Transaction transaction) {
        return transaction.getTransactionDate().isBetween(new Date(), new Date().getDateSixDaysBefore());
    }
}
