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
import seedu.address.model.ReadOnlyFinalBudgetBook;

/**
 * A class to access FinalBudgetsBook data stored as an xml file on the hard disk.
 */
public class XmlFinalBudgetsBookStorage implements FinalBudgetsBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlFinalBudgetsBookStorage.class);

    private Path filePath;

    public XmlFinalBudgetsBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFinalBudgetsBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFinalBudgetBook> readFinalBudgetsBook() throws DataConversionException, IOException {
        return readFinalBudgetsBook(filePath);
    }

    /**
     * Similar to {@link #readFinalBudgetsBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFinalBudgetBook> readFinalBudgetsBook(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("FinalBudgetsBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableFinalBudgetsBook xmlFinalBudgetsBook = XmlFinalBudgetsFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlFinalBudgetsBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveFinalBudgetsBook(ReadOnlyFinalBudgetBook finalBudgetBook) throws IOException {
        saveFinalBudgetsBook(finalBudgetBook, filePath);
    }

    /**
     * Similar to {@link #saveFinalBudgetsBook(ReadOnlyFinalBudgetBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveFinalBudgetsBook(ReadOnlyFinalBudgetBook finalBudgetBook, Path filePath) throws IOException {
        requireNonNull(finalBudgetBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFinalBudgetsFileStorage.saveDataToFile(filePath, new XmlSerializableFinalBudgetsBook(finalBudgetBook));
    }

}
