package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

import com.google.common.eventbus.Subscribe;

import javafx.concurrent.Task;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AddressBookLocalBackupEvent;
import seedu.address.commons.events.model.AddressBookOnlineRestoreEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.OnlineBackupEvent;
import seedu.address.commons.events.storage.OnlineRestoreEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.OnlineBackupFailureException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
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
        backupOnline(event.target, event.data, event.fileName, event.authToken);
    }

    /*
        Listens directly to RestoreCommand
     */
    @SuppressWarnings("unused")
    @Subscribe
    public void handleOnlineRestoreEvent(OnlineRestoreEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Restoring data from online storage"));
        restoreOnline(event.target, event.ref, event.authToken);
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
                              String fileName, Optional<String> authToken) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Task task = new Task<Void>() {
            @Override public Void call() throws Exception {
                switch(target) {
                    case GITHUB:
                    default:
                        gitHubStorage = new GitHubStorage(
                                authToken.orElseThrow(() -> new OnlineBackupFailureException("Invalid auth "
                                        + "token received")));
                        String successMessage = gitHubStorage.saveContentToStorage(XmlUtil.convertDataToString(
                                new XmlSerializableAddressBook(data)), fileName, "Address Book Backup");
                        updateMessage(successMessage);
                }
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            raise(new NewResultAvailableEvent(task.getMessage()));
        });
        task.setOnFailed(event -> {
            raise(new DataSavingExceptionEvent((Exception) task.getException()));
        });
        executorService.submit(task);
    }

    /**
     * Performs restoration from supported online storage
     * @param target
     * @param ref
     * @param authToken
     * @throws IOException
     * @throws OnlineBackupFailureException
     * @throws JAXBException
     */
    private void restoreOnline(OnlineStorage.OnlineStorageType target, String ref, Optional<String> authToken) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Task task = new Task<AddressBook>() {
            @Override public AddressBook call() throws Exception {
                switch(target) {
                    case GITHUB:
                    default:
                        gitHubStorage = new GitHubStorage(
                            authToken.orElseThrow(() -> new OnlineBackupFailureException("Invalid auth "
                                    + "token received")));
                        AddressBook restoredAddressBook = XmlUtil.getDataFromString(
                                gitHubStorage.readContentFromGist(ref), XmlSerializableAddressBook.class).toModelType();
                        return restoredAddressBook;
                }
            }
        };
        task.setOnSucceeded(event -> {
            raise(new AddressBookOnlineRestoreEvent(((Task<AddressBook>) task).getValue()));
        });
        task.setOnFailed(event -> {
            raise(new DataSavingExceptionEvent((Exception) task.getException()));
        });
        executorService.submit(task);
    }
}
