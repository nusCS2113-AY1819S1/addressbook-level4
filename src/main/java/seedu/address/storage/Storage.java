package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.DistributorBookChangedEvent;
import seedu.address.commons.events.model.ProductDatabaseChangedEvent;
import seedu.address.commons.events.model.SalesHistoryChangedEvent;
import seedu.address.commons.events.model.UserDatabaseChangedEvent;
import seedu.address.commons.events.model.UserDeletedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.ReadOnlyProductDatabase;
import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.login.User;
import seedu.address.model.saleshistory.ReadOnlySalesHistory;

/**
 * API of the Storage component
 */
public interface Storage extends ProductDatabaseStorage, DistributorBookStorage,
        UserPrefsStorage, UserDatabaseStorage, SalesHistoryStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getProductInfoBookFilePath();

    @Override
    Path getDistributorBookFilePath();

    @Override
    Optional<ReadOnlyProductDatabase> readProductDatabaseBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyDistributorBook> readDistributorBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyProductDatabase addressBook) throws IOException;

    @Override
    void saveDistributorBook(ReadOnlyDistributorBook distributorBook) throws IOException;


    /**
     * Saves the current version of the ProductInfo Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(ProductDatabaseChangedEvent abce);

    /**
     * Saves the current version of the ProductInfo Book to the hard disk.
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

    /**
     * Saves the current version of the Sales History to the hard disk.
     * Creates the data file if missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSalesHistoryChangedEvent(SalesHistoryChangedEvent event);

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

    @Override
    void deleteDistributorBook(User user) throws IOException;

    void update(User user);
}
