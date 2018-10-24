package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.DistributorBookChangedEvent;
import seedu.address.commons.events.model.UserDatabaseChangedEvent;
import seedu.address.commons.events.model.UserDeletedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.login.User;

/**
 * API of the Storage component
 */
public interface Storage extends ProductDatabaseStorage, DistributorBookStorage, UserPrefsStorage, UserDatabaseStorage {


    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getProductInfoBookFilePath();

    @Override
    Path getDistributorBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyDistributorBook> readDistributorBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    void saveDistributorBook(ReadOnlyDistributorBook distributorBook) throws IOException;


    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleDistributorBookChangedEvent(DistributorBookChangedEvent abce);

    /**
     * Saves the current version of the User Database to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleUserDatabaseChangedEvent(UserDatabaseChangedEvent abce);

    /**
     * Saves the current version of the User Database to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleUserDeletedEvent(UserDeletedEvent event) throws IOException;

    @Override
    Path getUserDatabaseFilePath();

    @Override
    Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException;

    @Override
    void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException;

    @Override
    void deleteAddressBook(User user) throws IOException;

    @Override
    void deleteDistributorBook(User user) throws IOException;

    void update(User user);
}
