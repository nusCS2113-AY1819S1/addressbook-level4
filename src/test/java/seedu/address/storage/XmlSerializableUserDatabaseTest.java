package seedu.address.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;

public class XmlSerializableUserDatabaseTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableUserDatabaseTest");
    private static final Path TYPICAL_USERS_FILE = TEST_DATA_FOLDER.resolve("typicalUsersUserDatabase.xml");
    private static final Path INVALID_USER_FILE = TEST_DATA_FOLDER.resolve("invalidUserUserDatabase.xml");
    private static final Path DUPLICATE_USER_FILE = TEST_DATA_FOLDER.resolve("duplicateUserUserDatabase.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableUserDatabase dataFromFile = XmlUtil.getDataFromFile(INVALID_USER_FILE,
                XmlSerializableUserDatabase.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
