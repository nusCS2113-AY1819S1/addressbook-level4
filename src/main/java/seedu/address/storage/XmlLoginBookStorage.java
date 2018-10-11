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
import seedu.address.model.ReadOnlyLoginBook;

/**
 * A class to access LoginBook data stored as an xml file on the hard disk.
 */
public class XmlLoginBookStorage implements LoginBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlLoginBookStorage.class);

    private Path loginFilePath;

    public XmlLoginBookStorage(Path loginFilePath) {
        this.loginFilePath = loginFilePath;
    }

    public Path getLoginBookFilePath() {
        return loginFilePath;
    }

    @Override
    public Optional<ReadOnlyLoginBook> readLoginBook() throws DataConversionException, IOException {
        return readLoginBook(loginFilePath);
    }

    /**
     * Similar to {@link #readLoginBook()}
     * @param loginFilePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyLoginBook> readLoginBook(Path loginFilePath) throws DataConversionException,
                                                                                FileNotFoundException {
        requireNonNull(loginFilePath);

        if (!Files.exists(loginFilePath)) {
            logger.info("LoginBook file " + loginFilePath + " not found");
            return Optional.empty();
        }

        XmlSerializableLoginBook xmlLoginBook = XmlLoginFileStorage.loadDataFromSaveFile(loginFilePath);
        try {
            return Optional.of(xmlLoginBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + loginFilePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveLoginBook(ReadOnlyLoginBook loginBook) throws IOException {
        saveLoginBook(loginBook, loginFilePath);
    }

    /**
     * Similar to {@link #saveLoginBook(ReadOnlyLoginBook)}
     * @param loginFilePath location of the data. Cannot be null
     */
    public void saveLoginBook(ReadOnlyLoginBook loginBook, Path loginFilePath) throws IOException {
        requireNonNull(loginBook);
        requireNonNull(loginFilePath);

        FileUtil.createIfMissing(loginFilePath);
        XmlLoginFileStorage.saveDataToFile(loginFilePath, new XmlSerializableLoginBook(loginBook));
    }
}
