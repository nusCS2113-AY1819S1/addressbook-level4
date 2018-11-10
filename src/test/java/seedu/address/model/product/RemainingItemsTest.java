package seedu.address.model.product;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;
//@@author Gara
public class RemainingItemsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new RemainingItems(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new RemainingItems(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> SerialNumber.isValidPhone(null));

        // invalid phone numbers
        assertFalse(RemainingItems.isValidRemainingItems("")); // empty string
        assertFalse(RemainingItems.isValidRemainingItems(" ")); // spaces only
        assertFalse(RemainingItems.isValidRemainingItems("phone")); // non-numeric
        assertFalse(RemainingItems.isValidRemainingItems("9011p041")); // alphabets within digits
        assertFalse(RemainingItems.isValidRemainingItems("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(RemainingItems.isValidRemainingItems("93121534"));
        assertTrue(RemainingItems.isValidRemainingItems("124293842033123")); // long phone numbers
    }
}
