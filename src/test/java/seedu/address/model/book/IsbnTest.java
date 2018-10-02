package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IsbnTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Isbn(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Isbn(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Isbn.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Isbn.isValidPhone("")); // empty string
        assertFalse(Isbn.isValidPhone(" ")); // spaces only
        assertFalse(Isbn.isValidPhone("91")); // less than 3 numbers
        assertFalse(Isbn.isValidPhone("phone")); // non-numeric
        assertFalse(Isbn.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Isbn.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Isbn.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Isbn.isValidPhone("93121534"));
        assertTrue(Isbn.isValidPhone("124293842033123")); // long phone numbers
    }
}
