package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.distributor.DTagContainsKeywordsPredicate;

/**
 * Finds and lists all distributors in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindTagDistributorCommand extends Command {

    public static final String COMMAND_WORD = "findtagdistributor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all distributors whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " fruits meat";

    private final DTagContainsKeywordsPredicate predicate;

    public FindTagDistributorCommand(DTagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDistributorList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_DISTRIBUTORS_LISTED_OVERVIEW,
                        model.getFilteredDistributorList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagDistributorCommand // instanceof handles nulls
                && predicate.equals(((FindTagDistributorCommand) other).predicate)); // state check
    }
}

