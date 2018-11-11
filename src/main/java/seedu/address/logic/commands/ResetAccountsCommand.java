package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AccountList;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;

/**
 * Resets the account list, to one account with "admin" as both username and password.
 * User is logged out as in model.
 */
public class ResetAccountsCommand extends Command {

    public static final String COMMAND_WORD = "resetAccounts";
    public static final String MESSAGE_SUCCESS = "Account database has been reset and user logged out";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        Username adminName = new Username("admin");
        Password adminPass = new Password("admin");
        Account admin = new Account(adminName, adminPass);

        model.setLoggedOutStatus();
        model.resetAccountData(new AccountList());
        model.addAccount(admin);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
