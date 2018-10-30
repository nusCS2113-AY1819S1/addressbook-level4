package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.user.UserName;
import seedu.address.testutil.Assert;

public class UserNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UserName(null));
    }

    @Test
    public void constructor_invalidUserName_throwsIllegalArgumentException() {
        String invalidUserName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new UserName(invalidUserName));
    }

    @Test
    public void isValidUserName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> UserName.isValidUserName(null));

        // invalid userName
        assertFalse(UserName.isValidUserName("")); // empty string
        assertFalse(UserName.isValidUserName(" ")); // spaces only
        assertFalse(UserName.isValidUserName("^")); // only non-alphanumeric characters
        assertFalse(UserName.isValidUserName("peter*")); // contains non-alphanumeric characters
        assertFalse(UserName.isValidUserName("tester tester")); //contain space

        // valid userName
        assertTrue(UserName.isValidUserName("tester")); // alphabets only
        assertTrue(UserName.isValidUserName("tester123")); // numbers and alphabets
    }

    @Test
    public void isUserNameTooLong() {
        Assert.assertThrows(NullPointerException.class, () -> UserName.isUserNameTooLong(null));
        //invalid userName
        assertTrue(UserName.isUserNameTooLong
                ("sjkflasdhfjklashfjklashfjklashfjklashfjklsdhafjklhasdkflhaslfjkhaslkfsdfggdfsgs"));
        // longer than 30 words

        //valid userName
        assertFalse(UserName.isUserNameTooLong("tester123tester123tester123")); //long name that is less than 30 char


    }

}
