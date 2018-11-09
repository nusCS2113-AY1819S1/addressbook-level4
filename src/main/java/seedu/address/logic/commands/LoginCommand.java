package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.account.UsernameMatchPredicate;

/**
 * Logs in account into Stock List.
 */

public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in account into Stock List. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "admin "
            + PREFIX_PASSWORD + "admin";

    public static final String MESSAGE_SUCCESS = "Logged in: %1$s";
    public static final String MESSAGE_FAIL = "Wrong username/password. Try again";
    public static final String MESSAGE_ALREADY_LOGGED_IN = "Already logged in as: %1$s";

    private final UsernameMatchPredicate predicate;
    private final String password;

    public LoginCommand(UsernameMatchPredicate predicate, Account account) {
        this.predicate = predicate;
        this.password = account.getPassword().toString();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.getLoginStatus()) {
            throw new CommandException(String.format(MESSAGE_ALREADY_LOGGED_IN, model.getLoggedInUser()));
        }
        requireNonNull(model);
        model.updateFilteredAccountList(predicate);
        List<Account> matchedAccounts = model.getFilteredAccountList();
        modifyLoginStatus(matchedAccounts, password, model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_FAIL);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getLoggedInUser()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && predicate.equals(((LoginCommand) other).predicate)); // state check
    }

    /**
     * sets logged in user in the model if passwords match
     */
    private void modifyLoginStatus (List<Account> matchedAccounts, String givenPassword, Model model) {
        for (Account account : matchedAccounts) {
            if (account.getPassword().toString().equals(givenPassword)) {
                model.setLoggedInUser(account.getUsername());
            }
        }
    }

}
