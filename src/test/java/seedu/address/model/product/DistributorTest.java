package seedu.address.model.product;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DistributorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidDistributor() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // invalid name
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only
        assertFalse(Email.isValidEmail("^")); // only non-alphanumeric characters
        assertFalse(Email.isValidEmail("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Email.isValidEmail("peter jack")); // alphabets only
        assertTrue(Email.isValidEmail("12345")); // numbers only
        assertTrue(Email.isValidEmail("peter the 2nd")); // alphanumeric characters
        assertTrue(Email.isValidEmail("Capital Tan")); // with capital letters
        assertTrue(Email.isValidEmail("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
