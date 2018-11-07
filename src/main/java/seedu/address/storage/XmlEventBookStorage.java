//@@author ian-tjahjono
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
import seedu.address.model.ReadOnlyEventBook;

/**
 * A class to access EventBook data stored as an xml file on the hard disk.
 */
public class XmlEventBookStorage implements EventBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEventBookStorage.class);

    private Path filePath;

    public XmlEventBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEventBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException {
        return readEventBook(filePath);
    }

    /**
     * Similar to {@link #readEventBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEventBook> readEventBook(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("EventBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableEventBook xmlEventBook = XmlFileStorage.loadEventFromSaveFile(filePath);
        try {
            return Optional.of(xmlEventBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, filePath);
    }

    /**
     * Similar to {@link #saveEventBook(ReadOnlyEventBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveEventBook(ReadOnlyEventBook eventBook, Path filePath) throws IOException {
        requireNonNull(eventBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableEventBook(eventBook));
    }

    @Override
    public void backupEventBook(ReadOnlyEventBook eventBook, Path backupFilePath) throws IOException {
        saveEventBook(eventBook, backupFilePath);
    }

}
