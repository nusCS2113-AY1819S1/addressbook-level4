package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.LoginInfoList;

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
    public Optional< LoginInfoList > readLoginInfo () throws DataConversionException {
        return readLoginInfo(filePath);
    }
    /**
     * Similar to {@link #readLoginInfo()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional< LoginInfoList > readLoginInfo(Path prefsFilePath) throws DataConversionException {
        LoginInfoList testing = JsonUtil.readJsonFile(prefsFilePath, LoginInfoList.class).get ();
        System.out.println (testing);
        return JsonUtil.readJsonFile(prefsFilePath, LoginInfoList.class);
    }

    @Override
    public void saveLoginInfo (LoginInfoList loginInfoList) throws IOException {
        JsonUtil.saveJsonFile(loginInfoList, filePath);
    }
}
