package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackedData;

/**
 * Represents a storage for {@link seedu.address.model.WorkoutBook}.
 */
public interface TrackedDataStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTrackedDataFilePath();

    /**
     * Returns Tracked data as a {@link ReadOnlyTrackedData}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTrackedData> readTrackedData() throws DataConversionException, IOException;

    /**
     * @see #getTrackedDataFilePath()
     */
    Optional<ReadOnlyTrackedData> readTrackedData(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTrackedData} to the storage.
     * @param trackedData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTrackedData(ReadOnlyTrackedData trackedData) throws IOException;

    /**
     * @see #saveTrackedData(ReadOnlyTrackedData)
     */
    void saveTrackedData(ReadOnlyTrackedData trackedData, Path filePath) throws IOException;

}
