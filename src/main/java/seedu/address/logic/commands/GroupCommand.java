package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;


public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";


    public static final String SECTION_UNDER_CONSTRUCTION = "section under construction";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2113 ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException{
        throw new CommandException(SECTION_UNDER_CONSTRUCTION);
    }

}
