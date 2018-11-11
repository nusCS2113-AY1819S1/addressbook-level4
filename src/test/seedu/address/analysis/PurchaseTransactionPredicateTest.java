package seedu.address.analysis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.transaction.TypicalTransactions.BUY_PEPSI;
import static seedu.address.testutil.transaction.TypicalTransactions.SALE_PEPSI;

import org.junit.Test;

public class PurchaseTransactionPredicateTest {
    @Test
    public void test_isPurchaseTransaction_returnsTrue() {
        PurchaseTransactionPredicate predicate = new PurchaseTransactionPredicate();
        assertTrue(predicate.test(BUY_PEPSI));
    }

    @Test
    public void test_isNotPurchaseTransaction_returnsFalse() {
        PurchaseTransactionPredicate predicate = new PurchaseTransactionPredicate();
        assertFalse(predicate.test(SALE_PEPSI));
    }
}
