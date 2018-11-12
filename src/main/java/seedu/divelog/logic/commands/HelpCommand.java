package seedu.divelog.logic.commands;

import seedu.divelog.commons.core.EventsCenter;
import seedu.divelog.commons.events.ui.ShowHelpRequestEvent;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (model.getPlanningMode()) {
            model.plannerCountPlus();
        }
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
