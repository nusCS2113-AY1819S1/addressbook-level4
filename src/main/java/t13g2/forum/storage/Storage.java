package t13g2.forum.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import t13g2.forum.commons.events.model.AddressBookChangedEvent;
import t13g2.forum.commons.events.storage.DataSavingExceptionEvent;
import t13g2.forum.commons.exceptions.DataConversionException;
import t13g2.forum.model.ReadOnlyAddressBook;
import t13g2.forum.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
