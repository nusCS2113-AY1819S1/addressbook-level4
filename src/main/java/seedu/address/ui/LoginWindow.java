package seedu.address.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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

    private static CreateAccountCommand createAccount;

    /**
     * Kick starts the log in process with pop-up login windows.
     * @param model
     * @param history
     */
    public static void initializeLoginProcess(Model model, CommandHistory history) throws CommandException {

        LoginDialogBox.setLoginDialogBox();

        String loginSelection = JOptionPane.showInputDialog(LoginDialogBox.getLoginFrame(),
                "Please type in command to either login normally, create new account, " +
                        "delete account or change account password", null);

        switch (loginSelection) {
            case "login":
                isSensitiveInformation = true;

                break;
            case "create account":
                isSensitiveInformation = true;
                createAccount.execute(model, history);
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

        final JFrame userIdentity = new JFrame();
        final JFrame userPassword = new JFrame();
        JButton button = new JButton();

        userIdentity.add(button);
        userIdentity.pack();
        userIdentity.setVisible(true);

        userPassword.add(button);
        userPassword.pack();
        userPassword.setVisible(true);

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

    public static void main(Model model, CommandHistory history) throws CommandException {
        initializeLoginProcess(model, history);
    }
}
