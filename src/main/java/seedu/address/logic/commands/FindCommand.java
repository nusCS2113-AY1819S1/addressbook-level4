package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.logic.parser.FindCommandParser;
import seedu.address.model.searchhistory.SearchHistoryManager;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names/tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [\\tag] [\\exclude] KEYWORD [MORE_KEYWORDS]...\n"
            + "Example1: " + COMMAND_WORD + " alice bob charlie\n"
            + "Example2: " + COMMAND_WORD + " " + FindCommandParser.EXCLUDE_OPTION_STRING + " alice\n"
            + "Example3: " + COMMAND_WORD + " " + FindCommandParser.TAG_OPTION_STRING + " President";

    protected boolean isExcludeMode = false;
    protected Predicate getMostUpdatedPredicate(SearchHistoryManager manager, Predicate predicate) {
        if (isExcludeMode) {
            predicate = predicate.negate();
        }
        return manager.executeNewSearch(predicate);
    }
}
