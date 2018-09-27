package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Adds group to the Address Book.
 */

public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String COMMAND_WORD_2 = "grp";

    public static final String COMMAND_ARGS = "Name: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2113 ";

    private final String name;

    public GroupCommand(String name){
        requireAllNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException{
        throw new CommandException(String.format(COMMAND_ARGS, name));
    }

}
