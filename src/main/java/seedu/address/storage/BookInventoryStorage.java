package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.BookInventory;
import seedu.address.model.ReadOnlyBookInventory;

/**
 * Represents a storage for {@link BookInventory}.
 */
public interface BookInventoryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBookInventoryFilePath();

    /**
     * Returns BookInventory data as a {@link ReadOnlyBookInventory}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBookInventory> readBookInventory() throws DataConversionException, IOException;

    /**
     * @see #getBookInventoryFilePath()
     */
    Optional<ReadOnlyBookInventory> readBookInventory(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBookInventory} to the storage.
     * @param bookInventory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBookInventory(ReadOnlyBookInventory bookInventory) throws IOException;

    /**
     * @see #saveBookInventory(ReadOnlyBookInventory)
     */
    void saveBookInventory(ReadOnlyBookInventory bookInventory, Path filePath) throws IOException;

    /**
     * BookInventory can be saved in a fixed temporary place
     */
    void backupInventoryBook(ReadOnlyBookInventory bookInventory) throws IOException;
}
