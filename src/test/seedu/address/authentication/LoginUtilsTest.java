//@@author liu-tianhang
package seedu.address.authentication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.LoginInfoManager;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;


public class LoginUtilsTest {
    private LoginUtils loginUtilsUserNameEmpty;
    private LoginUtils loginUtilsUserPasswordEmpty;
    private LoginUtils validUser;
    @Before
    public void typicalAccount() {
        UserName correctUserName = new UserName ("tester");
        UserName wrongUserName = new UserName ("tester2");
        Password correctPassword = new Password("123");
        Password wrongPassword = new Password ("1234");
        loginUtilsUserNameEmpty = new LoginUtils (wrongUserName, correctPassword , new LoginInfoManager ());
        loginUtilsUserPasswordEmpty = new LoginUtils (correctUserName, wrongPassword , new LoginInfoManager ());
        validUser = new LoginUtils (correctUserName, correctPassword , new LoginInfoManager ());
    }

    @Test
    public void isPasswordAndUserNameValidTest() {
        assertFalse (loginUtilsUserNameEmpty.isPasswordAndUserNameValid ());
        assertFalse (loginUtilsUserPasswordEmpty.isPasswordAndUserNameValid ());
        assertTrue (validUser.isPasswordAndUserNameValid ());
    }

}
