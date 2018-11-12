package seedu.divelog.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.divelog.commons.exceptions.DataConversionException;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.ReadOnlyDiveLog;

/**
 * Represents a storage for {@link DiveLog}.
 */
public interface DiveLogStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDiveLogFilePath();

    /**
     * Returns DiveLog data as a {@link ReadOnlyDiveLog}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDiveLog> readDiveLog() throws DataConversionException, IOException;

    /**
     * @see #getDiveLogFilePath()
     */
    Optional<ReadOnlyDiveLog> readDiveLog(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDiveLog} to the storage.
     * @param diveLog cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDiveLog(ReadOnlyDiveLog diveLog) throws IOException;

    /**
     * @see #saveDiveLog(ReadOnlyDiveLog)
     */
    void saveDiveLog(ReadOnlyDiveLog diveLog, Path filePath) throws IOException;

}
