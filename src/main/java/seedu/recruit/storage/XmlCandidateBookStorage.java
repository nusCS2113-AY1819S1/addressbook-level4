package seedu.recruit.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.commons.exceptions.DataConversionException;
import seedu.recruit.commons.exceptions.IllegalValueException;
import seedu.recruit.commons.util.FileUtil;
import seedu.recruit.model.ReadOnlyCandidateBook;

/**
 * A class to access CandidateBook data stored as an xml file on the hard disk.
 */
public class XmlCandidateBookStorage implements CandidateBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlCandidateBookStorage.class);

    private Path filePath;

    public XmlCandidateBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCandidateBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCandidateBook> readCandidateBook() throws DataConversionException, IOException {
        return readCandidateBook(filePath);
    }

    /**
     * Similar to {@link #readCandidateBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCandidateBook> readCandidateBook(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("CandidateBook file "  + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableCandidateBook xmlAddressBook = XmlFileStorage.loadCandidateBookFromSaveFile(filePath);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCandidateBook(ReadOnlyCandidateBook addressBook) throws IOException {
        saveCandidateBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveCandidateBook(ReadOnlyCandidateBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveCandidateBook(ReadOnlyCandidateBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveCandidateBookToFile(filePath, new XmlSerializableCandidateBook(addressBook));
    }

}
