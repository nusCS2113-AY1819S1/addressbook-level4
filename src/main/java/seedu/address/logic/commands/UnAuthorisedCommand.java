package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * A command for any unAuthorised Command.
 */
public class UnAuthorisedCommand extends Command {
    public static final String COMMAND_WORD = "UnAuthorised";
    public static final String MESSAGE_FAILURE = "You do not have access to %1$s command";
    private static String unAuthorisedCommand;
    public void setCommandWord(String command) {
        this.unAuthorisedCommand = command;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format (MESSAGE_FAILURE, unAuthorisedCommand));
    }

}
