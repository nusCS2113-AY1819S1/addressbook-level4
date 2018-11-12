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
import seedu.address.model.ReadOnlyTrackedDataList;

/**
 * A class to access the list of data being tracked, stored as an xml file on the hard disk.
 */
public class XmlTrackedDataListStorage implements TrackedDataListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTrackedDataListStorage.class);

    private Path filePath;

    public XmlTrackedDataListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTrackedDataListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTrackedDataList> readTrackedDataList() throws DataConversionException, IOException {
        return readTrackedDataList(filePath);
    }

    /**
     * Similar to {@link #readTrackedDataList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTrackedDataList> readTrackedDataList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Tracked data file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableTrackedDataList xmlTrackedDataList = XmlFileStorage.loadTrackedDataListFromSaveFile(filePath);
        try {
            return Optional.of(xmlTrackedDataList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTrackedDataList(ReadOnlyTrackedDataList trackedDataList) throws IOException {
        saveTrackedDataList(trackedDataList, filePath);
    }

    /**
     * Similar to {@link #saveTrackedDataList(ReadOnlyTrackedDataList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTrackedDataList(ReadOnlyTrackedDataList trackedDataList, Path filePath) throws IOException {
        requireNonNull(trackedDataList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableTrackedDataList(trackedDataList));
    }

}
