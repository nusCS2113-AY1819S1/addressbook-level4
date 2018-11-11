package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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
    private final KeywordType type = KeywordType.IncludeTags;

    public IncludeTagFindCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        executeSearch(model, predicate, type, predicate.getLowerCaseKeywords());
        return getCommandResultWithKeywordsHistory(model);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IncludeTagFindCommand // instanceof handles nulls
                && predicate.equals(((IncludeTagFindCommand) other).predicate)); // state check
    }
}
