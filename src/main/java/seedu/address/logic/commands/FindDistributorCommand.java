package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.distributor.DNameContainsKeywordsPredicate;

/**
 * Finds and lists all distributor in distirubtor book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindDistributorCommand extends Command {

    public static final String COMMAND_WORD = "finddistributor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all distributors whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final DNameContainsKeywordsPredicate predicate;

    public FindDistributorCommand(DNameContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindDistributorCommand // instanceof handles nulls
                && predicate.equals(((FindDistributorCommand) other).predicate)); // state check
    }
}

