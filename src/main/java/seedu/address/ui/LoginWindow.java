package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

/**
 * The login window. Provides the basic application layout containing
 * a simple text box for providing additional commands, a text field for
 * providing user ID, and a text field for providing password.
 */
public class LoginWindow {

    private static boolean isLoginSuccessful = false;
    private static boolean isValidUserId = false;
    private static boolean isValidUserPassword = false;
    static boolean isNormalLogin = false;
    static boolean isCreateAccount = false;
    private static boolean isDeleteAccount = false;
    private static boolean isChangePassword = false;
    public static boolean isSensitiveInformation;

    private static Path loginBookFilePath = Paths.get("data" , "loginbook.xml");

    /**
     * Kick starts the log in process with pop-up login windows.
     */
    static void initializeLoginProcess() {

        //@@author Chocological-reused
        //Reused from https://stackoverflow.com/posts/6555051/revisions with minor modifications
        LoginDialogBox.setLoginDialogBox();

        String loginSelection = JOptionPane.showInputDialog(LoginDialogBox.getLoginFrame(),
                "Please type in command to either login normally, create new account, " +
                        "delete account or change account password", null);
        //@@author

        switch (loginSelection) {
            case "login":
                isNormalLogin = true;
                break;
            case "create account":
                isCreateAccount = true;
                break;
            case "delete account":

                break;
            case "change password":

                break;
            default:
                throw new IllegalArgumentException("Invalid command!" + loginSelection);
        }
    }

    static boolean getIsLoginSuccessful() {
        return isLoginSuccessful;
    }
}
