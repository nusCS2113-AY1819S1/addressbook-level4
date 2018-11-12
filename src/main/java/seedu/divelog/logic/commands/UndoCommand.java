package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.model.Model;

/**
 * Reverts the {@code model}'s divelog book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoDiveLog()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        if (model.getPlanningMode()) {
            model.plannerCountPlus();
        }
        model.undoDiveLog();
        model.updateFilteredDiveList(Model.PREDICATE_SHOW_ALL_DIVES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
