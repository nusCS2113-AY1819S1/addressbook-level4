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
import seedu.address.model.ReadOnlyJobBook;

/**
 * A class to access JobBook data stored as an xml file on the hard disk.
 */
public class XmlJobBookStorage implements JobBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlJobBookStorage.class);

    private Path filePath;

    public XmlJobBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getJobBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyJobBook> readJobBook() throws DataConversionException, IOException {
        return readJobBook(filePath);
    }

    /**
     * Similar to {@link #readJobBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyJobBook> readJobBook(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("JobBook file "  + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableJobBook xmlAddressBook = XmlFileStorage.loadJobBookFromSaveFile(filePath);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveJobBook(ReadOnlyJobBook addressBook) throws IOException {
        saveJobBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveJobBook(ReadOnlyJobBook)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveJobBook(ReadOnlyJobBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveJobBookToFile(filePath, new XmlSerializableJobBook(addressBook));
    }

}
