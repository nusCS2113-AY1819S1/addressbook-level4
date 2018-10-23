package seedu.address.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(invalidPassword));
    }

    @Test
    public void isValidUsername() {
        // null password
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid passwords
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("\"")); // contains parentheses
        assertFalse(Password.isValidPassword("james 123")); // contains space

        // valid passwords
        assertTrue(Password.isValidPassword("james123")); //alphanumeric
        assertTrue(Password.isValidPassword("!@#$'*+/=?`{|}~^.@-")); // special characters
        assertTrue(Password.isValidPassword("james123!@#$'*+/=?`{|}~^.@-")); // alphanumeric + special characters
    }
}