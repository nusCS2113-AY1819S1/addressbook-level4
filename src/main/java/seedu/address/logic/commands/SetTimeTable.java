package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 *  Sets a person timetable
 */
public class SetTimeTable extends Command {

    public static final String COMMAND_WORD = "settime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the timetable of the person identified "
            + "by the index number used in the last person listing";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "SetTimeTable not implemented yet";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
