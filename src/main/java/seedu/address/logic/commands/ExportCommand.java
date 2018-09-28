package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;

import java.nio.file.Path;

/**
 * Exports CSV file into a directory from the address book.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = CliSyntax.COMMAND_EXPORT;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the address book into a directory"
            + "Parameters: directory";
    public static final String MESSAGE_UPDATE = "DIRECTORY DOES NOT EXIST";
    public static final String MESSAGE_SUCCESS = "Directory exist :)!";

    private int flag = 0;

    public ExportCommand(Path path) {
        this.flag = 1;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (flag == 1) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_UPDATE);
        }
    }
}
