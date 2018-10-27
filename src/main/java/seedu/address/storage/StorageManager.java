package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ClubBudgetElementsBookChangedEvent;
import seedu.address.commons.events.model.LoginBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyClubBudgetElementsBook;
import seedu.address.model.ReadOnlyLoginBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private LoginBookStorage loginBookStorage;
    private AddressBookStorage addressBookStorage;
    private ClubBudgetElementsBookStorage clubBudgetElementsBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(LoginBookStorage loginBookStorage, AddressBookStorage addressBookStorage,
                          ClubBudgetElementsBookStorage clubBudgetElementsBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.loginBookStorage = loginBookStorage;
        this.addressBookStorage = addressBookStorage;
        this.clubBudgetElementsBookStorage = clubBudgetElementsBookStorage;
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

    // ================ LoginBook methods ==============================

    @Override
    public Path getLoginBookFilePath() {
        return loginBookStorage.getLoginBookFilePath();
    }

    @Override
    public Optional<ReadOnlyLoginBook> readLoginBook() throws DataConversionException, IOException {
        return readLoginBook(loginBookStorage.getLoginBookFilePath());
    }

    @Override
    public Optional<ReadOnlyLoginBook> readLoginBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return loginBookStorage.readLoginBook(filePath);
    }

    @Override
    public void saveLoginBook(ReadOnlyLoginBook loginBook) throws IOException {
        saveLoginBook(loginBook, loginBookStorage.getLoginBookFilePath());
    }

    @Override
    public void saveLoginBook(ReadOnlyLoginBook loginBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        loginBookStorage.saveLoginBook(loginBook, filePath);
    }

    @Override
    @Subscribe
    public void handleLoginBookChangedEvent(LoginBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveLoginBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
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
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ ClubBudgetElementsBook methods ==============================

    @Override
    public Path getClubBudgetElementsBookFilePath() {
        return clubBudgetElementsBookStorage.getClubBudgetElementsBookFilePath();
    }

    @Override
    public Optional<ReadOnlyClubBudgetElementsBook> readClubBudgetElementsBook() throws DataConversionException,
            IOException {
        return readClubBudgetElementsBook(clubBudgetElementsBookStorage.getClubBudgetElementsBookFilePath());
    }

    @Override
    public Optional<ReadOnlyClubBudgetElementsBook> readClubBudgetElementsBook(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return clubBudgetElementsBookStorage.readClubBudgetElementsBook(filePath);
    }

    @Override
    public void saveClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook clubBudgetElementsBook) throws IOException {
        saveClubBudgetElementsBook(clubBudgetElementsBook, clubBudgetElementsBookStorage
                .getClubBudgetElementsBookFilePath());
    }

    @Override
    public void saveClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook clubBudgetElementsBook, Path filePath)
            throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        clubBudgetElementsBookStorage.saveClubBudgetElementsBook(clubBudgetElementsBook, filePath);
    }


    @Override
    @Subscribe
    public void handleClubBudgetElementsBookChangedEvent(ClubBudgetElementsBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveClubBudgetElementsBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
