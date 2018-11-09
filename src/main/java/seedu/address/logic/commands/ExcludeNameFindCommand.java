package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.searchhistory.KeywordType;

/**
 * Finds and lists all persons in address book whose name does not contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ExcludeNameFindCommand extends FindCommand {

    private final NameContainsKeywordsPredicate predicate;

    public ExcludeNameFindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        model.recordKeywords(KeywordType.ExcludeNames, predicate.getLowerCaseKeywords());
        model.executeSearch(predicate.negate());
        String keywordHistoryString = getKeywordHistoryString(model.getReadOnlyKeywordsRecord());
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size())
                        + keywordHistoryString);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExcludeNameFindCommand // instanceof handles nulls
                && predicate.equals(((ExcludeNameFindCommand) other).predicate)); // state check
    }
}
