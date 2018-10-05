package seedu.planner.logic.commands;

import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.events.ui.ExitAppRequestEvent;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Financial Planner as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
