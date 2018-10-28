package seedu.address.logic.drinkcommands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.DrinkModel;

/**
 * Terminates the program.
 */
public class ExitCommand extends DrinkCommand {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public DrinkCommandResult execute(DrinkModel model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new DrinkCommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
