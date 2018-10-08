package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ListGroupCommand extends Command {
    public static final String COMMAND_WORD = "listgroup";
    public static final String COMMAND_WORD_2 = "lg";
    public static final String MESSAGE_SUCCESS = "Listed all groups";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "List group command not implemented yet";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException{
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }

}
