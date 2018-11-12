package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
 * API of the Storage component
 */
public interface Storage extends WorkoutBookStorage, UserPrefsStorage, TrackedDataStorage, TrackedDataListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getWorkoutBookFilePath();

    @Override
    Optional<ReadOnlyWorkoutBook> readWorkoutBook() throws DataConversionException, IOException;

    @Override
    void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook) throws IOException;

    @Override
    Optional<ReadOnlyTrackedDataList> readTrackedDataList() throws DataConversionException, IOException;

    @Override
    void saveTrackedDataList(ReadOnlyTrackedDataList trackedDataList) throws IOException;

    @Override
    Optional<ReadOnlyTrackedData> readTrackedData() throws DataConversionException, IOException;

    @Override
    void saveTrackedData(ReadOnlyTrackedData trackedData) throws IOException;

    /**
     * Saves the current version of the Workout Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleWorkoutBookChangedEvent(WorkoutBookChangedEvent abce);

    /**
     * Saves the current version of the tracked data list to the hard disk.
     *   Creates the data file(s) if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTrackedDataListChangedEvent(TrackedDataListChangedEvent trackedDataListChanged);

    /**
     * Saves the current version of the tracked data list to the hard disk.
     *   Creates the data file(s) if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTrackedDataChangedEvent(TrackedDataChangedEvent trackedDataChanged);
}
