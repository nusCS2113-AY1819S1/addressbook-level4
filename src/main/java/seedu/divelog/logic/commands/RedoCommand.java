package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.model.Model;

/**
 * Reverts the {@code model}'s divelog book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoDiveLog()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoDiveLog();
        model.updateFilteredDiveList(Model.PREDICATE_SHOW_ALL_DIVES);
        if (model.getPlanningMode()) {
            model.plannerCountPlus();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
