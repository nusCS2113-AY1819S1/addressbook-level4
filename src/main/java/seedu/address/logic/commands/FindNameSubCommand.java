package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.searchhistory.KeywordType;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindNameSubCommand extends FindCommand {

    private final NameContainsKeywordsPredicate predicate;

    public FindNameSubCommand(NameContainsKeywordsPredicate predicate) {
        this(predicate, false);
    }

    public FindNameSubCommand(NameContainsKeywordsPredicate predicate, boolean isExcludeMode) {
        this.predicate = predicate;
        this.isExcludeMode = isExcludeMode;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (isExcludeMode) {
            model.recordKeywords(KeywordType.ExcludeNames, predicate.getLowerCaseKeywords());
            model.executeSearch(predicate.negate());
        } else {
            model.recordKeywords(KeywordType.IncludeNames, predicate.getLowerCaseKeywords());
            model.executeSearch(predicate);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size())
                        + formatter.getOutputString(model.getReadOnlyKeywordsRecord()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindNameSubCommand // instanceof handles nulls
                && predicate.equals(((FindNameSubCommand) other).predicate)); // state check
    }
}
