package seedu.address.logic.drinkcommands.user;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.drinkcommands.DrinkCommand;
import seedu.address.logic.drinkcommands.DrinkCommandResult;
import seedu.address.model.DrinkModel;

/**
 * Terminate Ui and return user to login sceen.
 * This functions as the exit command.
 */
public class LogoutCommand extends DrinkCommand {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_LOGOUT_ACKNOWLEDGEMENT = "Logging out Address Book as requested ...";

    @Override
    public DrinkCommandResult execute(DrinkModel model, CommandHistory history) {
        EventsCenter.getInstance().post(new LogoutEvent());
        return new DrinkCommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
    }
}
