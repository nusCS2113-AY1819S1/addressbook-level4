package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.EventManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyEventManager;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of EventManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EventManagerStorage eventManagerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(EventManagerStorage eventManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.eventManagerStorage = eventManagerStorage;
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


    // ================ EventManager methods ==============================

    @Override
    public Path getEventManagerFilePath() {
        return eventManagerStorage.getEventManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyEventManager> readEventManager() throws DataConversionException, IOException {
        return readEventManager(eventManagerStorage.getEventManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyEventManager> readEventManager(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventManagerStorage.readEventManager(filePath);
    }

    @Override
    public void saveEventManager(ReadOnlyEventManager eventManager) throws IOException {
        saveEventManager(eventManager, eventManagerStorage.getEventManagerFilePath());
    }

    @Override
    public void saveEventManager(ReadOnlyEventManager eventManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventManagerStorage.saveEventManager(eventManager, filePath);
    }


    @Override
    @Subscribe
    public void handleEManagerChangedEvent(EventManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEventManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
