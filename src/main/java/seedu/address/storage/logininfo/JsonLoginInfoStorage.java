package seedu.address.storage.logininfo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.LoginInfoManager;

/**
 * A class to access Login information stored in the hard disk as a json file
 */
public class JsonLoginInfoStorage implements LoginInfoStorage {
    private Path filePath;

    public JsonLoginInfoStorage(Path filePath) {
        this.filePath = filePath;
    }
    @Override
    public Path getLoginInfoFilePath () {
        return filePath;
    }

    @Override
    public Optional<LoginInfoManager> readLoginInfo () throws DataConversionException {
        return readLoginInfo(filePath);
    }
    /**
     * Similar to {@link #readLoginInfo()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<LoginInfoManager> readLoginInfo(Path prefsFilePath) throws DataConversionException {
        LoginInfoManager testing = JsonUtil.readJsonFile(prefsFilePath, LoginInfoManager.class).get ();
        System.out.println (testing);
        return JsonUtil.readJsonFile(prefsFilePath, LoginInfoManager.class);
    }

    @Override
    public void saveLoginInfo (LoginInfoManager loginInfoManager) throws IOException {
        JsonUtil.saveJsonFile(loginInfoManager, filePath);
    }
}
