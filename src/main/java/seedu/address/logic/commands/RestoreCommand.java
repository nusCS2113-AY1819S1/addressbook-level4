package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;

/**
 * Restores the address book to a snapshot of choice.
 */
public class RestoreCommand extends Command {
    public static final String COMMAND_WORD = CliSyntax.COMMAND_RESTORE;
    public static final String MESSAGE_UPDATE = COMMAND_WORD + ": Restores the address book to a snapshot of choice.\n"
            + COMMAND_WORD + " list\n"
            + COMMAND_WORD + " DD/MM/YYYY" + " time";

    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_UPDATE);
    }
}
