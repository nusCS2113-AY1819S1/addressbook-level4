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
import seedu.address.model.ReadOnlyClubBudgetElementsBook;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlClubBudgetElementsBookStorage implements ClubBudgetElementsBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlClubBudgetElementsBookStorage.class);

    private Path filePath;

    public XmlClubBudgetElementsBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getClubBudgetElementsBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyClubBudgetElementsBook> readClubBudgetElementsBook() throws DataConversionException, IOException {
        return readClubBudgetElementsBook(filePath);
    }

    /**
     * Similar to {@link #readClubBudgetElementsBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyClubBudgetElementsBook> readClubBudgetElementsBook(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ClubBudgetElementsBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableClubBudgetElementsBook xmlClubBudgetElementsBook = XmlClubBudgetElementsFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlClubBudgetElementsBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook clubBudgetElementsBook) throws IOException {
        saveClubBudgetElementsBook(clubBudgetElementsBook, filePath);
    }

    /**
     * Similar to {@link #saveClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook clubBudgetElementsBook, Path filePath) throws IOException {
        requireNonNull(clubBudgetElementsBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlClubBudgetElementsFileStorage.saveDataToFile(filePath, new XmlSerializableClubBudgetElementsBook(clubBudgetElementsBook));
    }

}
