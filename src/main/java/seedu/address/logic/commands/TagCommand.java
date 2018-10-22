package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.item.TagContainsKeywordsPredicate;

/**
 * Find and show items under a specific tag
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_SUCCESS = "Listed all items for your tag.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all items whose tag matches "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]..."
            + " Example: " + COMMAND_WORD + " Lab1";

    private final TagContainsKeywordsPredicate predicate;

    public TagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredItemList(predicate);
        return new CommandResult(
             String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredItemList().size()));
    }
}
