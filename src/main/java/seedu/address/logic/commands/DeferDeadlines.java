package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Defer deadline of a specific task in the taskbook.
 */

public class DeferDeadlines extends Command{

    public static final String COMMAND_WORD = "defer";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Defers the selected task in the taskbook"
            + "by the index number used in the last person listing. "
            + "Existing deadline will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DEADLINE + "[DEADLINE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE + "1.";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Defer deadline command not implemented yet";
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }

}
