package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Group;

/**
 * Adds group to the Address Book.
 */

public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String COMMAND_WORD_2 = "grp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group the address book. "
            + "Parameters: "
            + PREFIX_GROUP + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "CS2113 ";

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book";


    private final Group toAdd;

    public GroupCommand(Group group) {
        requireAllNonNull(group);
        this.toAdd = group;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model);

        if (model.hasGroup(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.addGroup(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
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
        return toAdd.equals(e.toAdd);
    }
}
