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
import seedu.address.model.ReadOnlyExpenseBook;

/**
 * A class to access ExpenseBook data stored as an xml file on the hard disk.
 */
public class XmlExpenseBookStorage implements ExpenseBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlExpenseBookStorage.class);

    private Path filePath;

    public XmlExpenseBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getExpenseBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyExpenseBook> readExpenseBook() throws DataConversionException, IOException {
        return readExpenseBook(filePath);
    }

    /**
     * Similar to {@link #readExpenseBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyExpenseBook> readExpenseBook(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ExpenseBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableExpenseBook xmlExpenseBook = XmlFileStorage.loadExpenseFromSaveFile(filePath);
        try {
            return Optional.of(xmlExpenseBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveExpenseBook(ReadOnlyExpenseBook expenseBook) throws IOException {
        saveExpenseBook(expenseBook, filePath);
    }

    /**
     * Similar to {@link #saveExpenseBook(ReadOnlyExpenseBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveExpenseBook(ReadOnlyExpenseBook expenseBook, Path filePath) throws IOException {
        requireNonNull(expenseBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableExpenseBook(expenseBook));
    }

    @Override
    public void backupExpenseBook(ReadOnlyExpenseBook expenseBook, Path backupFilePath) throws IOException {
        saveExpenseBook(expenseBook, backupFilePath);
    }

}
