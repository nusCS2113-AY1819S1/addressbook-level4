package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AddressBookLocalBackupEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.OnlineBackupEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.OnlineBackupFailureException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;


/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;

    private GitHubStorage gitHubStorage;

    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
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

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook, Path backupFilePath) throws IOException {
        addressBookStorage.backupAddressBook(addressBook, backupFilePath);
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
    //@@author QzSG
    @Override
    @Subscribe
    public void handleAddressBookLocalBackupEvent(AddressBookLocalBackupEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Saving student planner data as backup"));
        try {
            backupAddressBook(event.data, event.filePath);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ GitHub Storage methods ==============================
    /*
        Listens directly to BackupCommand
     */
    @SuppressWarnings("unused")
    @Subscribe
    public void handleOnlineBackupEvent(OnlineBackupEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Saving data to online storage"));
        try {
            backupOnline(event.target, event.data, event.fileName, event.authToken);
        } catch (IOException | OnlineBackupFailureException | JAXBException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    /**
     * Performs online backup to supported online storage
     * @param target
     * @param data
     * @param fileName
     * @param authToken
     * @throws IOException
     * @throws OnlineBackupFailureException
     * @throws JAXBException
     */
    private void backupOnline(OnlineStorage.OnlineStorageType target, ReadOnlyAddressBook data,
                              String fileName, Optional<String> authToken)
            throws IOException, OnlineBackupFailureException, JAXBException {
        switch(target) {
        case GITHUB:
        default:
            gitHubStorage = new GitHubStorage(
                    authToken.orElseThrow(() -> new OnlineBackupFailureException("Invalid auth token received")));
            gitHubStorage.saveContentToStorage(XmlUtil.convertContentToString(
                new XmlSerializableAddressBook(data)), fileName, "Address Book Backup");
        }
    }
}
