package seedu.planner.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.commons.util.FileUtil;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.Record;
import seedu.planner.model.record.UniqueRecordList;
import seedu.planner.model.summary.SummaryMap;
import seedu.planner.storage.xml_jaxb.XmlSerializableFinancialPlanner;
import seedu.planner.storage.xml_jaxb.XmlSerializableSummaryMap;

/**
 * A class to access FinancialPlanner data stored as an xml file on the hard disk.
 */
public class XmlFinancialPlannerStorage implements FinancialPlannerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlFinancialPlannerStorage.class);

    private Path recordListFilePath;
    private Path summaryMapFilePath;

    public XmlFinancialPlannerStorage(Path recordListFilePath, Path summaryMapFilePath) {
        this.recordListFilePath = recordListFilePath;
        this.summaryMapFilePath = summaryMapFilePath;
    }

    public Path getRecordListFilePath() { return recordListFilePath; }

    public Path getSummaryMapFilePath() { return summaryMapFilePath; }

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
        return null;
    }

    @Override
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner) throws IOException{
        saveFinancialPlanner(financialPlanner, recordListFilePath, summaryMapFilePath);
    }

    @Override
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner, Path recordListFilePath,
                                     Path summaryMapFilePath) throws IOException {
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
    public void saveSummaryMap(SummaryMap summaryMap) throws IOException {
        saveSummaryMap(summaryMap, summaryMapFilePath);
    }

    /**
     * Similar to {@link #saveSummaryMap(SummaryMap)}}
     * @param filePath location of the data. Cannot be null
     */
    public void saveSummaryMap(SummaryMap summaryMap, Path filePath) throws IOException {
        requireNonNull(summaryMap);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableSummaryMap(summaryMap));
    }

    // ======================================= Limit Storage methods ==============================

    /**
     * save the updated limit list into the special xml file
     * @param financialPlanner
     * @param filePath
     * @throws IOException
     */
    /*public void saveLimitList (ReadOnlyFinancialPlanner financialPlanner, Path filePath) throws IOException {
        requireNonNull(financialPlanner);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableFinancialPlanner(financialPlanner));
    }*/
}
