package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AccountInfo;
import seedu.address.model.UserPrefs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Stores information of user accounts (user-names and passwords) in a JSON file
 */
public class JsonAccountInfoStorage implements AccountInfoStorage {

    private Path filePath;

    public JsonAccountInfoStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getAccountInfoFilePath() {
        return filePath;
    }

    public Optional<AccountInfo> readAccountInfo() throws DataConversionException {
        return readAccountInfo(filePath);
    }

    /**
     * Similar to {@link #readAccountInfo()}
     * @param accountInfoFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<AccountInfo> readAccountInfo(Path accountInfoFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(accountInfoFilePath, AccountInfo.class);
    }

    @Override
    public void saveAccountInfo(AccountInfo accountInfo) throws IOException {
        JsonUtil.saveJsonFile(accountInfo, filePath);
    }
}
