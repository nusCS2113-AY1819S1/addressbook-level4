package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
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
        try {
            model.revertLastSearch();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyHistoryException e) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
