package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.SalesHistoryChangedEvent;
import seedu.address.commons.events.model.UserDatabaseChangedEvent;
import seedu.address.commons.events.model.UserDeletedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.login.User;
import seedu.address.model.saleshistory.ReadOnlySalesHistory;

/**
 * API of the Storage component
 */
public interface Storage extends ProductDatabaseStorage, UserPrefsStorage, UserDatabaseStorage, SalesHistoryStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getProductInfoBookFilePath();

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

    /**
     * Saves the current version of the Sales History to the hard disk.
     * Creates the data file if missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSalesHistoryChangedEvent(SalesHistoryChangedEvent event) throws IOException;

    @Override
    Path getUserDatabaseFilePath();

    @Override
    Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException;

    @Override
    void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException;

    @Override
    void deleteAddressBook(User user) throws IOException;

    @Override
    Path getSalesHistoryFilePath();

    @Override
    Optional<ReadOnlySalesHistory> readSalesHistory() throws DataConversionException, IOException;

    @Override
    void saveSalesHistory(ReadOnlySalesHistory salesHistory) throws IOException;

    @Override
    void deleteSalesHistory() throws IOException;

    void update(User user);
}
