package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Creates a group in the addressbook
 */
public class CreateGroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String COMMAND_WORD_2 = "grp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new Group. "
            + "Parameters: "
            + PREFIX_NAME + "GROUP_NAME "
            + PREFIX_GROUP_LOCATION + "GROUP_LOCATION\n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example:"
            + COMMAND_WORD + " "
            + PREFIX_NAME + "TUT[E01] "
            + PREFIX_GROUP_LOCATION + "E1-06-01"
            + PREFIX_TAG + "SmartGroup ";

    public static final String MESSAGE_SUCCESS = "New Group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This Group already exist in the Student Management System.";

    private final Group createGroup;


    public CreateGroupCommand(Group newGroup) {
        requireNonNull(newGroup);
        createGroup = newGroup;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasGroup(createGroup)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.createGroup(createGroup);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, createGroup));
    }


}
