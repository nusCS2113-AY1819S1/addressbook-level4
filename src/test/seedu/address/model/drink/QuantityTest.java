package seedu.address.model.drink;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class QuantityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity (null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null quantity
        Assert.assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // invalid quantities
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("-1")); // "negative" quantity, with minus sign
        assertFalse(Quantity.isValidQuantity("bla")); // non-numeric
        assertFalse(Quantity.isValidQuantity("2948j3d")); // alphabets within digits
        assertFalse(Quantity.isValidQuantity("234 323")); // spaces within digits
        assertFalse(Quantity.isValidQuantity("2147483648")); // 2^31, integer overflow

        // valid phone numbers
        assertTrue(Quantity.isValidQuantity("911")); // only numbers
        assertTrue(Quantity.isValidQuantity("93121534"));
        assertTrue(Quantity.isValidQuantity("2147483647")); // large quantities, 2^31 - 1
    }
}
