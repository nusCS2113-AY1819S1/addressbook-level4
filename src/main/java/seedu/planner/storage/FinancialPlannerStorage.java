package seedu.planner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.Record;
import seedu.planner.model.record.UniqueRecordList;
import seedu.planner.model.summary.SummaryMap;

/**
 * Represents a storage for {@link FinancialPlanner}.
 */
public interface FinancialPlannerStorage {

    /**
     * Returns the file path of the data file of the record list storage.
     */
    Path getRecordListFilePath();

    /**
     * Returns the file path of the data file for SummaryMap storage
     */
    Path getSummaryMapFilePath();

    // ================ Financial Planner storage methods ===========================
    /**
     * Returns FinancialPlanner data as a {@link ReadOnlyFinancialPlanner}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFinancialPlanner> readFinancialPlanner() throws DataConversionException, IOException;

    /**
     * @see #getRecordListFilePath() and #getSummaryMapFilePath
     */
    Optional<ReadOnlyFinancialPlanner> readFinancialPlanner(Path recordListFilePath,
                                                            Path summaryListFilePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFinancialPlanner} to the storage.
     * @param financialPlanner cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner) throws IOException;

    /**
     * @see #saveFinancialPlanner(ReadOnlyFinancialPlanner)
     */
    void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner, Path recordListFilePath,
                              Path summaryMapFilePath) throws IOException;

    // ================ Record List storage methods ===========================
    /**
     * Returns UniqueRecordList data as a {@link UniqueRecordList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UniqueRecordList> readRecordList() throws DataConversionException, IOException;

    /**
     * @see #getRecordListFilePath()
     */
    Optional<UniqueRecordList> readRecordList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ObservableList<Record>} to the storage.
     * @param recordList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRecordList(ObservableList<Record> recordList) throws IOException;

    /**
     * @see #saveRecordList(ObservableList<Record>)
     */
    void saveRecordList(ObservableList<Record> recordList, Path filePath) throws IOException;

    // ================ Summary Map storage methods ===========================

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

    // ================ Limit List storage methods ===========================
    //TODO: @Oscar put your limit functions here

}
