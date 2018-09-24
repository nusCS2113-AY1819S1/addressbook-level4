package seedu.address.ui;

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
    public static boolean isSensitiveInformation;

    public static boolean getIsLoginSuccessful() {
        return isLoginSuccessful;
    }

    private static CreateAccountCommand createAccount = null;

    /**
     * Kick starts the log in process with pop-up login windows.
     * @param model
     * @param history
     */
    static void initializeLoginProcess(Model model, CommandHistory history) throws CommandException {

        //@@author Chocological-reused
        //Reused from https://stackoverflow.com/posts/6555051/revisions with minor modifications
        LoginDialogBox.setLoginDialogBox();

        String loginSelection = JOptionPane.showInputDialog(LoginDialogBox.getLoginFrame(),
                "Please type in command to either login normally, create new account, " +
                        "delete account or change account password", null);
        //@@author

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
    }

}
