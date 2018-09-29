package seedu.address.ui;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.logic.commands.LoginUserIdCommand;
import seedu.address.logic.commands.LoginUserPasswordCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LoginBook;
import seedu.address.model.Model;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPassword;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;
import seedu.address.storage.XmlLoginBookStorage;

/**
 * The login window. Provides the basic application layout containing
 * a simple text box for providing additional commands, a text field for
 * providing user ID, and a text field for providing password.
 */
public class LoginWindow {

    private static boolean isLoginSuccessful = false;
    private static boolean isValidUserId = false;
    private static boolean isValidUserPassword = false;
    public static boolean isSensitiveInformation;

    private static Path loginBookFilePath = Paths.get("data" , "loginbook.xml");

    /**
     * Kick starts the log in process with pop-up login windows.
     * @param model
     * @param history
     */
    static void initializeLoginProcess(Model model, CommandHistory history) throws CommandException, IOException {

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
                LoginDialogBoxUserIdPassword loginUserIdPassword = new LoginDialogBoxUserIdPassword();
                loginUserIdPassword.loginDialogBoxUserIdPassword();

                UserId loginId = new UserId(loginUserIdPassword.getUserId());
                UserPassword loginPassword = new UserPassword(loginUserIdPassword.getUserPassword());
                LoginDetails loginDetails = new LoginDetails(loginId, loginPassword);

                UserIdContainsKeywordsPredicate matchId = new UserIdContainsKeywordsPredicate(loginUserIdPassword.getUserId());
                LoginUserIdCommand loginUserIdCommand = new LoginUserIdCommand(matchId);
                loginUserIdCommand.execute(model, history);
                if (loginUserIdCommand.isUserIdExists(model)) {
                    isValidUserId = true;
                }

                UserPasswordContainsKeywordsPredicate matchPassword = new UserPasswordContainsKeywordsPredicate(loginUserIdPassword.getUserPassword());
                LoginUserPasswordCommand loginUserPasswordCommand = new LoginUserPasswordCommand(matchPassword);
                loginUserPasswordCommand.execute(model, history);
                if (loginUserPasswordCommand.isUserPasswordExists(model)) {
                    isValidUserPassword = true;
                }
                if (isValidUserId && isValidUserPassword) {
                    isLoginSuccessful = true;
                }
                isSensitiveInformation = false;
                break;
            case "create account":
                isSensitiveInformation = true;
                LoginDialogBoxUserIdPassword createUserIdPassword = new LoginDialogBoxUserIdPassword();
                createUserIdPassword.loginDialogBoxUserIdPassword();

                UserId createId = new UserId(createUserIdPassword.getUserId());
                UserPassword createPassword = new UserPassword(createUserIdPassword.getUserPassword());
                LoginDetails createDetails = new LoginDetails(createId, createPassword);
                CreateAccountCommand createAccount = new CreateAccountCommand(createDetails);

                XmlLoginBookStorage xmlLoginBookStorageSave = new XmlLoginBookStorage(loginBookFilePath);
                LoginBook saveLoginBook = new LoginBook();
                xmlLoginBookStorageSave.saveLoginBook(saveLoginBook, loginBookFilePath) ;
                createAccount.execute(model, history);
                isSensitiveInformation = false;
                break;
            case "delete account":
                isSensitiveInformation = true;

                isSensitiveInformation = false;
                break;
            case "change password":
                isSensitiveInformation = true;

                isSensitiveInformation = false;
                break;
            default:
                throw new IllegalArgumentException("Invalid command!" + loginSelection);
        }
    }

    static boolean getIsLoginSuccessful() {
        return isLoginSuccessful;
    }
}
