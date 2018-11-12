package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.isFileExists;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

//@@author jitwei98
/**
 * Import the persons and todos to the address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import the persons in the file specified to the "
            + "address book.\n"
            + "Parameters: FILENAME (required)\n"
            + "Example: " + COMMAND_WORD + " export.xml ";

    public static final String MESSAGE_IMPORT_SUCCESS = "Imported %1$s persons.";
    private static final String MESSAGE_FAILURE = "Import failed! Error: %1$s";
    private static final String MESSAGE_INVALID_LIST_SIZE = "Invalid list size.";
    private static final String MESSAGE_FILE_NOT_FOUND = "File not found!";

    private final Path filePath;

    public ImportCommand(Path filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ReadOnlyAddressBook readOnlyAddressBook = model.getAddressBook();
        ObservableList<Person> personList = readOnlyAddressBook.getPersonList();
        int initialNumberOfPersons = personList.size();

        if (!isFileExists(filePath)) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        }

        // TODO: Write better Exception messages
        try {
            model.importPersonsFromAddressBook(filePath);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_FAILURE, ioe));
        } catch (DataConversionException dce) {
            throw new CommandException(String.format(MESSAGE_FAILURE, dce));
        }

        int finalNumberOfPersons = personList.size();
        int personsImported = calculateImportedEntries(initialNumberOfPersons, finalNumberOfPersons);

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, personsImported));
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
            throw new CommandException(String.format(MESSAGE_FAILURE, MESSAGE_INVALID_LIST_SIZE));
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
