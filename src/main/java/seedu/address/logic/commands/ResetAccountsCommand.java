package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AccountList;
import seedu.address.model.Model;

/**
 * Resets the account list, to one account with "admin" as both username and password.
 * NEEDS WORK - admin username and password upon reset
 */
public class ResetAccountsCommand extends Command {

    public static final String COMMAND_WORD = "resetAccounts";
    public static final String MESSAGE_SUCCESS = "Account database has been reset!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetAccountData(new AccountList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
