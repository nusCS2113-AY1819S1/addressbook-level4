package seedu.address.logic.commands;

import java.nio.file.Path;

import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;

/**
 * Exports CSV file into a directory from the address book.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = CliSyntax.COMMAND_EXPORT;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the address book into a directory\n"
            + "Parameters: directory";
    public static final String MESSAGE_FAILURE = "DIRECTORY DOES NOT EXIST";
    public static final String MESSAGE_SUCCESS = "Directory exist :)!";

    private int flag = 0;

    public ExportCommand() {}

    public ExportCommand(Path path) {
        this.flag = 1;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        FileEncryptor fe = new FileEncryptor("data/addressbook.xml");

        if (fe.isLocked()) {
            throw new CommandException(fe.MESSAGE_ADDRESS_BOOK_LOCKED);
        }

        if (flag == 1) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_USAGE);
        }
    }
}
