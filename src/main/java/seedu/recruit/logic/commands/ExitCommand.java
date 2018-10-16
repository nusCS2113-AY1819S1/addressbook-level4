package seedu.recruit.logic.commands;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ExitAppRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
