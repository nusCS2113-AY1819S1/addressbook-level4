package seedu.divelog.logic.commands;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.model.Model;

/**
 * exits planning mode
 */
public class ExitPlanningCommand extends Command {
    public static final String COMMAND_WORD_NORMAL = "normalmode";

    public static final String MESSAGE_EXIT = "You're now in Normal Mode";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.getPlanningMode()) {
            while (model.getPlannerCount() > 0) {
                model.undoDiveLog();
                model.plannerCountMinus();
            }
            model.setPlanningMode();
            return new CommandResult(MESSAGE_EXIT);
        } else {
            throw new CommandException("You are already in Normal Mode.");
        }
    }
}
