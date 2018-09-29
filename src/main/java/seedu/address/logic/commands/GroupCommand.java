package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

/**
 * Adds group to the Address Book.
 */

public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String COMMAND_WORD_2 = "grp";

    public static final String COMMAND_ARGS = "Group Name: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group the address book. "
            + "Parameters: "
            + PREFIX_GROUP + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "CS2113 ";

    private final String name;

    public GroupCommand(String name){
        requireAllNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException{
        throw new CommandException(String.format(COMMAND_ARGS, name));
    }

    @Override
    public boolean equals(Object other) {
        // if same object means return true
        if (other == this) {
            return true;
        }
        // instanceof will handle nulls
        if (!(other instanceof GroupCommand)) {
            return false;
        }
        // state check
        GroupCommand e = (GroupCommand) other;
        return name.equals(e.name);
    }
}
