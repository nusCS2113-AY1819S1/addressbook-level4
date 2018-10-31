package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all items in the stock list to the user.
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_SUCCESS = "Opened File";

    public static final String MESSAGE_INVALID_FILE_NAME = "Invalid file name! \n%1$s";

    public static final String MESSAGE_EMPTY_FILE_NAME = "No file name inputted! \n%1$s";

    public static final String MESSAGE_FILE_NOT_EXIST = "No such file in directory! \n%1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens specified .xml file version of stock list "
            + "\n"
            + "Parameters: FILENAME \n"
            + "Example: " + COMMAND_WORD + " backup";

    private final String fileName;

    public OpenCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.openStockList(fileName);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenCommand // instanceof handles nulls
                && fileName.equals(((OpenCommand) other).fileName));
    }
}
