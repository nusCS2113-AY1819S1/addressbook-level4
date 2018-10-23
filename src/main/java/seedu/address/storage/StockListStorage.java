package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStockList;

/**
 * Represents a storage for {@link seedu.address.model.StockList}.
 */
public interface StockListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getStockListFilePath();

    /**
     * Returns StockList data as a {@link ReadOnlyStockList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStockList> readStockList() throws DataConversionException, IOException;

    /**
     * @see #getStockListFilePath()
     */
    Optional<ReadOnlyStockList> readStockList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyStockList} to the storage.
     * @param stockList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStockList(ReadOnlyStockList stockList) throws IOException;

    /**
     * @see #saveStockList(ReadOnlyStockList)
     */
    void saveStockList(ReadOnlyStockList stockList, Path filePath) throws IOException;

    void saveStockListVersion(ReadOnlyStockList stockList, Path filePath) throws IOException;

}
