package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.person.TimeTable;

/**
 * Represents a storage for a single {@link seedu.address.model.person.TimeTable} object.
 */
public interface TimeTableStorage {

    /**
     * Returns the file path of the TimeTable data file.
     */
    Path getTimeTableFilePath();

    /**
     * Returns TimeTable data from storage.
     *   Returns {@code Optional.empty()} if storage (.ics) file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<TimeTable> readTimeTable() throws DataConversionException, IOException;

    /**
     * Saves the given {@link TimeTable} to the storage.
     * @param timeTable cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTimeTable(TimeTable timeTable) throws IOException;

}
