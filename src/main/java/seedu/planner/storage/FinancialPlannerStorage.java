package seedu.planner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.DateBasedLimitList;
import seedu.planner.model.summary.SummaryMap;

/**
 * Represents a storage for {@link FinancialPlanner}.
 */
public interface FinancialPlannerStorage {

    /**
     * Returns the file path of the data file of the record list storage.
     */
    Path getFinancialPlannerFilePath();

    /**
     * Returns the file path for the data file of the limit list storage.
     */
    Path getLimitListFilePath();

    /**
     * Returns the file path of the data file for SummaryMap storage
     */
    Path getSummaryMapFilePath();

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
     * Returns limitlist data as a limitlist.
     * @return
     * @throws DataConversionException
     * @throws IOException
     */
    Optional<DateBasedLimitList> readLimitList() throws DataConversionException, IOException;

    /**
     * see previous one
     */
    Optional<DateBasedLimitList> readLimitList(Path filePath) throws DataConversionException, IOException;

    /**
     * save the given limitlist to the storage.
     * @param limitList
     * @throws IOException
     */
    void saveLimitList(ReadOnlyFinancialPlanner limitList) throws IOException;

    /**
     * @see #saveLimitList(ReadOnlyFinancialPlanner, Path)
     */
    void saveLimitList(ReadOnlyFinancialPlanner limitList, Path filePath) throws IOException;


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

    /**
     * Returns SummaryMap data as a {@link SummaryMap}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem reading from the storage.
     */
    Optional<SummaryMap> readSummaryMap() throws DataConversionException, IOException;

    /**
     * @see #getSummaryMapFilePath()
     */
    Optional<SummaryMap> readSummaryMap(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link SummaryMap} to the storage.
     * @param summaryMap cannot be null
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSummaryMap(SummaryMap summaryMap) throws IOException;

    /**
     * @see #saveSummaryMap(SummaryMap)
     */
    void saveSummaryMap(SummaryMap summaryMap, Path filePath) throws IOException;

}
