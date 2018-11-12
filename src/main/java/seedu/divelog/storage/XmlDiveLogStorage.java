package seedu.divelog.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.divelog.commons.core.LogsCenter;
import seedu.divelog.commons.exceptions.DataConversionException;
import seedu.divelog.commons.exceptions.IllegalValueException;
import seedu.divelog.commons.util.FileUtil;
import seedu.divelog.model.ReadOnlyDiveLog;

/**
 * A class to access DiveLog data stored as an xml file on the hard disk.
 */
public class XmlDiveLogStorage implements DiveLogStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlDiveLogStorage.class);

    private Path filePath;

    public XmlDiveLogStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDiveLogFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDiveLog> readDiveLog() throws DataConversionException, IOException {
        return readDiveLog(filePath);
    }

    /**
     * Similar to {@link #readDiveLog(Path)}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDiveLog> readDiveLog(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("DiveLog file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableDiveLog xmlDiveLog = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlDiveLog.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDiveLog(ReadOnlyDiveLog diveLog) throws IOException {
        saveDiveLog(diveLog, filePath);
    }

    /**
     * Similar to {@link #saveDiveLog(ReadOnlyDiveLog)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveDiveLog(ReadOnlyDiveLog diveLog, Path filePath) throws IOException {
        requireNonNull(diveLog);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableDiveLog(diveLog));
    }

}
