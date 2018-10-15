package seedu.address.logic.commands;

import UnRefactored.commons.core.EventsCenter;
import UnRefactored.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command implements CommandParser {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new ExitCommand();
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
