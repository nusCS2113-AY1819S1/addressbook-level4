package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.BookInventoryChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBookInventory;
import seedu.address.model.UserPrefs;

/**
 * API of the InventoryStorage component
 */
public interface InventoryStorage extends BookInventoryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyBookInventory> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyBookInventory addressBook) throws IOException;

    /**
     * Saves the current version of the BookInventory to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(BookInventoryChangedEvent bice);
}
