package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.searchhistory.SearchHistoryManager;
import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

/**
 * Reverts the {@code model}'s search history to its previous state.
 */
public class UndoSearchCommand extends Command {
    public static final String COMMAND_WORD = "undosearch";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "Search History is empty";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        SearchHistoryManager manager = model.getSearchHistoryManager();
        try {
            Predicate predicate = manager.revertLastSearch();
            if (predicate == null) {
                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            } else {
                model.updateFilteredPersonList(predicate);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyHistoryException e) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
