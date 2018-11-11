package seedu.address.analysis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.transaction.TypicalTransactions.SALE_PEPSI;

import org.junit.Test;

import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.transaction.TransactionBuilder;

public class TransactionInADayPredicateTest {
    @Test
    public void test_isWithinDay_returnsTrue() {
        TransactionInDayPredicate predicate = new TransactionInDayPredicate();
        Transaction transactionToday = new TransactionBuilder().withDrink("Pepsi").withDateToday()
                .withTransactionType("sale")
                .withAmountMoney("5.00")
                .withQuantity("40")
                .build();
        assertTrue(predicate.test(transactionToday));
    }

    @Test
    public void test_isNotWithinDay_returnsFalse() {
        TransactionInDayPredicate predicate = new TransactionInDayPredicate();
        Transaction transaction = new TransactionBuilder(SALE_PEPSI).build();
        assertFalse(predicate.test(transaction));
    }

}
