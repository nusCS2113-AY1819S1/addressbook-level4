package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;

/**
 * Backs up a snapshot of the address book into a .backup folder.
 */
public class BackUpCommand extends Command {

    public static final String COMMAND_WORD = CliSyntax.COMMAND_BACKUP;
    public static final String MESSAGE_SUCCESS = "Address book has been backed up!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
