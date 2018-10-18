package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyExpenditureTracker;

/**
 * A class to access ExpenditureTracker data stored as an xml file on the hard disk.
 */
public class XmlExpenditureTrackerStorage implements ExpenditureTrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlExpenditureTrackerStorage.class);

    private Path filePath;

    public XmlExpenditureTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getExpenditureTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyExpenditureTracker> readExpenditureTracker() throws DataConversionException, IOException {
        return readExpenditureTracker(filePath);
    }

    /**
     * Similar to {@link #readExpenditureTracker()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyExpenditureTracker> readExpenditureTracker(Path filePath)
            throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ExpenditureTracker file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableExpenditureTracker xmlExpenditureTracker =
                XmlFileStorage.loadExpenditureTrackerDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlExpenditureTracker.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveExpenditureTracker(ReadOnlyExpenditureTracker expenditureTracker) throws IOException {
        saveExpenditureTracker(expenditureTracker, filePath);
    }

    /**
     * Similar to {@link #saveExpenditureTracker(ReadOnlyExpenditureTracker)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveExpenditureTracker(ReadOnlyExpenditureTracker expenditureTracker, Path filePath) throws IOException {
        requireNonNull(expenditureTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveExpenditureTrackerDataToFile(filePath, new XmlSerializableExpenditureTracker(expenditureTracker));
    }

}
