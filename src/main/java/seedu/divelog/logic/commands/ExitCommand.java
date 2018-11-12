package seedu.divelog.logic.commands;

import seedu.divelog.commons.core.EventsCenter;
import seedu.divelog.commons.events.ui.ExitAppRequestEvent;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        if (model.getPlanningMode()) {
            model.plannerCountPlus();
        }
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
