package seedu.address.analysis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.transaction.TypicalTransactions.SALE_PEPSI;

import org.junit.Test;

import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.transaction.TransactionBuilder;

public class TransactionWithinThirtyDaysPredicateTest {
    @Test
    public void test_isWithinMonth_returnsTrue() {
        TransactionInThirtyDaysPredicate predicate = new TransactionInThirtyDaysPredicate();
        // NOTE: transaction date should be set to a more recent date
        Transaction transaction = new TransactionBuilder().withDrink("Pepsi").withDateToday()
                .withTransactionType("sale")
                .withAmountMoney("5.00")
                .withQuantity("40")
                .withDate("27/10/2018")
                .build();
        assertTrue(predicate.test(transaction));
    }

    @Test
    public void test_isNotWithinMonth_returnsFalse() {
        TransactionInThirtyDaysPredicate predicate = new TransactionInThirtyDaysPredicate();
        Transaction transaction = new TransactionBuilder(SALE_PEPSI)
                .withDate("1/10/2018")
                .build();
        assertFalse(predicate.test(transaction));
    }
}
