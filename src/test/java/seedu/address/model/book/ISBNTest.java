package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ISBNTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ISBN(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ISBN(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> ISBN.isValidPhone(null));

        // invalid phone numbers
        assertFalse(ISBN.isValidPhone("")); // empty string
        assertFalse(ISBN.isValidPhone(" ")); // spaces only
        assertFalse(ISBN.isValidPhone("91")); // less than 3 numbers
        assertFalse(ISBN.isValidPhone("phone")); // non-numeric
        assertFalse(ISBN.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(ISBN.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(ISBN.isValidPhone("911")); // exactly 3 numbers
        assertTrue(ISBN.isValidPhone("93121534"));
        assertTrue(ISBN.isValidPhone("124293842033123")); // long phone numbers
    }
}
