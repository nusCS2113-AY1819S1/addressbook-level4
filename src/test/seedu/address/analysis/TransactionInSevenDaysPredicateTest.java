package seedu.address.analysis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.transaction.TypicalTransactions.SALE_PEPSI;

import org.junit.Test;

import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.transaction.TransactionBuilder;

public class TransactionInSevenDaysPredicateTest {
    @Test
    public void test_isWithinWeek_returnsTrue() {
        TransactionInSevenDaysPredicate predicate = new TransactionInSevenDaysPredicate();
        // NOTE: transaction date should be set to a more recent date
        Transaction transaction = new TransactionBuilder().withDrink("Pepsi").withDateToday()
                .withTransactionType("sale")
                .withAmountMoney("5.00")
                .withQuantity("40")
                .withDate("9/11/2018")
                .build();
        assertTrue(predicate.test(transaction));
    }

    @Test
    public void test_isNotWithinWeek_returnsFalse() {
        TransactionInSevenDaysPredicate predicate = new TransactionInSevenDaysPredicate();
        Transaction transaction = new TransactionBuilder(SALE_PEPSI)
                .withDate("1/11/2018")
                .build();
        assertFalse(predicate.test(transaction));
    }
}
