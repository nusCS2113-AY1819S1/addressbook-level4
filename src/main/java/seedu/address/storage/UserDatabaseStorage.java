package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserDatabase;

/**
 * Represents a storage for {@link seedu.address.model.UserDatabase}.
 */
public interface UserDatabaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getUserDatabaseFilePath();

    /**
     * Returns ProductDatabase data as a {@link ReadOnlyUserDatabase}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException;

    /**
     * @see #getUserDatabaseFilePath()
     */
    Optional<ReadOnlyUserDatabase> readUserDatabase(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyUserDatabase} to the storage.
     * @param userDatabase cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException;

    /**
     * @see #saveUserDatabase(ReadOnlyUserDatabase)
     */
    void saveUserDatabase(ReadOnlyUserDatabase userDatabase, Path filePath) throws IOException;
}
