package seedu.address.storage.transactions;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.transaction.ReadOnlyTransactionList;

/**
 * Represents a storage for {@link seedu.address.model.transaction.TransactionList}.
 */
public interface TransactionListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getTransactionListFilePath();

    /**
     * Returns Transaction List data as a {@link seedu.address.model.transaction.ReadOnlyTransactionList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTransactionList> readTransactionList() throws DataConversionException, IOException;

    /**
     * @see #getTransactionListFilePath()
     */
    Optional<ReadOnlyTransactionList> readTransactionList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTransactionList} to the storage.
     *
     * @param transactionList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTransactionList(ReadOnlyTransactionList transactionList) throws IOException;

    /**
     * @see #saveTransactionList(ReadOnlyTransactionList)
     */
    void saveTransactionList(ReadOnlyTransactionList transactionList, Path filePath) throws IOException;
}
