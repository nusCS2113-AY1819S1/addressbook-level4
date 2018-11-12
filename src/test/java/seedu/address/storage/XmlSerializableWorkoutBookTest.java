package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.WorkoutBook;
import seedu.address.testutil.TypicalWorkouts;

public class XmlSerializableWorkoutBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableWorkoutBookTest");
    private static final Path TYPICAL_WORKOUTS_FILE = TEST_DATA_FOLDER.resolve("typicalWorkoutsWorkoutBook.xml");
    private static final Path INVALID_WORKOUTS_FILE = TEST_DATA_FOLDER.resolve("invalidWorkoutWorkoutBook.xml");
    private static final Path DUPLICATE_WORKOUTS_FILE = TEST_DATA_FOLDER.resolve("duplicateWorkoutWorkoutBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalWorkoutsFile_success() throws Exception {
        XmlSerializableWorkoutBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_WORKOUTS_FILE,
                XmlSerializableWorkoutBook.class);
        WorkoutBook workoutBookFromFile = dataFromFile.toModelType();
        WorkoutBook typicalWorkoutsWorkoutBook = TypicalWorkouts.getTypicalWorkoutBook();
        assertEquals(workoutBookFromFile, typicalWorkoutsWorkoutBook);
    }

    @Test
    public void toModelType_invalidWorkoutFile_throwsIllegalValueException() throws Exception {
        XmlSerializableWorkoutBook dataFromFile = XmlUtil.getDataFromFile(INVALID_WORKOUTS_FILE,
                XmlSerializableWorkoutBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateWorkouts_throwsIllegalValueException() throws Exception {
        XmlSerializableWorkoutBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_WORKOUTS_FILE,
                XmlSerializableWorkoutBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableWorkoutBook.MESSAGE_DUPLICATE_WORKOUT);
        dataFromFile.toModelType();
    }
}
