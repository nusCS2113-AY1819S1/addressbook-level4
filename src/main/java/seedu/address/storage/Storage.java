package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.WorkoutBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends WorkoutBookStorage, UserPrefsStorage {

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

    /**
     * Saves the current version of the Workout Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleWorkoutBookChangedEvent(WorkoutBookChangedEvent abce);
}
