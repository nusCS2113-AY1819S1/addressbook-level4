package seedu.address.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;

public class XmlSerializableLoginBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableLoginBookTest");
    private static final Path INVALID_ACCOUNT_FILE = TEST_DATA_FOLDER.resolve("invalidAccountsLoginBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_invalidAccountFile_throwsIllegalValueException() throws Exception {
        XmlSerializableLoginBook dataFromFile = XmlUtil.getDataFromFile(INVALID_ACCOUNT_FILE,
                XmlSerializableLoginBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
