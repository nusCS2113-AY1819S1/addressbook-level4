package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
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
 * Manages storage of ProductDatabase data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    private DistributorBookStorage distributorBookStorage;
    private ProductDatabaseStorage productDatabaseStorage;
    private UserPrefsStorage userPrefsStorage;
    private UserDatabaseStorage userDatabaseStorage;
    private SalesHistoryStorage salesHistoryStorage;

    public StorageManager(ProductDatabaseStorage productDatabaseStorage, DistributorBookStorage distributorBookStorage,
                          UserPrefsStorage userPrefsStorage, UserDatabaseStorage userDatabaseStorage,
                          SalesHistoryStorage salesHistoryStorage) {
        super();
        this.productDatabaseStorage = productDatabaseStorage;
        this.distributorBookStorage = distributorBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userDatabaseStorage = userDatabaseStorage;
        this.salesHistoryStorage = salesHistoryStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ProductDatabase methods ==============================

    @Override
    public Path getProductInfoBookFilePath() {
        return productDatabaseStorage.getProductInfoBookFilePath();
    }

    @Override
    public Optional<ReadOnlyProductDatabase> readProductDatabaseBook() throws DataConversionException, IOException {
        return readProductDatabaseBook(productDatabaseStorage.getProductInfoBookFilePath());
    }

    @Override
    public Optional<ReadOnlyProductDatabase> readProductDatabaseBook(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return productDatabaseStorage.readProductDatabaseBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyProductDatabase addressBook) throws IOException {
        saveAddressBook(addressBook, productDatabaseStorage.getProductInfoBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyProductDatabase addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        productDatabaseStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(ProductDatabaseChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ DistributorBook methods ==============================

    @Override
    public Path getDistributorBookFilePath() {
        return distributorBookStorage.getDistributorBookFilePath();
    }

    @Override
    public Optional<ReadOnlyDistributorBook> readDistributorBook() throws DataConversionException, IOException {
        return readDistributorBook(distributorBookStorage.getDistributorBookFilePath());
    }

    @Override
    public Optional<ReadOnlyDistributorBook> readDistributorBook(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return distributorBookStorage.readDistributorBook(filePath);
    }

    @Override
    public void saveDistributorBook(ReadOnlyDistributorBook distributorBook) throws IOException {
        saveDistributorBook(distributorBook, distributorBookStorage.getDistributorBookFilePath());
    }

    @Override
    public void saveDistributorBook(ReadOnlyDistributorBook distributorBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        distributorBookStorage.saveDistributorBook(distributorBook, filePath);
    }

    @Override
    public void deleteDistributorBook(User user) throws IOException {
        logger.fine("Attempting to delete to data file: " + user.getDistributorBookFilePath());
        distributorBookStorage.deleteDistributorBook(user);
    }

    @Override
    @Subscribe
    public void handleDistributorBookChangedEvent(DistributorBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveDistributorBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ UserDatabase methods ==============================

    @Override
    public Path getUserDatabaseFilePath() {
        return userDatabaseStorage.getUserDatabaseFilePath();
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException {
        return readUserDatabase(userDatabaseStorage.getUserDatabaseFilePath());
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userDatabaseStorage.readUserDatabase(filePath);
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException {
        saveUserDatabase(userDatabase, userDatabaseStorage.getUserDatabaseFilePath());
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userDatabaseStorage.saveUserDatabase(userDatabase, filePath);
    }

    @Override
    public void deleteAddressBook(User user) throws IOException {
        logger.fine("Attempting to delete to data file: " + user.getAddressBookFilePath());
        productDatabaseStorage.deleteAddressBook(user);
    }

    @Override
    @Subscribe
    public void handleUserDatabaseChangedEvent(UserDatabaseChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local users data changed, saving to file"));
        try {
            saveUserDatabase(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleUserDeletedEvent(UserDeletedEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "User has been deleted, deleting files"));
        deleteAddressBook(event.data);
        deleteSalesHistory();
    }

    //================ Sales History methods =======================

    @Override
    public Path getSalesHistoryFilePath() {
        return salesHistoryStorage.getSalesHistoryFilePath();
    }

    @Override
    public Optional<ReadOnlySalesHistory> readSalesHistory() throws DataConversionException, IOException {
        return salesHistoryStorage.readSalesHistory();
    }

    @Override
    public Optional<ReadOnlySalesHistory> readSalesHistory(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return salesHistoryStorage.readSalesHistory(filePath);
    }

    @Override
    public void saveSalesHistory(ReadOnlySalesHistory salesHistory) throws IOException {
        salesHistoryStorage.saveSalesHistory(salesHistory, salesHistoryStorage.getSalesHistoryFilePath());
    }

    @Override
    public void saveSalesHistory(ReadOnlySalesHistory salesHistory, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        salesHistoryStorage.saveSalesHistory(salesHistory, filePath);
    }

    @Override
    public void deleteSalesHistory() throws IOException {
        salesHistoryStorage.deleteSalesHistory();
    }

    @Override
    @Subscribe
    public void handleSalesHistoryChangedEvent(SalesHistoryChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage
                (event, "Sales history data changed, saving to file"));
        try {
            saveSalesHistory(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ============== Storage updater =====================

    /**
     * Updates the storage with data from files.
     * @param user
     */
    public void update(User user) {
        this.productDatabaseStorage = new XmlProductDatabaseStorage(user.getAddressBookFilePath());
        this.salesHistoryStorage = new XmlSalesHistoryStorage(user.getSalesHistoryFilePath());
        this.distributorBookStorage = new XmlDistributorBookStorage(user.getDistributorBookFilePath());
    }

}
