package seedu.planner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.planner.commons.events.model.FinancialPlannerChangedEvent;
import seedu.planner.commons.events.model.SummaryMapChangedEvent;
import seedu.planner.commons.events.storage.DataSavingExceptionEvent;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.summary.SummaryMap;

/**
 * API of the Storage component
 */
public interface Storage extends FinancialPlannerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    // =================================== Financial Planner methods =========================================

    @Override
    Path getFinancialPlannerFilePath();

    @Override
    Optional<ReadOnlyFinancialPlanner> readFinancialPlanner() throws DataConversionException, IOException;

    @Override
    void saveRecordList(ReadOnlyFinancialPlanner financialPlanner) throws IOException;

    /**
     * Saves the current version of the Financial Planner to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleFinancialPlannerChangedEvent(FinancialPlannerChangedEvent event);

    // ================================== Summary Map storage methods ========================================

    @Override
    Optional<SummaryMap> readSummaryMap() throws DataConversionException, IOException;

    @Override
    void saveSummaryMap(SummaryMap summaryMap) throws IOException;

    /**
     * Saves the summary map in the current version of Financial Planner to the had disk.
     *   Creates the date file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSummaryMapChangedEvent(SummaryMapChangedEvent event);
}
