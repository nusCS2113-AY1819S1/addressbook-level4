package seedu.divelog.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.divelog.commons.core.ComponentManager;
import seedu.divelog.commons.core.LogsCenter;
import seedu.divelog.commons.events.model.DiveLogChangedEvent;
import seedu.divelog.commons.events.storage.DataSavingExceptionEvent;
import seedu.divelog.commons.exceptions.DataConversionException;
import seedu.divelog.model.ReadOnlyDiveLog;
import seedu.divelog.model.UserPrefs;

/**
 * Manages storage of DiveLog data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private DiveLogStorage diveLogStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(DiveLogStorage diveLogStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.diveLogStorage = diveLogStorage;
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


    // ================ DiveLog methods ==============================

    @Override
    public Path getDiveLogFilePath() {
        return diveLogStorage.getDiveLogFilePath();
    }

    @Override
    public Optional<ReadOnlyDiveLog> readDiveLog() throws DataConversionException, IOException {
        return readDiveLog(diveLogStorage.getDiveLogFilePath());
    }

    @Override
    public Optional<ReadOnlyDiveLog> readDiveLog(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return diveLogStorage.readDiveLog(filePath);
    }

    @Override
    public void saveDiveLog(ReadOnlyDiveLog diveLog) throws IOException {
        saveDiveLog(diveLog, diveLogStorage.getDiveLogFilePath());
    }

    @Override
    public void saveDiveLog(ReadOnlyDiveLog diveLog, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        diveLogStorage.saveDiveLog(diveLog, filePath);
    }


    @Override
    @Subscribe
    public void handleDiveLogChangedEvent(DiveLogChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveDiveLog(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
