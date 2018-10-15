package UnRefactored.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import UnRefactored.commons.events.model.TaskBookChangedEvent;
import UnRefactored.commons.events.storage.DataSavingExceptionEvent;
import UnRefactored.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getTaskBookFilePath();

    @Override
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    @Override
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(TaskBookChangedEvent abce);
}
