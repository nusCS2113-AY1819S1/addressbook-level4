package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EventManager;
import seedu.address.model.ReadOnlyEventManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link EventManager}.
 */
public interface EventManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEventManagerFilePath();

    /**
     * Returns EventManager data as a {@link ReadOnlyEventManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEventManager> readEventManager() throws DataConversionException, IOException;

    /**
     * @see #getEventManagerFilePath()
     */
    Optional<ReadOnlyEventManager> readEventManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEventManager} to the storage.
     * @param eventManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventManager(ReadOnlyEventManager eventManager) throws IOException;

    /**
     * @see #saveEventManager(ReadOnlyEventManager)
     */
    void saveEventManager(ReadOnlyEventManager eventManager, Path filePath) throws IOException;

}
