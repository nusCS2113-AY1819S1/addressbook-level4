package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
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
 * Manages storage of ProductDatabase data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    private DistributorBookStorage distributorBookStorage;
    private ProductDatabaseStorage productDatabaseStorage;
    private UserPrefsStorage userPrefsStorage;
    private UserDatabaseStorage userDatabaseStorage;

    public StorageManager(ProductDatabaseStorage productDatabaseStorage, DistributorBookStorage distributorBookStorage,
                          UserPrefsStorage userPrefsStorage, UserDatabaseStorage userDatabaseStorage) {
        super();
        this.productDatabaseStorage = productDatabaseStorage;
        this.distributorBookStorage = distributorBookStorage;

        this.userPrefsStorage = userPrefsStorage;
        this.userDatabaseStorage = userDatabaseStorage;
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
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(productDatabaseStorage.getProductInfoBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return productDatabaseStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, productDatabaseStorage.getProductInfoBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        productDatabaseStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
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
    }

    // ============== Storage updater =====================

    public void update(User user) {
        this.productDatabaseStorage = new XmlProductDatabaseStorage(user.getAddressBookFilePath());
        this.distributorBookStorage = new XmlDistributorBookStorage(user.getDistributorBookFilePath());
    }

}
