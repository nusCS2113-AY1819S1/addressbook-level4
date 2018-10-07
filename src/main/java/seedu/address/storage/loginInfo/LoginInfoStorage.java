package seedu.address.storage.loginInfo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.LoginInfoManager;

/**
 * Represents a storage for {@link LoginInfoManager}.
 */
public interface LoginInfoStorage {

    /**
     * Returns the file path of the Login info data file.
     */
    Path getLoginInfoFilePath();

    /**
     * Returns Login info data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<LoginInfoManager> readLoginInfo() throws DataConversionException, IOException;

    /**
     * Saves the given {@link LoginInfoManager} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLoginInfo(LoginInfoManager userPrefs) throws IOException;

}
