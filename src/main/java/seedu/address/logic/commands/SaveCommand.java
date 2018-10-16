package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all items in the stock list to the user.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_SUCCESS = "Saved Stock List";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves current version of stock list "
            + "as an xml file with the specified file name.\n"
            + "Parameters: FILENAME \n"
            + "Example: " + COMMAND_WORD + " backup";

    private static String fileName;

    public SaveCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.saveStockList(fileName);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
