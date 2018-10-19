package seedu.address.logic;

/**
 * The manager of the login functionality of the app.
 */
public class LoginManager {

    private static boolean isSensitiveInformation = false;
    private static boolean isLoginSuccessful = false;
    private static boolean isMember = false;
    private static boolean isPresident = false;
    private static boolean isTreasurer = false;

    public LoginManager() {}

    public static boolean getIsSensitiveInformation() {
        return isSensitiveInformation;
    }

    public static boolean getIsLoginSuccessful() {
        return isLoginSuccessful;
    }

    public static boolean getIsMember() {
        return isMember;
    }

    public static boolean getIsPresident() {
        return isPresident;
    }

    public static boolean getIsTreasurer() {
        return isTreasurer;
    }

    public static void setIsSensitiveInformation(boolean setSensitiveInformation) {
        isSensitiveInformation = setSensitiveInformation;
    }

    public static void setIsLoginSuccessful(boolean setLoginSuccessful) {
        isLoginSuccessful = setLoginSuccessful;
    }

    public static void setIsMember(boolean setMember) {
        isMember = setMember;
    }

    public static void setIsPresident(boolean setPresident) {
        isPresident = setPresident;
    }

    public static void setIsTreasurer(boolean setTreasurer) {
        isTreasurer = setTreasurer;
    }

    public static void setAllRolesFalse() {
        isMember = false;
        isPresident = false;
        isTreasurer = false;
    }
}
