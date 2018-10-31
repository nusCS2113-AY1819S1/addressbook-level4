package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.saleshistory.ReadOnlySalesHistory;
import seedu.address.model.saleshistory.SalesHistory;

/**
 * Interface for the {@link SalesHistory} storage.
 */
public interface SalesHistoryStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getSalesHistoryFilePath();

    /**
     * Returns {@link SalesHistory} data as a {@link ReadOnlySalesHistory}
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySalesHistory> readSalesHistory() throws DataConversionException, IOException;

    /**
     * @see #getSalesHistoryFilePath()
     */
    Optional<ReadOnlySalesHistory> readSalesHistory(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySalesHistory} to the storage.
     * @param salesHistory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSalesHistory(ReadOnlySalesHistory salesHistory) throws IOException;

    /**
     * @see #saveSalesHistory(ReadOnlySalesHistory)
     */
    void saveSalesHistory(ReadOnlySalesHistory salesHistory, Path filePath) throws IOException;

    void deleteSalesHistory() throws IOException;
}
