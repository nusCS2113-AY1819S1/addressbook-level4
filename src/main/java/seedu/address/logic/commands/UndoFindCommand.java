package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.formatter.KeywordsOutputFormatter;
import seedu.address.model.Model;
import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

/**
 * Reverts the {@code model}'s search history to its previous state.
 */
public class UndoFindCommand extends Command {
    public static final String COMMAND_WORD = "undofind";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "Search History is empty";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        try {
            model.revertLastSearch();
            KeywordsOutputFormatter formatter = new KeywordsOutputFormatter();
            String keywordHistoryString = formatter.getOutputString(model.getReadOnlyKeywordsRecord());
            return new CommandResult(MESSAGE_SUCCESS + keywordHistoryString);
        } catch (EmptyHistoryException e) {
            model.resetSearchHistoryToInitialState();
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
