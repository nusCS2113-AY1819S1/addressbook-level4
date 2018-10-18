package seedu.address.logic.commands.user;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Terminate Ui and return user to login sceen
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_LOGOUT_ACKNOWLEDGEMENT = "Logout Address Book as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new LogoutEvent ());
        return new CommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
    }
}
