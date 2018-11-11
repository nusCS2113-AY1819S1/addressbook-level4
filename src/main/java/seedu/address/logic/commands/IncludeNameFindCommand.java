package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.searchhistory.KeywordType;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class IncludeNameFindCommand extends FindCommand {

    private final NameContainsKeywordsPredicate predicate;
    private final KeywordType type = KeywordType.IncludeNames;

    public IncludeNameFindCommand(NameContainsKeywordsPredicate predicate) {
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
                || (other instanceof IncludeNameFindCommand // instanceof handles nulls
                && predicate.equals(((IncludeNameFindCommand) other).predicate)); // state check
    }
}
