package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.security.AppUsers;

/**
 * Represents a storage for {@link AppUsers}.
 */
public interface AppUsersStorage {

    /**
     * Returns the file path of the AppUsers data file.
     */
    Path getAppUsersFilePath();

    /**
     * Returns AppUsers data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<AppUsers> readAppUsers() throws DataConversionException, IOException;

    /**
     * Saves the given {@link AppUsers} to the storage.
     * @param appUsers cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAppUsers(AppUsers appUsers) throws IOException;

}
