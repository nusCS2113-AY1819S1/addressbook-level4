package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.security.AppUsers;

/**
 * A class to access AppUsers stored in the hard disk as a json file
 */
public class JsonAppUsersStorage implements AppUsersStorage {

    private Path filePath;

    public JsonAppUsersStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getAppUsersFilePath() {
        return filePath;
    }

    @Override
    public Optional<AppUsers> readAppUsers() throws DataConversionException {
        return readAppUsers(filePath);
    }

    /**
     * Similar to {@link #readAppUsers()}
     * @param appUsersFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<AppUsers> readAppUsers(Path appUsersFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(appUsersFilePath, AppUsers.class);
    }

    @Override
    public void saveAppUsers(AppUsers appUsers) throws IOException {
        JsonUtil.saveJsonFile(appUsers, filePath);
    }

}
