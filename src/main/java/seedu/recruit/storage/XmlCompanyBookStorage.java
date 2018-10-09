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
import seedu.recruit.model.ReadOnlyCompanyBook;

/**
 * A class to access CompanyBook data stored as an xml file on the hard disk.
 */
public class XmlCompanyBookStorage implements CompanyBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlCompanyBookStorage.class);

    private Path filePath;

    public XmlCompanyBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getCompanyBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCompanyBook> readCompanyBook() throws DataConversionException, IOException {
        return readCompanyBook(filePath);
    }

    /**
     * Similar to {@link #readCompanyBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyCompanyBook> readCompanyBook(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("CompanyBook file "  + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableCompanyBook xmlAddressBook = XmlFileStorage.loadCompanyBookFromSaveFile(filePath);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCompanyBook(ReadOnlyCompanyBook companyBook) throws IOException {
        saveCompanyBook(companyBook, filePath);
    }

    /**
     * Similar to {@link #saveCompanyBook(ReadOnlyCompanyBook)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveCompanyBook(ReadOnlyCompanyBook companyBook, Path filePath) throws IOException {
        requireNonNull(companyBook);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveCompanyBookToFile(filePath, new XmlSerializableCompanyBook(companyBook));
    }

}
