package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author jitwei98
/**
 * Exports the listed persons in the address book to a xml file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_PARAMETERS = "Parameters: FILENAME (must end with .xml)\n";
    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD + " export.xml";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the persons listed "
            + "in the address book.\n"
            + COMMAND_PARAMETERS
            + COMMAND_EXAMPLE;

    public static final String MESSAGE_EXPORT_SUCCESS = "Exported persons listed to %1$s";
    public static final String MESSAGE_FAILURE = "Export failed!";

    private final Path filePath;

    public ExportCommand(Path filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            model.exportFilteredAddressBook(filePath);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        // same object
        if (other == this) {
            return true;
        }

        // handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        // checks state
        ExportCommand e = (ExportCommand) other;
        return filePath.equals(e.filePath);
    }
}
