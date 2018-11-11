package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.LoginInfo;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

public class LoginInfoManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LoginInfoManager loginInfoManager = new LoginInfoManager ();

    private UserName actualUserName = new UserName ("tester");
    private Password actualPassword = new Password ("Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0=");
    private AuthenticationLevel actualAuthenticationLevel = new AuthenticationLevel ("ADMIN");
    private LoginInfo actualLoginInfo = new LoginInfo (actualUserName, actualPassword, actualAuthenticationLevel);

    @Test
    public void getLoginInfoTest() {

        assertEquals (loginInfoManager.getLoginInfo (actualUserName).toString (), actualLoginInfo.toString ());
    }

    @Test
    public void isUserNameExistTest() {
        UserName nonExistUserName = new UserName ("DoNotExist");
        assertFalse (loginInfoManager.isUserNameExist (nonExistUserName));

        assertTrue (loginInfoManager.isUserNameExist (actualUserName));
    }

    @Test
    public void changePasswordTest() {

        Password newPassword = new Password ("UgoydIK6V3t3iMXySCrbeFKlcUc/u3ylo1gmElyQAmQ=");
        LoginInfo expectedLoginInfo = new LoginInfo (actualUserName, newPassword, actualAuthenticationLevel);

        loginInfoManager.changePassword (actualUserName, newPassword);
        assertEquals (loginInfoManager.getLoginInfo (actualUserName).toString (), expectedLoginInfo.toString ());
    }

    @Test
    public void deleteAccountTest() {
        loginInfoManager.deleteAccount (actualUserName);

        assertFalse (loginInfoManager.isUserNameExist (actualUserName));
    }

    @Test
    public void createNewAccount() {
        UserName newUserName = new UserName ("tester2");
        Password newPassword = new Password ("Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0=");
        AuthenticationLevel newAuthenticationLevel = new AuthenticationLevel ("ADMIN");
        LoginInfo newAccount = new LoginInfo (newUserName, newPassword, newAuthenticationLevel);

        loginInfoManager.createNewAccount (newAccount);

        assertTrue (loginInfoManager.isUserNameExist (newUserName));
    }
}
