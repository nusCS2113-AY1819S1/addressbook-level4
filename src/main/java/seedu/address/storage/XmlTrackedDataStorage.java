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
import seedu.address.model.ReadOnlyTrackedData;

/**
 * A class to access Tracked data stored as an xml file on the hard disk.
 */
public class XmlTrackedDataStorage implements TrackedDataStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTrackedDataStorage.class);

    private Path filePath;

    public XmlTrackedDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTrackedDataFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTrackedData> readTrackedData() throws DataConversionException, IOException {
        return readTrackedData(filePath);
    }

    /**
     * Similar to {@link #readTrackedData()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTrackedData> readTrackedData(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Tracked data file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableTrackedData xmlTrackedData = XmlFileStorage.loadTrackedDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlTrackedData.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTrackedData(ReadOnlyTrackedData trackedData) throws IOException {
        saveTrackedData(trackedData, filePath);
    }

    /**
     * Similar to {@link #saveTrackedData(ReadOnlyTrackedData)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTrackedData(ReadOnlyTrackedData trackedData, Path filePath) throws IOException {
        requireNonNull(trackedData);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableTrackedData(trackedData));
    }

}
