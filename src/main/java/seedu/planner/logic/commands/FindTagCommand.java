package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.record.TagsContainsKeywordsPredicate;

/**
 * Finds and lists all records in financial planner whose tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all records whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " food clothes shopping";

    public static final String MESSAGE_SUCCESS = "%1$d records listed matching tags: %2$s.\n";

    private final String[] keywords;

    private final TagsContainsKeywordsPredicate predicate;

    public FindTagCommand(TagsContainsKeywordsPredicate predicate, String[] keywords) {
        this.predicate = predicate;
        this.keywords = keywords;
    }

    private String convertKeywordsToSuccessMessage() {
        StringBuilder message = new StringBuilder();
        for (String keyword: keywords) {
            message.append("[").append(keyword).append("], ");
        }

        String newMessage = message.toString();
        newMessage = newMessage.substring(0, newMessage.length() - 2);
        return newMessage;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRecordList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                model.getFilteredRecordList().size(), convertKeywordsToSuccessMessage()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && predicate.equals(((FindTagCommand) other).predicate)); // state check
    }
}
