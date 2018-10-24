package seedu.address.model.product;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SerialNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new SerialNumber(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new SerialNumber(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> SerialNumber.isValidPhone(null));

        // invalid phone numbers
        assertFalse(SerialNumber.isValidPhone("")); // empty string
        assertFalse(SerialNumber.isValidPhone(" ")); // spaces only
        assertFalse(SerialNumber.isValidPhone("91")); // less than 3 numbers
        assertFalse(SerialNumber.isValidPhone("phone")); // non-numeric
        assertFalse(SerialNumber.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(SerialNumber.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(SerialNumber.isValidPhone("911")); // exactly 3 numbers
        assertTrue(SerialNumber.isValidPhone("93121534"));
        assertTrue(SerialNumber.isValidPhone("124293842033123")); // long phone numbers
    }
}
