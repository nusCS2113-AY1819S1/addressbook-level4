package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.InventoryListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.UserPrefs;
import seedu.address.storage.logininfo.LoginInfoStorage;

/**
 * Manages storage of Inventory List data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private InventoryListStorage inventoryListStorage;
    private UserPrefsStorage userPrefsStorage;
    private LoginInfoStorage loginInfoStorage;

    public StorageManager(InventoryListStorage inventoryListStorage, UserPrefsStorage userPrefsStorage,
                          LoginInfoStorage loginInfoStorage) {
        super();
        // this.addressBookStorage = addressBookStorage;
        this.inventoryListStorage = inventoryListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.loginInfoStorage = loginInfoStorage;
    }

    // ================ LoginInfoManager methods ==============================

    @Override
    public Path getLoginInfoFilePath() {
        return loginInfoStorage.getLoginInfoFilePath ();
    }

    @Override
    public Optional<LoginInfoManager> readLoginInfo() throws DataConversionException, IOException {
        return loginInfoStorage.readLoginInfo ();
    }

    @Override
    public void saveLoginInfo(LoginInfoManager loginInfoManager) throws IOException {
        loginInfoStorage.saveLoginInfo (loginInfoManager);
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

    // ================ AddressBook methods ==============================
    /*
    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    */
    // ================ Inventory List methods ==============================
    @Override
    public Path getInventoryListFilePath() {
        return inventoryListStorage.getInventoryListFilePath();
    }


    @Override
    public Optional<ReadOnlyInventoryList> readInventoryList() throws DataConversionException, IOException {
        return readInventoryList(inventoryListStorage.getInventoryListFilePath());
    }

    @Override
    public Optional<ReadOnlyInventoryList> readInventoryList(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return inventoryListStorage.readInventoryList(filePath);
    }

    @Override
    public void saveInventoryList(ReadOnlyInventoryList inventoryList) throws IOException {
        saveInventoryList(inventoryList, inventoryListStorage.getInventoryListFilePath());
    }

    @Override
    public void saveInventoryList(ReadOnlyInventoryList inventoryList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        inventoryListStorage.saveInventoryList(inventoryList, filePath);
    }


    @Subscribe
    public void handleInventoryListChangedEvent(InventoryListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveInventoryList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
