package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Clears search history
 */
public class ClearSearchHistoryCommand extends Command {

    public static final String COMMAND_WORD = "clearsearch";
    private static final String MESSAGE_SUCCESS = "Search History Cleared";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        model.getSearchHistoryManager().clearSearchHistory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
