package seedu.address.analysis;

import seedu.address.model.drink.Date;
import seedu.address.model.transaction.Transaction;

/**
 * ToDO: fill up this
 */
public class TransactionInThirtyDaysPredicate extends TransactionPeriodPredicate {
    @Override
    public boolean test(Transaction transaction) {
        return transaction.getTransactionDate().isBetween(new Date().getDateTwentyNineDaysBefore(), new Date());
    }
}
