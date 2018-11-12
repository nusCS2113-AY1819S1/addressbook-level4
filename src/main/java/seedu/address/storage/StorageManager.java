package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TrackedDataChangedEvent;
import seedu.address.commons.events.model.TrackedDataListChangedEvent;
import seedu.address.commons.events.model.WorkoutBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackedData;
import seedu.address.model.ReadOnlyTrackedDataList;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of WorkoutBook data and Tracked Data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private WorkoutBookStorage workoutBookStorage;
    private TrackedDataListStorage trackedDataListStorage;
    private TrackedDataStorage trackedDataStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(WorkoutBookStorage workoutBookStorage, TrackedDataListStorage trackedDataListStorage,
                          TrackedDataStorage trackedDataStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.workoutBookStorage = workoutBookStorage;
        this.trackedDataListStorage = trackedDataListStorage;
        this.trackedDataStorage = trackedDataStorage;
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

    // ================ TrackedDataList methods ==============================

    @Override
    public Path getTrackedDataListFilePath() {
        return trackedDataListStorage.getTrackedDataListFilePath();
    }

    @Override
    public Optional<ReadOnlyTrackedDataList> readTrackedDataList() throws DataConversionException, IOException {
        return readTrackedDataList(trackedDataListStorage.getTrackedDataListFilePath());
    }

    @Override
    public Optional<ReadOnlyTrackedDataList> readTrackedDataList(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return trackedDataListStorage.readTrackedDataList(filePath);
    }

    @Override
    public void saveTrackedDataList(ReadOnlyTrackedDataList trackedDataList) throws IOException {
        saveTrackedDataList(trackedDataList, trackedDataListStorage.getTrackedDataListFilePath());
    }

    @Override
    public void saveTrackedDataList(ReadOnlyTrackedDataList trackedDataList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        trackedDataListStorage.saveTrackedDataList(trackedDataList, filePath);
    }

    @Override
    @Subscribe
    public void handleTrackedDataListChangedEvent(TrackedDataListChangedEvent trackedDataListChanged) {
        logger.info(LogsCenter.getEventHandlingLogMessage(trackedDataListChanged,
                "Tracked data list changed, saving to file"));
        try {
            saveTrackedDataList(trackedDataListChanged.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ TrackedData methods ==============================

    @Override
    public Path getTrackedDataFilePath() {
        return trackedDataStorage.getTrackedDataFilePath();
    }

    @Override
    public Optional<ReadOnlyTrackedData> readTrackedData() throws DataConversionException, IOException {
        return readTrackedData(trackedDataStorage.getTrackedDataFilePath());
    }

    @Override
    public Optional<ReadOnlyTrackedData> readTrackedData(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return trackedDataStorage.readTrackedData(filePath);
    }

    @Override
    public void saveTrackedData(ReadOnlyTrackedData trackedData) throws IOException {
        saveTrackedData(trackedData, trackedDataStorage.getTrackedDataFilePath());
    }

    @Override
    public void saveTrackedData(ReadOnlyTrackedData trackedData, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        trackedDataStorage.saveTrackedData(trackedData, filePath);
    }

    @Override
    @Subscribe
    public void handleTrackedDataChangedEvent(TrackedDataChangedEvent trackedDataChanged) {
        logger.info(LogsCenter.getEventHandlingLogMessage(trackedDataChanged,
                "Tracked data changed, saving to file"));
        try {
            saveTrackedData(trackedDataChanged.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
