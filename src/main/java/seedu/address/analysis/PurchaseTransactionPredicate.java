package seedu.address.analysis;

import java.util.function.Predicate;

import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionType;

/**
 * Tests that a {@code Transaction} is a purchase.
 */
public class PurchaseTransactionPredicate implements Predicate<Transaction> {
    @Override
    public boolean test(Transaction transaction) {
        return transaction.getTransactionType() == TransactionType.PURCHASE;
    }

}
