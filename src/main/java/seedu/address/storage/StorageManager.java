package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.WorkoutBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of WorkoutBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private WorkoutBookStorage workoutBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(WorkoutBookStorage workoutBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.workoutBookStorage = workoutBookStorage;
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


    // ================ WorkoutBook methods ==============================

    @Override
    public Path getWorkoutBookFilePath() {
        return workoutBookStorage.getWorkoutBookFilePath();
    }

    @Override
    public Optional<ReadOnlyWorkoutBook> readWorkoutBook() throws DataConversionException, IOException {
        return readWorkoutBook(workoutBookStorage.getWorkoutBookFilePath());
    }

    @Override
    public Optional<ReadOnlyWorkoutBook> readWorkoutBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return workoutBookStorage.readWorkoutBook(filePath);
    }

    @Override
    public void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook) throws IOException {
        saveWorkoutBook(workoutBook, workoutBookStorage.getWorkoutBookFilePath());
    }

    @Override
    public void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        workoutBookStorage.saveWorkoutBook(workoutBook, filePath);
    }


    @Override
    @Subscribe
    public void handleWorkoutBookChangedEvent(WorkoutBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveWorkoutBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
