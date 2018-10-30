package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.user.Password;
import seedu.address.testutil.Assert;


public class PasswordTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password (null));
    }
    @Test
    public void constructor_invalidUserName_throwsIllegalArgumentException() {
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password (invalidPassword));
    }
    @Test
    public void isValidPassword() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword (null));

        // invalid userName
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("password password")); //contain space

        // valid userName
        assertTrue(Password.isValidPassword("myPassword")); // alphabets only
        assertTrue(Password.isValidPassword("myPassword123")); // numbers and alphabets
        assertTrue (Password.isValidPassword ("myPassword123^*(")); //with non-alphanumeric characters
        assertTrue (Password.isValidPassword ("Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0"));
    }

    @Test
    public void isUserNameTooLong() {
        Assert.assertThrows(NullPointerException.class, () -> Password.isPasswordTooLong (null));
        //invalid userName
        assertTrue (Password.isPasswordTooLong
                ("sjkflasdhfjklashfjklashfjklashfjklashfjklsdhafjklhasdkflhaslfjkhaslkfsdfggdfsgs"));
        // longer than 30 words

        //valid userName
        assertFalse (Password.isPasswordTooLong("Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0"));
        //long hashed that is less than 50 char
        assertFalse (Password.isPasswordTooLong("tester123tester123tester123"));
        //long password that is less than 50 char

    }
}
