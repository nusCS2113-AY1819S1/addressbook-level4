package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
//@@Author OscarZeng
/**
 * This function will print out all the limit information.
 */
public class CheckLimitCommand extends Command {
    public static final String COMMAND_WORD = "checklimit";
    public static final String MESSAGE_SUCCESS = "Listed %d limits.";
    public static final String MESSAGE_FAILURE = "No more limits to list!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getLimitList().isEmpty()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getLimitList().size())
                + model.manualLimitCheck());
    }
}
