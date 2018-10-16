package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.UserLoginException;
import seedu.address.model.Model;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;
import seedu.address.ui.MainWindow;

/**
 * Queries the login book to see if there is a user ID and password that matches input user ID and password.
 * Used for the login process.
 * Keyword matching is case insensitive for user ID but case sensitive for password.
 */
public class LoginUserIdPasswordCommand extends LoginCommand {

    private final UserIdContainsKeywordsPredicate idPredicate;
    private final UserPasswordContainsKeywordsPredicate passwordPredicate;

    public LoginUserIdPasswordCommand(UserIdContainsKeywordsPredicate idPredicate,
                                      UserPasswordContainsKeywordsPredicate passwordPredicate) {
        super();
        this.idPredicate = idPredicate;
        this.passwordPredicate = passwordPredicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (model.getFilteredLoginDetailsList().size() == 0) { // when user tries to login again after failed first try
            model.updateFilteredLoginDetailsList(PREDICATE_SHOW_ALL_ACCOUNTS); // resets account list to show all
        }
        Predicate updatedIdPredicate = getMostUpdatedIdPredicate(idPredicate);
        model.updateFilteredLoginDetailsList(updatedIdPredicate);
        Predicate updatedPasswordPredicate = getMostUpdatedPasswordPredicate(passwordPredicate);
        model.updateFilteredLoginDetailsList(updatedPasswordPredicate);
        if (model.getFilteredLoginDetailsList().size() != 0) {
            MainWindow.setIsLoginSuccessful(true);
        } else {
            MainWindow.setIsLoginSuccessful(false);
            UserLoginException userLoginException = new UserLoginException();
            userLoginException.showLoginError();
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_LOGIN_LISTED_OVERVIEW, model.getFilteredLoginDetailsList().size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginUserIdPasswordCommand // instanceof handles nulls
                && idPredicate.equals(((LoginUserIdPasswordCommand) other).idPredicate))
                && passwordPredicate.equals(((LoginUserIdPasswordCommand) other).passwordPredicate); // state check
    }
}
