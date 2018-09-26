package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;

/**
 * Imports the address book from a directory.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = CliSyntax.COMMAND_EXPORT;
    public static final String MESSAGE_UPDATE = "Export command works!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_UPDATE);
    }
}
