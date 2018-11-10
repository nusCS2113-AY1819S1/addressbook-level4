package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.searchhistory.KeywordType;

/**
 * Finds and lists all persons in address book whose tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class IncludeTagFindCommand extends FindCommand {

    private final TagContainsKeywordsPredicate predicate;

    public IncludeTagFindCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.recordKeywords(KeywordType.IncludeTags, predicate.getLowerCaseKeywords());
        model.executeSearch(predicate);
        String keywordHistoryString = getKeywordHistoryString(model.getReadOnlyKeywordsRecord());
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size())
                        + keywordHistoryString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IncludeTagFindCommand // instanceof handles nulls
                && predicate.equals(((IncludeTagFindCommand) other).predicate)); // state check
    }
}
