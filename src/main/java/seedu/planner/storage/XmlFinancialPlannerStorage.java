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
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.summary.SummaryMap;
import seedu.planner.storage.xml_jaxb.XmlSerializableFinancialPlanner;

/**
 * A class to access FinancialPlanner data stored as an xml file on the hard disk.
 */
public class XmlFinancialPlannerStorage implements FinancialPlannerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlFinancialPlannerStorage.class);

    private Path financialPlannerFilePath;
    private Path summaryMapFilePath;

    public XmlFinancialPlannerStorage(Path financialPlannerFilePath, Path summaryMapFilePath) {
        this.financialPlannerFilePath = financialPlannerFilePath;
        this.summaryMapFilePath = summaryMapFilePath;
    }

    public Path getFinancialPlannerFilePath() {
        return financialPlannerFilePath;
    }

    public Path getSummaryMapFilePath() { return summaryMapFilePath; }

    // ===================== Record List Storage methods ======================================

    @Override
    public Optional<ReadOnlyFinancialPlanner> readFinancialPlanner() throws DataConversionException, IOException {
        return readFinancialPlanner(financialPlannerFilePath);
    }

    /**
     * Similar to {@link #readFinancialPlanner()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFinancialPlanner> readFinancialPlanner(Path filePath) throws DataConversionException,
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
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner) throws IOException {
        saveFinancialPlanner(financialPlanner, financialPlannerFilePath);
    }

    /**
     * Similar to {@link #saveFinancialPlanner(ReadOnlyFinancialPlanner)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner, Path filePath) throws IOException {
        requireNonNull(financialPlanner);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableFinancialPlanner(financialPlanner));
    }

    // ===================== Summary Map Storage methods ======================================

    @Override
    public Optional<SummaryMap> readSummaryMap() throws DataConversionException, IOException {
        return null;
    }

    /**
     * Similar to {@link #readSummaryMap()} ()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<SummaryMap> readSummaryMap(Path filePath) throws DataConversionException,
            FileNotFoundException {
        return null;
    }

    @Override
    public void saveSummaryMap(SummaryMap summaryMap) throws IOException {
    }

    /**
     * Similar to {@link #saveSummaryMap(SummaryMap)}}
     * @param filePath location of the data. Cannot be null
     */
    public void saveSummaryMap(SummaryMap summaryMap, Path filePath) throws IOException {
    }
}
