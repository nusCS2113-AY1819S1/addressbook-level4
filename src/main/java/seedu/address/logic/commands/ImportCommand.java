package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author jitwei98
/**
 * Import the persons and todos to the address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import the persons and todos to the address book.\n"
            + "Parameters: FILENAME (required)\n"
            + "Example: " + COMMAND_WORD + " export.xml ";

    // TODO: do the same for todos
    public static final String MESSAGE_IMPORT_SUCCESS = "Imported %1$s persons and %2$s todos.";
    // = "Imported Persons: %1$s, Imported Todos: %1$s";
    private static final String MESSAGE_FAILURE = "Import failed!";

    private final Path filePath;

    public ImportCommand(Path filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        int initialNumberOfPersons = model.getFilteredPersonList().size();
        int initialNumberOfTodos = model.getFilteredTodoList().size();

        // TODO: Write better Exception messages
        try {
            model.importAddressBook(filePath);
        } catch (IOException e) {
            throw new CommandException("IOException");
        } catch (DataConversionException e) {
            throw new CommandException("DataConversionException");
        }

        int finalNumberOfPersons = model.getFilteredPersonList().size();
        int finalNumberOfTodos = model.getFilteredTodoList().size();

        int personsImported = calculateImportedEntries(initialNumberOfPersons, finalNumberOfPersons);
        int todosImported = calculateImportedEntries(initialNumberOfTodos, finalNumberOfTodos);

        return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, personsImported, todosImported));
    }

    /**
     * Returns the number of entries imported to the address book.
     * @throws CommandException If an error occurs during command execution.
     */
    private static int calculateImportedEntries(int initialListSize, int finalListSize) throws CommandException {
        requireNonNull(initialListSize);
        requireNonNull(finalListSize);

        int importedEntries = finalListSize - initialListSize;
        if (!isValidSize(importedEntries)) {
            // TODO: Write better Exception message
            throw new CommandException("Invalid imported person list size");
        }
        return importedEntries;
    }

    private static boolean isValidSize(int size) {
        return size >= 0;
    }

    @Override
    public boolean equals(Object other) {
        // same object
        if (other == this) {
            return true;
        }

        // handles nulls
        if (!(other instanceof ImportCommand)) {
            return false;
        }

        // checks state
        ImportCommand e = (ImportCommand) other;
        return filePath.equals(e.filePath);
    }
}
