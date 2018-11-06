package seedu.address.model.transaction;

import static seedu.address.testutil.transaction.TypicalTransactions.BUY_PEPSI;
import static seedu.address.testutil.transaction.TypicalTransactions.SALE_COKE_1;

import org.junit.Assert;
import org.junit.Test;

public class TransactionTypeTest {
    private TransactionType saleType = TransactionType.SALE;
    private TransactionType importType = TransactionType.PURCHASE;

    // TODO: decide whether these are necessary
    @Test
    public void constructor_sale_returnSale() {
        Assert.assertEquals(saleType, TransactionType.valueOf("SALE"));
    }

    @Test
    public void constructor_purchaseLowerCase_returnPurchase() {
        Assert.assertEquals(importType, TransactionType.valueOf("purchase".toUpperCase()));
    }

    @Test
    public void toString_print_printCorrectFormat() {
        Assert.assertEquals("SALE", SALE_COKE_1.getTransactionType().toString());
        Assert.assertEquals("PURCHASE", BUY_PEPSI.getTransactionType().toString());
    }
}
