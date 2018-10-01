package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;
import seedu.address.ui.LoginWindow;

/**
 * Queries the login book to see if there is a password that matches input password. Used for the login process.
 * Keyword matching is case sensitive.
 */
public class LoginUserPasswordCommand extends LoginCommand {

    private final UserPasswordContainsKeywordsPredicate predicate;

    public LoginUserPasswordCommand(UserPasswordContainsKeywordsPredicate predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredLoginDetailsList(predicate);
        if (model.getFilteredLoginDetailsList().size() != 0) {
            LoginWindow.isExistUserPassword = true;
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_LOGINDETAILS_LISTED_OVERVIEW, model.getFilteredLoginDetailsList().size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginUserPasswordCommand // instanceof handles nulls
                && predicate.equals(((LoginUserPasswordCommand) other).predicate)); // state check
    }
}
