package seedu.address.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class AccountCredentialTest {

    private static AccountCredential accountCredentialNoParam;
    private static AccountCredential accountCredentialParam;

    @BeforeClass
    public static void testFixtureSetup() {
        accountCredentialNoParam = new AccountCredential();
        accountCredentialParam = new AccountCredential("test1", "test1");
    }

    @Test
    public void getUserName_correctReturnedValue() {
        assertTrue(accountCredentialNoParam.getUserName().equals("test"));
        assertTrue(accountCredentialParam.getUserName().equals("test1"));
    }

    @Test
    public void getUserName_incorrectReturnedValue() {
        assertFalse(accountCredentialNoParam.getUserName().equals("test1"));
        assertFalse(accountCredentialParam.getUserName().equals("test"));
    }

    @Test
    public void passwordIsValid_correctPassword_correctBooleanReturned() {
        assertTrue(accountCredentialNoParam.passwordIsValid("test"));
        assertTrue(accountCredentialParam.passwordIsValid("test1"));
    }

    @Test
    public void passwordIsValid_incorrectPassword_correctBooleanReturned() {
        assertFalse(accountCredentialNoParam.passwordIsValid("test1"));
        assertFalse(accountCredentialParam.passwordIsValid("test"));
    }

}
