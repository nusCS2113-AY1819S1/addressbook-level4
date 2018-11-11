//@@author SHININGGGG
package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;

public class XmlSerializableExpenditureTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableExpenditureTrackerTest");
    //private static final Path TYPICAL_EXPENDITURES_FILE = TEST_DATA_FOLDER
    // .resolve("typical.xml");
    //private static final Path INVALID_EXPENDITURES_FILE = TEST_DATA_FOLDER
    // .resolve("invalidExpenditureExpenditureTracker.xml");
    private static final Path DUPLICATE_EXPENDITURES_FILE = TEST_DATA_FOLDER
            .resolve("duplicateExpenditureExpenditureTracker.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_duplicateTasks_throwsIllegalValueException() throws Exception {
        XmlSerializableExpenditureTracker dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_EXPENDITURES_FILE,
                XmlSerializableExpenditureTracker.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

}
