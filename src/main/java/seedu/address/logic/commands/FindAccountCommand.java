package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
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
        return new CommandResult(
                String.format(Messages.MESSAGE_ACCOUNTS_LISTED_OVERVIEW, model.getFilteredAccountList().size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindAccountCommand // instanceof handles nulls
                && predicate.equals(((FindAccountCommand) other).predicate)); // state check
    }
}
