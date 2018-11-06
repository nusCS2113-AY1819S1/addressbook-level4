//@@author liu-tianhang
package seedu.address.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class AuthenticationLevelTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AuthenticationLevel (null));
    }

    @Test
    public void constructor_invalidUserName_throwsIllegalArgumentException() {
        String invalidAuthenticationLevel = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new AuthenticationLevel(invalidAuthenticationLevel));
    }
    @Test
    public void isValidUserName() {
        // null AuthenticationLevel
        Assert.assertThrows(NullPointerException.class, () -> AuthenticationLevel.isAuthenticationLevelValid (null));

        // invalid AuthenticationLevel
        assertFalse(AuthenticationLevel.isAuthenticationLevelValid("")); // empty string
        assertFalse(AuthenticationLevel.isAuthenticationLevelValid(" ")); // spaces only
        assertFalse(AuthenticationLevel.isAuthenticationLevelValid("^")); // only non-alphanumeric characters
        assertFalse(AuthenticationLevel.isAuthenticationLevelValid("accountant"));
        // invalide name as it require all caps

        // valid AuthenticationLevel
        assertTrue(AuthenticationLevel.isAuthenticationLevelValid("ADMIN")); // for admin AuthenticationLevel
        assertTrue(AuthenticationLevel.isAuthenticationLevelValid("MANAGER")); // for manager AuthenticationLevel
        assertTrue(AuthenticationLevel.isAuthenticationLevelValid("STOCKTAKER")); // for stock taker AuthenticationLevel
        assertTrue(AuthenticationLevel.isAuthenticationLevelValid("ACCOUNTANT")); // for accountant AuthenticationLevel
    }
}
