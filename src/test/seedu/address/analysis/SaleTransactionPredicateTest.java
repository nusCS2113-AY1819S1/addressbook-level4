package seedu.address.analysis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.transaction.TypicalTransactions.BUY_PEPSI;
import static seedu.address.testutil.transaction.TypicalTransactions.SALE_PEPSI;

import org.junit.Test;

public class SaleTransactionPredicateTest {
    @Test
    public void test_isSaleTransaction_returnsTrue() {
        SaleTransactionPredicate predicate = new SaleTransactionPredicate();
        assertTrue(predicate.test(SALE_PEPSI));
    }

    @Test
    public void test_isNotSaleTransaction_returnsFalse() {
        SaleTransactionPredicate predicate = new SaleTransactionPredicate();
        assertFalse(predicate.test(BUY_PEPSI));
    }
}
