package seedu.address.model.transaction;

import static seedu.address.model.transaction.testutil.TypicalTransactions.IMPORT_PEPSI;
import static seedu.address.model.transaction.testutil.TypicalTransactions.SALE_COKE_1;

import org.junit.Assert;
import org.junit.Test;

public class TransactionTypeTest {
    private TransactionType saleType = TransactionType.SALE;
    private TransactionType importType = TransactionType.IMPORT;

    @Test
    public void constructor_sale_returnSale() {
        Assert.assertEquals(saleType, TransactionType.valueOf("SALE"));
    }

    @Test
    public void constructor_importLowerCase_returnImport() {
        Assert.assertEquals(importType, TransactionType.valueOf("import".toUpperCase()));
    }
g
    @Test
    public void toString_print_printCorrectFormat() {
        Assert.assertEquals("SALE", SALE_COKE_1.getTransactionType().toString());
        Assert.assertEquals("IMPORT", IMPORT_PEPSI.getTransactionType().toString());
    }
}
