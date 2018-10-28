package seedu.address.logic.drinkcommands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.DrinkModel;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends DrinkCommand {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public DrinkCommandResult execute(DrinkModel model, CommandHistory history) {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new DrinkCommandResult(SHOWING_HELP_MESSAGE);
    }
}
