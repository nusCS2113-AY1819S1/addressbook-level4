package seedu.address.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class UsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Username(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null username
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid usernames
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only
        assertFalse(Username.isValidUsername("j")); // one character
        assertFalse(Username.isValidUsername("_j")); // begins with non-alphanumeric
        assertFalse(Username.isValidUsername("j_")); // ends with non-alphanumeric
        assertFalse(Username.isValidUsername("_j_")); // begins and ends with non-alphanumeric
        assertFalse(Username.isValidUsername("my name")); // has space

        // valid usernames
        assertTrue(Username.isValidUsername("james"));
        assertTrue(Username.isValidUsername("la")); // two characters
        assertTrue(Username.isValidUsername("my.name")); // with period
        assertTrue(Username.isValidUsername("my-name")); // with hyphen
        assertTrue(Username.isValidUsername("my_name")); // with underscore
    }
}
