package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all accounts in the account list to the user.
 */
public class ListAccountsCommand extends Command {

    public static final String COMMAND_WORD = "listAccounts";

    public static final String MESSAGE_SUCCESS = "Listed all accounts";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
