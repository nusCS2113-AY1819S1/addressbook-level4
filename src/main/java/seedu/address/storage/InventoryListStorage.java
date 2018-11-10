package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyInventoryList;

/**
 * Represents a storage for {@link seedu.address.model.InventoryList}.
 */
public interface InventoryListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getInventoryListFilePath();

    /**
     * Returns Inventory List data as a {@link seedu.address.model.ReadOnlyInventoryList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyInventoryList> readInventoryList() throws DataConversionException, IOException;

    /**
     * @see #getInventoryListFilePath()
     */
    Optional<ReadOnlyInventoryList> readInventoryList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyInventoryList} to the storage.
     * @param inventoryList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveInventoryList(ReadOnlyInventoryList inventoryList) throws IOException;

    /**
     * @see #saveInventoryList(ReadOnlyInventoryList)
     */
    void saveInventoryList(ReadOnlyInventoryList inventoryList, Path filePath) throws IOException;

}
