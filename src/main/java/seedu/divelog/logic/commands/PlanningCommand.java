package seedu.divelog.logic.commands;

import seedu.divelog.logic.CommandHistory;

import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.model.Model;

//author @Cjunx
/**
 * Enters or exit the Planning Mode
 */
public class PlanningCommand extends Command {

    public static final String COMMAND_WORD_PLAN = "newplan";

    public static final String MESSAGE_ENTRY = "You're now in Planning Mode";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.getPlanningMode()) {
            model.zeroPlannerCount();
            model.setPlanningMode();
            return new CommandResult(MESSAGE_ENTRY);
        } else {
            throw new CommandException("You are already in Planning Mode, and thus cannot enter Planning Mode");
        }
    }
}
