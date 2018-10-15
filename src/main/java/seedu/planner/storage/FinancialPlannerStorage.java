package seedu.planner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.ReadOnlyFinancialPlanner;

/**
 * Represents a storage for {@link FinancialPlanner}.
 */
public interface FinancialPlannerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFinancialPlannerFilePath();



    /**
     * Returns FinancialPlanner data as a {@link ReadOnlyFinancialPlanner}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFinancialPlanner> readFinancialPlanner() throws DataConversionException, IOException;

    /**
     * @see #getFinancialPlannerFilePath()
     */
    Optional<ReadOnlyFinancialPlanner> readFinancialPlanner(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFinancialPlanner} to the storage.
     * @param financialPlanner cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRecordList(ReadOnlyFinancialPlanner financialPlanner) throws IOException;

    /**
     * @see #saveRecordList(ReadOnlyFinancialPlanner)
     */
    void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner, Path filePath) throws IOException;

}
