package seedu.address.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The login window. Provides the basic application layout containing
 * a simple text box for providing additional commands, a text field for
 * providing user ID, and a text field for providing password.
 */
public class LoginWindow {

    private static boolean isLoginSuccessful = false;
    private static boolean isUserIdExists = false;
    public static boolean isSensitiveInformation;

    public static boolean getIsLoginSuccessful() {
        return isLoginSuccessful;
    }

    /**
     * Kick starts the log in process with pop-up login windows.
     */
    public static void initializeLoginProcess() {

        //@@author Chocological-reused
        //Reused from https://stackoverflow.com/posts/8853170/revisions with minor modifications
        final JFrame initialLoginOptions = new JFrame();
        JButton initialLoginButton = new JButton();

        initialLoginOptions.add(initialLoginButton);
        initialLoginOptions.pack();
        initialLoginOptions.setVisible(true);

        String loginSelection = JOptionPane.showInputDialog(initialLoginOptions,
                "Please type in command to either login normally, create new account, " +
                        "delete account or change account password", null);

        switch (loginSelection) {
            case "login":
                isSensitiveInformation = true;

                break;
            case "create account":
                isSensitiveInformation = true;

                break;
            case "delete account":
                isSensitiveInformation = true;

                break;
            case "change password":
                isSensitiveInformation = true;

                break;
            default:
                throw new IllegalArgumentException("Invalid command!" + loginSelection);
        }
        //@@author

        //@@author Chocological-reused
        //Reused from https://stackoverflow.com/posts/8853170/revisions with minor modifications
        final JFrame userIdentity = new JFrame();
        final JFrame userPassword = new JFrame();
        JButton button = new JButton();

        userIdentity.add(button);
        userIdentity.pack();
        userIdentity.setVisible(true);

        userPassword.add(button);
        userPassword.pack();
        userPassword.setVisible(true);
        //@@author


        String userId = JOptionPane.showInputDialog(userIdentity,
                "Please enter student matriculation ID as user ID:", null);

        //@@author Chocological-reused
        //Reused from https://stackoverflow.com/posts/8853170/revisions with minor modifications
        if (!isUserIdExists) {
            final JFrame wrongUserId = new JFrame();
            wrongUserId.add(button);
            wrongUserId.pack();
            wrongUserId.setVisible(true);
            JOptionPane.showMessageDialog(null, "User ID does not exist!");
            System.exit(0);
        }

        String password = JOptionPane.showInputDialog(userPassword,
                "Enter your password:", null);
        //@@author

        //@@author Chocological-reused
        //Reused from https://stackoverflow.com/posts/8853170/revisions with minor modifications
        if (!isLoginSuccessful) {
            final JFrame wrongPassword = new JFrame();
            wrongPassword.add(button);
            wrongPassword.pack();
            wrongPassword.setVisible(true);
            JOptionPane.showMessageDialog(null, "Wrong password!");
            System.exit(0);
        }
        //@@author
    }

    public static void main(String[] args) {
        initializeLoginProcess();
    }
}
