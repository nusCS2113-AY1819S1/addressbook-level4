package seedu.address.commons.core;

import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.UserName;

/**
 * Current user info used by the app
 */
public class CurrentUser {

    private static UserName userName;
    private static AuthenticationLevel authenticationLevel;

    public static UserName getUserName () {
        return userName;
    }

    public static String getAuthenticationLevel () {
        return authenticationLevel.toString ();
    }

    /**
     * Return true if {@code test} is equal to the authenticationLevel
     */
    public static boolean checkAuthenticationLevel(String test) {
        if (authenticationLevel.toString ().equals (test)) {
            return true;
        }
        return false;
    }
    public static LoginInfo getCurrentUser () {
        LoginInfo currentUser = new LoginInfo (userName, authenticationLevel);
        return currentUser;
    }

    public static void setLoginInfo (UserName userName, AuthenticationLevel authenticationLevel) {
        CurrentUser.userName = userName;
        CurrentUser.authenticationLevel = authenticationLevel;
    }

}
