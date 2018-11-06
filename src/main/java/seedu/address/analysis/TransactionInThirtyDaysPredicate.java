package seedu.address.analysis;

import java.util.function.Predicate;

import seedu.address.model.drink.Date;
import seedu.address.model.transaction.Transaction;

/**
 *  ToDO: fill up this
 */
public class TransactionInThirtyDaysPredicate implements Predicate<Transaction> {
    @Override
    public boolean test(Transaction transaction) {
        return transaction.getTransactionDate().between(new Date(), new Date().getDateTwentyNineDaysBefore());
    }
}
