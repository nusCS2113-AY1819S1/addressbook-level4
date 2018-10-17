package seedu.planner.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.commons.util.FileUtil;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.DateBasedLimitList;
import seedu.planner.model.record.UniqueRecordList;
import seedu.planner.model.summary.SummaryMap;
import seedu.planner.storage.xmljaxb.XmlSerializableFinancialPlanner;
import seedu.planner.storage.xmljaxb.XmlSerializableSummaryMap;

/**
 * A class to access FinancialPlanner data stored as an xml file on the hard disk.
 */
public class XmlFinancialPlannerStorage implements FinancialPlannerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlFinancialPlannerStorage.class);

    private Path recordListFilePath;
    private Path summaryMapFilePath;
    private Path limitListFilePath;

    public XmlFinancialPlannerStorage(Path recordListFilePath, Path summaryMapFilePath, Path limitListFilePath) {
        this.recordListFilePath = recordListFilePath;
        this.summaryMapFilePath = summaryMapFilePath;
        this.limitListFilePath = limitListFilePath;
    }

    public Path getRecordListFilePath() {
        return recordListFilePath;
    }

    public Path getSummaryMapFilePath() {
        return summaryMapFilePath;
    }

    public Path getLimitListFilePath() {
        return limitListFilePath;
    }

    // ===================== Financial Planner Storage methods ================================

    @Override
    public Optional<ReadOnlyFinancialPlanner> readFinancialPlanner() throws DataConversionException, IOException {
        return readFinancialPlanner(recordListFilePath, summaryMapFilePath);
    }

    @Override
    public Optional<ReadOnlyFinancialPlanner> readFinancialPlanner(Path recordListFilePath,
                                                                   Path summaryMapFilePath)
            throws DataConversionException, IOException {
        requireNonNull(recordListFilePath);
        requireNonNull(summaryMapFilePath);

        Optional<ReadOnlyFinancialPlanner> financialPlannerOptional = Optional.empty();
        Optional<UniqueRecordList> recordListOptional = readRecordList(recordListFilePath);
        Optional<SummaryMap> summaryMapOptional = readSummaryMap(summaryMapFilePath);
        if (recordListOptional.isPresent() && summaryMapOptional.isPresent()) {
            FinancialPlanner financialPlanner = new FinancialPlanner();
            financialPlanner.resetData(recordListOptional.get(), summaryMapOptional.get());
            financialPlannerOptional = Optional.of(financialPlanner);
        }
        return financialPlannerOptional;
    }

    @Override
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner) throws IOException {
        saveFinancialPlanner(financialPlanner, recordListFilePath, summaryMapFilePath);
    }

    @Override
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner, Path recordListFilePath,
                                     Path summaryMapFilePath) throws IOException {
        saveRecordList(financialPlanner, recordListFilePath);
        saveSummaryMap(financialPlanner, summaryMapFilePath);
    }

    // ===================== Record List Storage methods ======================================

    @Override
    public Optional<UniqueRecordList> readRecordList() throws DataConversionException, IOException {
        return readRecordList(recordListFilePath);
    }

    /**
     * Similar to {@link #readRecordList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<UniqueRecordList> readRecordList(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("FinancialPlanner file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableFinancialPlanner xmlFinancialPlanner = XmlFileStorage.loadDataFromSaveFile(filePath,
                XmlSerializableFinancialPlanner.class);
        try {
            return Optional.of(xmlFinancialPlanner.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRecordList(ReadOnlyFinancialPlanner financialPlanner) throws IOException {
        saveRecordList(financialPlanner, recordListFilePath);
    }

    /**
     * Similar to {@link FinancialPlannerStorage#saveRecordList(ReadOnlyFinancialPlanner)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveRecordList(ReadOnlyFinancialPlanner financialPlanner, Path filePath) throws IOException {
        requireNonNull(financialPlanner);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableFinancialPlanner(financialPlanner));
    }

    // ===================== Summary Map Storage methods ======================================

    @Override
    public Optional<SummaryMap> readSummaryMap() throws DataConversionException, IOException {
        return readSummaryMap(summaryMapFilePath);
    }

    /**
     * Similar to {@link #readSummaryMap()} ()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<SummaryMap> readSummaryMap(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("SummaryMap file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableSummaryMap xmlSummaryMap = XmlFileStorage.loadDataFromSaveFile(filePath,
                XmlSerializableSummaryMap.class);

        try {
            return Optional.of(xmlSummaryMap.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSummaryMap(ReadOnlyFinancialPlanner financialPlanner) throws IOException {
        saveSummaryMap(financialPlanner, summaryMapFilePath);
    }

    /**
     * Similar to {@link #saveSummaryMap(ReadOnlyFinancialPlanner)}}
     * @param filePath location of the data. Cannot be null
     */
    public void saveSummaryMap(ReadOnlyFinancialPlanner financialPlanner, Path filePath) throws IOException {
        requireNonNull(financialPlanner);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableSummaryMap(financialPlanner));
    }

    // ======================================= Limit Storage methods ==============================

    @Override
    public Optional<DateBasedLimitList> readLimitList() throws DataConversionException, IOException {
        return readLimitList(limitListFilePath);
    }

    @Override
    public Optional<DateBasedLimitList> readLimitList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("The LimitList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableLimitList xmlLimitList = XmlFileStorage.loadDataFromSaveFile(filePath,
                XmlSerializableLimitList.class);
        try {
            return Optional.of(xmlLimitList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveLimitList(ReadOnlyFinancialPlanner limitList) throws IOException {
        saveLimitList(limitList, limitListFilePath);
    }

    /**
     * Similar to {@link FinancialPlannerStorage#saveRecordList(ReadOnlyFinancialPlanner)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveLimitList(ReadOnlyFinancialPlanner limitList, Path filePath) throws IOException {
        requireNonNull(limitList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableLimitList(limitList));
    }
}
