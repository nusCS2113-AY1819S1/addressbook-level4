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
import seedu.planner.storage.xmljaxb.XmlSerializableFinancialPlanner;

/**
 * A class to access FinancialPlanner data stored as an xml file on the hard disk.
 */
public class XmlFinancialPlannerStorage implements FinancialPlannerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlFinancialPlannerStorage.class);

    private Path recordListFilePath;
    private Path limitListFilePath;

    public XmlFinancialPlannerStorage(Path recordListFilePath, Path limitListFilePath) {
        this.recordListFilePath = recordListFilePath;
        this.limitListFilePath = limitListFilePath;
    }

    public Path getRecordListFilePath() {
        return recordListFilePath;
    }

    public Path getLimitListFilePath() {
        return limitListFilePath;
    }

    // ===================== Financial Planner Storage methods ================================

    @Override
    public Optional<ReadOnlyFinancialPlanner> readFinancialPlanner() throws DataConversionException, IOException {
        return readFinancialPlanner(recordListFilePath);
    }

    @Override
    public Optional<ReadOnlyFinancialPlanner> readFinancialPlanner(Path recordListFilePath)
            throws DataConversionException, IOException {
        requireNonNull(recordListFilePath);

        Optional<ReadOnlyFinancialPlanner> financialPlannerOptional = Optional.empty();
        Optional<UniqueRecordList> recordListOptional = readRecordList(recordListFilePath);
        if (recordListOptional.isPresent()) {
            FinancialPlanner financialPlanner = new FinancialPlanner();
            financialPlanner.resetData(recordListOptional.get());
            financialPlannerOptional = Optional.of(financialPlanner);
        }
        return financialPlannerOptional;
    }

    @Override
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner) throws IOException {
        saveFinancialPlanner(financialPlanner, recordListFilePath);
    }

    @Override
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner, Path recordListFilePath)
            throws IOException {
        saveRecordList(financialPlanner, recordListFilePath);
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
