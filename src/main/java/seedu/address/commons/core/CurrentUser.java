package seedu.address.commons.core;

public class CurrentUser {
    private static String userName;
    private static String authenticationLevel;
    public CurrentUser(String userName, String authenticationLevel){
        System.out.println (userName);
        this.userName = userName;
        this.authenticationLevel = authenticationLevel;
    }

    public static String getUserName () { return userName; }

    public static String getAuthenticationLevel () {
        return authenticationLevel;
    }

    public static LoginInfo getCurrentUser (){
        LoginInfo currentUser = new LoginInfo (userName, authenticationLevel);
        return currentUser;
    }
}
