package seedu.address.model.transaction;

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

    @Test
    public void toString_print_printCorrectFormat() {
        Assert.assertEquals("Sale, Import", saleType.toString());
    }
}
