package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.product.TagContainsKeywordsPredicate;

/**
 * Finds and lists all products in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindTagProductCommand extends Command {

    public static final String COMMAND_WORD = "findtagproduct";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all products whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " fruits meat";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagProductCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredProductList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW,
                        model.getFilteredProductList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagProductCommand // instanceof handles nulls
                && predicate.equals(((FindTagProductCommand) other).predicate)); // state check
    }
}

