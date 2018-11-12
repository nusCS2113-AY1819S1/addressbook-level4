package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.BookInventoryChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBookInventory;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of BookInventory data in local storage.
 */
public class InventoryStorageManager extends ComponentManager implements InventoryStorage {

    private static final Logger logger = LogsCenter.getLogger(InventoryStorageManager.class);
    private BookInventoryStorage bookInventoryStorage;
    private UserPrefsStorage userPrefsStorage;


    public InventoryStorageManager(BookInventoryStorage bookInventoryStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.bookInventoryStorage = bookInventoryStorage;
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


    // ================ BookInventory methods ==============================

    @Override
    public Path getBookInventoryFilePath() {
        return bookInventoryStorage.getBookInventoryFilePath();
    }

    @Override
    public Optional<ReadOnlyBookInventory> readBookInventory() throws DataConversionException, IOException {
        return readBookInventory(bookInventoryStorage.getBookInventoryFilePath());
    }

    @Override
    public Optional<ReadOnlyBookInventory> readBookInventory(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return bookInventoryStorage.readBookInventory(filePath);
    }

    @Override
    public void saveBookInventory(ReadOnlyBookInventory bookInventory) throws IOException {
        saveBookInventory(bookInventory, bookInventoryStorage.getBookInventoryFilePath());
    }

    @Override
    public void saveBookInventory(ReadOnlyBookInventory bookInventory, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        bookInventoryStorage.saveBookInventory(bookInventory, filePath);
    }

    @Override
    public void backupInventoryBook(ReadOnlyBookInventory addressBook) throws IOException {
        bookInventoryStorage.backupInventoryBook(addressBook);
    }


    @Override
    @Subscribe
    public void handleBookInventoryChangedEvent(BookInventoryChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveBookInventory(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
