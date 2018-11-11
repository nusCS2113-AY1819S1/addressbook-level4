package seedu.address.storage.transactions;

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
import seedu.address.model.transaction.ReadOnlyTransactionList;
import seedu.address.storage.XmlFileStorage;

/**
 * A class to access TransactionList data stored as an xml file on the hard disk.
 */
public class XmlTransactionListStorage implements TransactionListStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlTransactionListStorage.class);

    private Path filePath;

    public XmlTransactionListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTransactionListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTransactionList> readTransactionList() throws DataConversionException, IOException {
        return readTransactionList(filePath);
    }

    /**
     * Similar to {@link #readTransactionList()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTransactionList> readTransactionList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Transaction List file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableTransactionList xmlTransactionList = XmlFileStorage.loadTransactionDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlTransactionList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTransactionList(ReadOnlyTransactionList transactionList) throws IOException {
        saveTransactionList(transactionList, filePath);
    }

    /**
     * Similar to {@link #saveTransactionList(ReadOnlyTransactionList)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void saveTransactionList(ReadOnlyTransactionList transactionList, Path filePath) throws IOException {
        requireNonNull(transactionList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveTransactionDataToFile(filePath, new XmlSerializableTransactionList(transactionList));
    }
}
