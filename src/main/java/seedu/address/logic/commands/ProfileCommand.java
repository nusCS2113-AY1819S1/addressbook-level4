package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowProfileRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Format full profile for user to input user information.
 */
public class ProfileCommand extends Command {

    public static final String COMMAND_WORD = "profile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows user's information.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_PROFILE_MESSAGE = "Opened profile window.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ShowProfileRequestEvent());
        return new CommandResult(SHOWING_PROFILE_MESSAGE);
    }
}
