package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.WorkoutBook;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlAdaptedWorkout;
import seedu.address.storage.XmlSerializableWorkoutBook;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.WorkoutBookBuilder;
import seedu.address.testutil.WorkoutBuilder;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validWorkoutBook.xml");
    private static final Path MISSING_WORKOUT_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingWorkoutField.xml");
    private static final Path INVALID_WORKOUT_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidWorkoutField.xml");
    private static final Path VALID_WORKOUT_FILE = TEST_DATA_FOLDER.resolve("validWorkout.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempWorkoutBook.xml");

    private static final String VALID_NAME = "Hans Muster's workout";
    private static final String VALID_TYPE = "strength";
    private static final String VALID_DURATION = "20m";
    private static final String VALID_DIFFICULTY = "beginner";
    private static final String VALID_EQUIPMENT = "dumbbell";
    private static final String VALID_MUSCLE = "bicep";
    private static final String VALID_CALORIES = "123";
    private static final String VALID_INSTRUCTION = "set1: hammer curl reps: 4-6";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("heavy"));

    private static final String INVALID_CALORIES = "123 calories";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, WorkoutBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, WorkoutBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, WorkoutBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        WorkoutBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableWorkoutBook.class).toModelType();
        assertEquals(9, dataFromFile.getWorkoutList().size());
    }

    @Test
    public void xmlAdaptedWorkoutFromFile_fileWithMissingWorkoutField_validResult() throws Exception {
        XmlAdaptedWorkout actualWorkout = XmlUtil.getDataFromFile(
                MISSING_WORKOUT_FIELD_FILE, XmlAdaptedWorkoutWithRootElement.class);
        XmlAdaptedWorkout expectedWorkout = new XmlAdaptedWorkout(
                null, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY, VALID_EQUIPMENT, VALID_MUSCLE,
                VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        assertEquals(expectedWorkout, actualWorkout);
    }


    @Test
    public void xmlAdaptedWorkoutFromFile_fileWithInvalidWorkoutField_validResult() throws Exception {
        XmlAdaptedWorkout actualWorkout = XmlUtil.getDataFromFile(
                INVALID_WORKOUT_FIELD_FILE, XmlAdaptedWorkoutWithRootElement.class);
        XmlAdaptedWorkout expectedWorkout = new XmlAdaptedWorkout(
                VALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY, VALID_EQUIPMENT, VALID_MUSCLE,
                INVALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        assertEquals(expectedWorkout, actualWorkout);
    }


    @Test
    public void xmlAdaptedWorkoutFromFile_fileWithValidWorkout_validResult() throws Exception {
        XmlAdaptedWorkout actualWorkout = XmlUtil.getDataFromFile(
                VALID_WORKOUT_FILE, XmlAdaptedWorkoutWithRootElement.class);
        XmlAdaptedWorkout expectedWorkout = new XmlAdaptedWorkout(
                VALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY, VALID_EQUIPMENT, VALID_MUSCLE,
                VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        assertEquals(expectedWorkout, actualWorkout);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new WorkoutBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new WorkoutBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableWorkoutBook dataToWrite = new XmlSerializableWorkoutBook(new WorkoutBook());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableWorkoutBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableWorkoutBook.class);
        assertEquals(dataToWrite, dataFromFile);

        WorkoutBookBuilder builder = new WorkoutBookBuilder(new WorkoutBook());
        dataToWrite = new XmlSerializableWorkoutBook(
                builder.withWorkout(new WorkoutBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableWorkoutBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedWorkout}
     * objects.
     */
    @XmlRootElement(name = "workout")
    private static class XmlAdaptedWorkoutWithRootElement extends XmlAdaptedWorkout {}
}
