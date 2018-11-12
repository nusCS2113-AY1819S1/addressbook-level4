package seedu.address.analysis;

import java.util.function.Predicate;

import seedu.address.model.transaction.Transaction;

/**
 * Abstract class that tests whether a {@code Transaction} occurred in the relevant period.
 */
public abstract class TransactionPeriodPredicate implements Predicate<Transaction> {
    @Override
    public abstract boolean test(Transaction transaction);
}
