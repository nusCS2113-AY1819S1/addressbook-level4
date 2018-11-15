package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_LOGIN_LISTED_OVERVIEW;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.LoginManager;
import seedu.address.model.Model;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;
import seedu.address.model.login.UserRoleContainsKeywordsPredicate;

/**
 * Queries the login book to see if there is a user ID and password that matches input user ID and password.
 * Used for the login process.
 * Keyword matching is case insensitive for user ID but case sensitive for password.
 */
public class LoginUserIdPasswordRoleCommand extends LoginCommand {

    private static final Logger logger = LogsCenter.getLogger(LoginUserIdPasswordRoleCommand.class);

    private final UserIdContainsKeywordsPredicate idPredicate;
    private final UserPasswordContainsKeywordsPredicate passwordPredicate;
    private final UserRoleContainsKeywordsPredicate rolePredicate;

    public LoginUserIdPasswordRoleCommand(UserIdContainsKeywordsPredicate idPredicate,
                                          UserPasswordContainsKeywordsPredicate passwordPredicate,
                                          UserRoleContainsKeywordsPredicate rolePredicate) {
        super();
        if (isNull(idPredicate)) {
            logger.log(Level.WARNING, "idPredicate is null!");
        }
        if (isNull(passwordPredicate)) {
            logger.log(Level.WARNING, "passwordPredicate is null!");
        }
        if (isNull(rolePredicate)) {
            logger.log(Level.WARNING, "rolePredicate is null!");
        }
        requireNonNull(idPredicate);
        requireNonNull(passwordPredicate);
        requireNonNull(rolePredicate);
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
     * credentials, and sets the login condition as successful only if the input user credentials is correct.
     * @param model the current model being used to filter the accounts list
     */
    private void checkUpdatedAccountListSetLoginCondition(Model model) {
        if (model.getFilteredLoginDetailsList().size() != 0) {
            LoginManager.setIsLoginSuccessful(true);
            logger.log(Level.INFO, "Login successful");
            assert LoginManager.getIsLoginSuccessful() : "LoginManager.getIsLoginSuccessful() should be true";
        } else {
            LoginManager.setAllRolesFalse();
            assert !LoginManager.getIsPresident() : "LoginManager.getIsPresident() should be false";
            assert !LoginManager.getIsTreasurer() : "LoginManager.getIsTreasurer() should be false";
            assert !LoginManager.getIsMember() : "LoginManager.getIsMember() should be false";
            LoginManager.setIsLoginSuccessful(false);
            logger.log(Level.INFO, "Login failed");
            assert !LoginManager.getIsLoginSuccessful() : "LoginManager.getIsLoginSuccessful() should be false";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LoginUserIdPasswordRoleCommand // instanceof handles nulls
            && getIdPredicate().equals(((LoginUserIdPasswordRoleCommand) other).getIdPredicate()))
            && getPasswordPredicate().equals(((LoginUserIdPasswordRoleCommand) other).getPasswordPredicate())
            && getRolePredicate().equals(((LoginUserIdPasswordRoleCommand) other).getRolePredicate()); // state check
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
