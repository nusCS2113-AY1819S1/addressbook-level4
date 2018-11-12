package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.TrackedData;
import seedu.address.testutil.TypicalWorkouts;

public class XmlSerializableTrackedDataTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableTrackedDataTest");
    private static final Path TYPICAL_TRACKED_DATA_FILE = TEST_DATA_FOLDER.resolve("typicalWorkoutsTrackedData.xml");
    private static final Path INVALID_TRACKED_DATA_FILE = TEST_DATA_FOLDER.resolve("invalidWorkoutTrackedData.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalTrackedDataFile_success() throws Exception {
        XmlSerializableTrackedData dataFromFile = XmlUtil.getDataFromFile(TYPICAL_TRACKED_DATA_FILE,
                XmlSerializableTrackedData.class);
        TrackedData trackedDataFromFile = dataFromFile.toModelType();
        TrackedData typicalWorkoutsTrackedData = TypicalWorkouts.getTypicalTrackedData();
        assertEquals(trackedDataFromFile, typicalWorkoutsTrackedData);
    }

    @Test
    public void toModelType_invalidTrackedDataFile_throwsIllegalValueException() throws Exception {
        XmlSerializableTrackedData dataFromFile = XmlUtil.getDataFromFile(INVALID_TRACKED_DATA_FILE,
                XmlSerializableTrackedData.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
