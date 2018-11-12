package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.TrackedDataList;
import seedu.address.testutil.TypicalParameters;

public class XmlSerializableTrackedDataListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableTrackedDataListTest");
    private static final Path TYPICAL_PARAMETERS_FILE = TEST_DATA_FOLDER
            .resolve("typicalParametersTrackedDataList.xml");
    private static final Path INVALID_PARAMETERS_FILE = TEST_DATA_FOLDER
            .resolve("invalidParameterTrackedDataList.xml");
    private static final Path DUPLICATE_PARAMETERS_FILE =
            TEST_DATA_FOLDER.resolve("duplicateParameterTrackedDataList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalTrackedDataList_success() throws Exception {
        XmlSerializableTrackedDataList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PARAMETERS_FILE,
                XmlSerializableTrackedDataList.class);
        TrackedDataList trackedDataListFromFile = dataFromFile.toModelType();
        TrackedDataList typicalParametersTrackedDataList = TypicalParameters.getTypicalTrackedDataList();
        assertEquals(trackedDataListFromFile, typicalParametersTrackedDataList);
    }

    @Test
    public void toModelType_invalidTrackedDataList_throwsIllegalValueException() throws Exception {
        XmlSerializableTrackedDataList dataFromFile = XmlUtil.getDataFromFile(INVALID_PARAMETERS_FILE,
                XmlSerializableTrackedDataList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateParameters_throwsIllegalValueException() throws Exception {
        XmlSerializableTrackedDataList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PARAMETERS_FILE,
                XmlSerializableTrackedDataList.class);
        thrown.expect(IllegalValueException.class);
        //thrown.expectMessage(XmlSerializableTrackedDataList.MESSAGE_DUPLICATE_WORKOUT);
        dataFromFile.toModelType();
    }
}
