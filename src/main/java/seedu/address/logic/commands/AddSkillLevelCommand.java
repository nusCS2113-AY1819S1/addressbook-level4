package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLLEVEL;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
/**
 * Changes the skill of an existing person in the address book, with a tagged skill level.
 */

public class AddSkillLevelCommand extends Command {
    public static final String COMMAND_WORD = "asl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the skill of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing skill will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_SKILLLEVEL + "[SKILL] [LEVEL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SKILLLEVEL + "Photography 5";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "AddSkillWithLevel command not implemented yet";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
