package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.SearchHistoryManager;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names/tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    /**
     * Ensures that Search History only remains valid for back-to-back find commands
     */
    protected void ensureSearchHistoryValidity(CommandHistory history) {
        String lastExecutedCommand = history.getLastExecutedCommand();
        String lastExecutedCommandWord = "";
        if (lastExecutedCommand != null) {
            lastExecutedCommandWord = AddressBookParser.basicParseCommand(lastExecutedCommand);
        }
        if (!lastExecutedCommandWord.equals(COMMAND_WORD)) {
            SearchHistoryManager.getInstance().clearSearchHistory();
        }
    }

    protected Predicate getMostUpdatedPredicate(Predicate predicate) {
        return SearchHistoryManager.getInstance().updateNewSearch(predicate);
    }
}
