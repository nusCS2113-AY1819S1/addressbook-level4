package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.TagsContainsKeywords;

/**
 * Find and display the persons with the same tag
 * Allows one to find the friends of similar tags
 */

public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String COMMAND_WORD_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified tags (case-sensitive) and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + " friends";

    private final TagsContainsKeywords tagsKeywords;

    public TagCommand(TagsContainsKeywords tagsKeywords) {
        this.tagsKeywords = tagsKeywords;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(tagsKeywords);
        model.updateFriendList(tagsKeywords);
        model.updateOtherList(tagsKeywords);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
