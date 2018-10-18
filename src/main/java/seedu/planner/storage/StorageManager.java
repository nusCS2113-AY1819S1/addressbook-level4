package seedu.planner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.planner.commons.core.ComponentManager;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.model.FinancialPlannerChangedEvent;
import seedu.planner.commons.events.model.LimitListChangedEvent;
import seedu.planner.commons.events.model.SummaryMapChangedEvent;
import seedu.planner.commons.events.storage.DataSavingExceptionEvent;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.DateBasedLimitList;
import seedu.planner.model.record.UniqueRecordList;
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


    // =============== Methods to get file paths ==============================

    @Override
    public Path getRecordListFilePath() {
        return financialPlannerStorage.getRecordListFilePath();
    }

    @Override
    public Path getSummaryMapFilePath() {
        return financialPlannerStorage.getSummaryMapFilePath();
    }

    //TODO: @ Oscar add your get filepath function here

    // ================ Financial Planner methods ===========================

    @Override
    public Optional<ReadOnlyFinancialPlanner> readFinancialPlanner() throws DataConversionException, IOException {
        return readFinancialPlanner(financialPlannerStorage.getRecordListFilePath(),
                financialPlannerStorage.getSummaryMapFilePath());
    }

    @Override
    public Optional<ReadOnlyFinancialPlanner> readFinancialPlanner(Path recordListFilePath,
                                                                   Path summaryMapFilePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + recordListFilePath + " and "
                 + summaryMapFilePath);
        return financialPlannerStorage.readFinancialPlanner(recordListFilePath, summaryMapFilePath);
    }

    @Override
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner) throws IOException {
        saveFinancialPlanner(financialPlanner, financialPlannerStorage.getRecordListFilePath(),
                financialPlannerStorage.getSummaryMapFilePath());
    }

    @Override
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner, Path recordListFilePath,
                                     Path summaryMapFilePath) throws IOException {
        logger.fine("Attempting to write to data file: " + recordListFilePath + " and "
                + summaryMapFilePath);
        financialPlannerStorage.saveFinancialPlanner(financialPlanner, recordListFilePath, summaryMapFilePath);
    }

    // ================ Record List methods ==============================

    @Override
    public Optional<UniqueRecordList> readRecordList() throws DataConversionException, IOException {
        return readRecordList(financialPlannerStorage.getRecordListFilePath());
    }

    @Override
    public Optional<UniqueRecordList> readRecordList(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return financialPlannerStorage.readRecordList(filePath);
    }

    @Override
    public void saveRecordList(ReadOnlyFinancialPlanner financialPlanner) throws IOException {
        saveRecordList(financialPlanner, financialPlannerStorage.getRecordListFilePath());
    }

    @Override
    public void saveRecordList(ReadOnlyFinancialPlanner financialPlanner, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        financialPlannerStorage.saveRecordList(financialPlanner, filePath);
    }

    // ================ SummaryMap storage methods ==============================

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
    public void saveSummaryMap(ReadOnlyFinancialPlanner financialPlanner) throws IOException {
        saveSummaryMap(financialPlanner, financialPlannerStorage.getSummaryMapFilePath());
    }

    @Override
    public void saveSummaryMap(ReadOnlyFinancialPlanner financialPlanner, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        financialPlannerStorage.saveSummaryMap(financialPlanner, filePath);
    }

    // ================================== Event management methods ================================

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

    @Override
    @Subscribe
    public void handleLimitListChangedEvent(LimitListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveLimitList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ LimitList storage methods ==============================

    @Override
    public Path getLimitListFilePath() {
        return financialPlannerStorage.getLimitListFilePath();
    }

    @Override
    public Optional<DateBasedLimitList> readLimitList() throws DataConversionException, IOException {
        return readLimitList(financialPlannerStorage.getLimitListFilePath());
    }

    @Override
    public Optional<DateBasedLimitList> readLimitList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return financialPlannerStorage.readLimitList(filePath);
    }

    @Override
    public void saveLimitList(ReadOnlyFinancialPlanner limitList) throws IOException {
        saveLimitList(limitList, financialPlannerStorage.getLimitListFilePath());
    }

    @Override
    public void saveLimitList(ReadOnlyFinancialPlanner limitList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        financialPlannerStorage.saveLimitList(limitList, filePath);
    }





}
