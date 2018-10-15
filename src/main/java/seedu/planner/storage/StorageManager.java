package seedu.planner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.planner.commons.core.ComponentManager;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.model.FinancialPlannerChangedEvent;
import seedu.planner.commons.events.model.SummaryMapChangedEvent;
import seedu.planner.commons.events.storage.DataSavingExceptionEvent;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.summary.SummaryMap;

/**
 * Manages storage of FinancialPlanner data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FinancialPlannerStorage financialPlannerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(FinancialPlannerStorage financialPlannerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.financialPlannerStorage = financialPlannerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ FinancialPlanner methods ==============================

    @Override
    public Path getFinancialPlannerFilePath() {
        return financialPlannerStorage.getFinancialPlannerFilePath();
    }

    @Override
    public Optional<ReadOnlyFinancialPlanner> readFinancialPlanner() throws DataConversionException, IOException {
        return readFinancialPlanner(financialPlannerStorage.getFinancialPlannerFilePath());
    }

    @Override
    public Optional<ReadOnlyFinancialPlanner> readFinancialPlanner(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return financialPlannerStorage.readFinancialPlanner(filePath);
    }

    @Override
    public void saveRecordList(ReadOnlyFinancialPlanner financialPlanner) throws IOException {
        saveFinancialPlanner(financialPlanner, financialPlannerStorage.getFinancialPlannerFilePath());
    }

    //public void

    @Override
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        financialPlannerStorage.saveFinancialPlanner(financialPlanner, filePath);
    }

    @Override
    @Subscribe
    public void handleFinancialPlannerChangedEvent(FinancialPlannerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveRecordList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    //@Override
   // @Subscribe
    public void handleLimitListChangedEvent (FinancialPlannerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveRecordList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ SummaryMap storage methods ==============================

    @Override
    public Path getSummaryMapFilePath() {
        return financialPlannerStorage.getSummaryMapFilePath();
    }

    @Override
    public Optional<SummaryMap> readSummaryMap() throws DataConversionException, IOException {
        return readSummaryMap(financialPlannerStorage.getSummaryMapFilePath());
    }

    @Override
    public Optional<SummaryMap> readSummaryMap(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return financialPlannerStorage.readSummaryMap(filePath);
    }

    @Override
    public void saveSummaryMap(SummaryMap summaryMap) throws IOException {
        saveSummaryMap(summaryMap, financialPlannerStorage.getSummaryMapFilePath());
    }

    @Override
    public void saveSummaryMap(SummaryMap summaryMap, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        financialPlannerStorage.saveSummaryMap(summaryMap, filePath);
    }

    @Override
    @Subscribe
    public void handleSummaryMapChangedEvent(SummaryMapChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveSummaryMap(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
