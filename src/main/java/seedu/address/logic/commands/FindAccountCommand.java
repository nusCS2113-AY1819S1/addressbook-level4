package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.account.UsernameContainsKeywordsPredicate;

/**
 * Finds and lists all accounts in account list whose username contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindAccountCommand extends Command {

    public static final String COMMAND_WORD = "findAccount";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all accounts whose usernames contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " jalil kelvin";

    private final UsernameContainsKeywordsPredicate predicate;

    public FindAccountCommand(UsernameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.updateFilteredAccountList(predicate);
        List<Account> foundAccounts = model.getFilteredAccountList();
        String messageOutput = getMessageOutput(model.getFilteredAccountList().size(), foundAccounts);
        return new CommandResult(messageOutput);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindAccountCommand // instanceof handles nulls
                && predicate.equals(((FindAccountCommand) other).predicate)); // state check
    }

    private String getMessageOutput (int numAccounts, List<Account> accounts) {
        String messageOutput = "";
        messageOutput += String.format(Messages.MESSAGE_ACCOUNTS_FOUND_OVERVIEW, numAccounts) + "\n";

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
