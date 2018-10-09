package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Quantity.isValidAddress(null));

        // invalid addresses
        assertFalse(Quantity.isValidAddress("")); // empty string
        assertFalse(Quantity.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Quantity.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Quantity.isValidAddress("-")); // one character
        assertTrue(Quantity.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
