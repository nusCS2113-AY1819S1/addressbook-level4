package seedu.address.commons.core;

/**
 * Current user info used by the app
 */
public class CurrentUser {

    private static String userName;
    private static String authenticationLevel;

    public static String getUserName () {
        return userName;
    }

    public static String getAuthenticationLevel () {
        return authenticationLevel;
    }

    public static LoginInfo getCurrentUser () {
        LoginInfo currentUser = new LoginInfo (userName, authenticationLevel);
        return currentUser;
    }

    public static void setLoginInfo (String userName, String authenticationLevel) {
        CurrentUser.userName = userName;
        CurrentUser.authenticationLevel = authenticationLevel;
    }

}
