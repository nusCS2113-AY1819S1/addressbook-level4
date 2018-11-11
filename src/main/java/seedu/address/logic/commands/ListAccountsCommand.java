package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.account.Account;

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
        List<Account> listOfAccounts = model.getFilteredAccountList();
        String messageOutput = getMessageOutput(listOfAccounts);
        return new CommandResult(messageOutput);
    }

    private String getMessageOutput (List<Account> accounts) {
        String messageOutput = "";
        messageOutput += MESSAGE_SUCCESS + "\n";

        messageOutput += "Accounts: \n";
        int counter = 0;
        for (Account account : accounts) {
            counter++;
            messageOutput += counter + ". "
                    +
                    account.getUsername()
                    + "\n";
        }

        return messageOutput;
    }
}
