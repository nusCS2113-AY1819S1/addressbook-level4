package seedu.address.model.timeidentifiedclass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.TreeMap;

import org.junit.Test;

import seedu.address.model.timeidentifiedclass.exceptions.ClosedTransactionException;
import seedu.address.testutil.Assert;

public class TransactionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // null transaction time
        Assert.assertThrows(NullPointerException.class, () -> new Transaction(null, new TreeMap<>()));
        // null transaction record
        Assert.assertThrows(NullPointerException.class, () -> new Transaction("time",
                null));
    }

    @Test
    public void isValidTransactionTime_invalidTimes() {
        // null transaction time
        Assert.assertThrows(NullPointerException.class, () -> Transaction.isValidTransactionTime(null));

        // empty transaction time
        assertFalse(Transaction.isValidTransactionTime(""));
        // invalid time
        assertFalse(Transaction.isValidTransactionTime("invalid time"));
        // no date
        assertFalse(Transaction.isValidTransactionTime("12:00:00"));
        // no time
        assertFalse(Transaction.isValidTransactionTime("2018/10/12"));
        // extra space (improper format)
        assertFalse(Transaction.isValidTransactionTime("2018/10/12  12:00:00"));
        // invalid months
        assertFalse(Transaction.isValidTransactionTime("2018/00/12 12:00:00"));
        assertFalse(Transaction.isValidTransactionTime("2018/13/12 12:00:00"));
        // invalid days
        assertFalse(Transaction.isValidTransactionTime("2018/01/00 12:00:00"));
        assertFalse(Transaction.isValidTransactionTime("2018/01/32 12:00:00"));
        // invalid hour
        assertFalse(Transaction.isValidTransactionTime("2018/01/20 24:00:00"));
        // invalid minute
        assertFalse(Transaction.isValidTransactionTime("2018/01/20 23:60:00"));
        // invalid second
        assertFalse(Transaction.isValidTransactionTime("2018/01/20 12:12:60"));

    }

    @Test
    public void isValidTransactionTime_validTimes() {
        assertTrue(Transaction.isValidTransactionTime("2018/12/31 23:59:59"));
        assertTrue(Transaction.isValidTransactionTime("2018/12/31 00:00:00"));
    }

    @Test
    public void addProduct_invalidProductName() {
        Transaction toTest = new Transaction();
        // null product name
        Assert.assertThrows(NullPointerException.class, () -> toTest.addProduct(null));
        Assert.assertThrows(NullPointerException.class, () -> toTest.addProduct(null, 2));
    }

    @Test
    public void addProduct_throwsClosedTransactionException() {
        Transaction toTest = new Transaction();
        toTest.closeTransaction();

        Assert.assertThrows(ClosedTransactionException.class, () -> toTest.addProduct("Apple"));
    }

    @Test
    public void addProduct_noExceptionsThrown() {
        Transaction toTest = new Transaction();
        try {
            toTest.addProduct("Apple");
        } catch (Exception e) {
            fail("Product should be added without exception");
        }
    }
}
