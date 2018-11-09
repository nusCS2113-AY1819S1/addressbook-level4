package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandsParser;
import seedu.address.logic.parser.DefaultParser;
import seedu.address.model.Model;

//@@author: IcedCoffeeBoy

/**
 * Login the person into ProManage
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    private static final String MESSAGE_SUCCESS = "Successfully logout, priority now is default";


    public LogoutCommand() {
    }

    public CommandsParser getParser() throws CommandException {
        return new DefaultParser();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
