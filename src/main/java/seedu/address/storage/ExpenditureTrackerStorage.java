//@@author SHININGGGG
package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyExpenditureTracker;

/**
 * Represents a storage for {@link seedu.address.model.ExpenditureTracker}.
 */
public interface ExpenditureTrackerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getExpenditureTrackerFilePath();

    /**
     * Returns Expenditure data as a {@link ReadOnlyExpenditureTracker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyExpenditureTracker> readExpenditureTracker() throws DataConversionException, IOException;

    /**
     * @see #getExpenditureTrackerFilePath()
     */
    Optional<ReadOnlyExpenditureTracker> readExpenditureTracker(Path filePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyExpenditureTracker} to the storage.
     * @param expenditureTracker cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExpenditureTracker(ReadOnlyExpenditureTracker expenditureTracker) throws IOException;

    /**
     * @see #saveExpenditureTracker(ReadOnlyExpenditureTracker)
     */
    void saveExpenditureTracker(ReadOnlyExpenditureTracker expenditureTracker, Path filePath) throws IOException;

}
