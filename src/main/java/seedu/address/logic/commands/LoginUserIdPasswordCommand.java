package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_LOGIN_LISTED_OVERVIEW;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.UserLoginException;
import seedu.address.model.Model;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;
import seedu.address.model.login.UserRoleContainsKeywordsPredicate;
import seedu.address.ui.MainWindow;

/**
 * Queries the login book to see if there is a user ID and password that matches input user ID and password.
 * Used for the login process.
 * Keyword matching is case insensitive for user ID but case sensitive for password.
 */
public class LoginUserIdPasswordCommand extends LoginCommand {

    private final UserIdContainsKeywordsPredicate idPredicate;
    private final UserPasswordContainsKeywordsPredicate passwordPredicate;
    private final UserRoleContainsKeywordsPredicate rolePredicate;

    public LoginUserIdPasswordCommand(UserIdContainsKeywordsPredicate idPredicate,
                                      UserPasswordContainsKeywordsPredicate passwordPredicate,
                                      UserRoleContainsKeywordsPredicate rolePredicate) {
        super();
        this.idPredicate = idPredicate;
        this.passwordPredicate = passwordPredicate;
        this.rolePredicate = rolePredicate;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        resetAccountList(model);
        updateFilteredAccountList(model);
        checkUpdatedAccountListSetLoginCondition(model);
        return new CommandResult(MESSAGE_LOGIN_LISTED_OVERVIEW);
    }

    /**
     * Resets the previously updated accounts list in the model to a fresh state containing all existing accounts.
     * @param model the current model being used to filter the accounts list
     */
    private void resetAccountList(Model model) {
        if (model.getFilteredLoginDetailsList().size() == 0) { // when user tries to login again after failed first try
            model.updateFilteredLoginDetailsList(PREDICATE_SHOW_ALL_ACCOUNTS); // resets account list to show all
        }
    }

    /**
     * Updates the account list in the model to reflect input of user login credentials.
     * @param model the current model being used to filter the accounts list
     */
    private void updateFilteredAccountList(Model model) {
        Predicate updatedIdPredicate = getMostUpdatedIdPredicate(getIdPredicate());
        model.updateFilteredLoginDetailsList(updatedIdPredicate);
        Predicate updatedPasswordPredicate = getMostUpdatedPasswordPredicate(getPasswordPredicate());
        model.updateFilteredLoginDetailsList(updatedPasswordPredicate);
        Predicate updatedRolePredicate = getMostUpdatedRolePredicate(getRolePredicate());
        model.updateFilteredLoginDetailsList(updatedRolePredicate);
    }


    /**
     * Checks if there is an existing account in the account list in the model that matches input of user login
     * credentials, and sets the login condition as successful or unsuccessful accordingly.
     * @param model the current model being used to filter the accounts list
     */
    private void checkUpdatedAccountListSetLoginCondition(Model model) {
        if (model.getFilteredLoginDetailsList().size() != 0) {
            MainWindow.setIsLoginSuccessful(true);
        } else {
            UserLoginException userLoginException = new UserLoginException();
            userLoginException.showLoginError();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LoginUserIdPasswordCommand // instanceof handles nulls
            && getIdPredicate().equals(((LoginUserIdPasswordCommand) other).getIdPredicate()))
            && getPasswordPredicate().equals(((LoginUserIdPasswordCommand) other).getPasswordPredicate())
            && getRolePredicate().equals(((LoginUserIdPasswordCommand) other).getRolePredicate()); // state check
    }

    public UserIdContainsKeywordsPredicate getIdPredicate() {
        return idPredicate;
    }

    public UserPasswordContainsKeywordsPredicate getPasswordPredicate() {
        return passwordPredicate;
    }

    public UserRoleContainsKeywordsPredicate getRolePredicate() {
        return rolePredicate;
    }
}
