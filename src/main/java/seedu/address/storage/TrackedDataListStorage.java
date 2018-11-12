package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackedDataList;

/**
 * Represents a storage for {@link seedu.address.model.WorkoutBook}.
 */
public interface TrackedDataListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTrackedDataListFilePath();

    /**
     * Returns Tracked data as a {@link ReadOnlyTrackedDataList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTrackedDataList> readTrackedDataList() throws DataConversionException, IOException;

    /**
     * @see #getTrackedDataListFilePath()
     */
    Optional<ReadOnlyTrackedDataList> readTrackedDataList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTrackedDataList} to the storage.
     * @param trackedData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTrackedDataList(ReadOnlyTrackedDataList trackedData) throws IOException;

    /**
     * @see #saveTrackedDataList(ReadOnlyTrackedDataList)
     */
    void saveTrackedDataList(ReadOnlyTrackedDataList trackedData, Path filePath) throws IOException;

}
