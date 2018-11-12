package seedu.divelog.logic.commands;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.model.Model;

//author @Cjunx
/**
 * Ports Over Planning mode
 */
public class PortOverCommand extends Command {
    public static final String COMMAND_WORD_PORT_OVER = "portover";

    public static final String MESSAGE_ENTRY = "You've successfully ported over your planning dives";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.getPlanningMode()) {
            if (model.getPlannerCount() == 0) {
                String messageNoData = "You have yet to input any planning dives to port over";
                throw new CommandException(messageNoData);
            }
            model.zeroPlannerCount();
            model.setPlanningMode();
            return new CommandResult(MESSAGE_ENTRY);
        } else {
            String messageFailure = "You are not in planning mode thus you have no data to port over";
            throw new CommandException(messageFailure);
        }
    }
}


