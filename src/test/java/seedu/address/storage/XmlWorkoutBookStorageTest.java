package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalWorkouts.ALICE_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.HOON_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.IDA_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.WorkoutBook;

public class XmlWorkoutBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlWorkoutBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readWorkoutBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readWorkoutBook(null);
    }

    private java.util.Optional<ReadOnlyWorkoutBook> readWorkoutBook(String filePath) throws Exception {
        return new XmlWorkoutBookStorage(Paths.get(filePath)).readWorkoutBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWorkoutBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readWorkoutBook("NotXmlFormatWorkoutBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readWorkoutBook_invalidWorkoutWorkoutBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readWorkoutBook("invalidWorkoutWorkoutBook.xml");
    }

    @Test
    public void readWorkoutBook_invalidAndValidWorkoutWorkoutBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readWorkoutBook("invalidAndValidWorkoutWorkoutBook.xml");
    }

    @Test
    public void readAndSaveWorkoutBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempWorkoutBook.xml");
        WorkoutBook original = getTypicalWorkoutBook();
        XmlWorkoutBookStorage xmlWorkoutBookStorage = new XmlWorkoutBookStorage(filePath);

        //Save in new file and read back
        xmlWorkoutBookStorage.saveWorkoutBook(original, filePath);
        ReadOnlyWorkoutBook readBack = xmlWorkoutBookStorage.readWorkoutBook(filePath).get();
        assertEquals(original, new WorkoutBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addWorkout(HOON_WORKOUT);
        original.removeWorkout(ALICE_WORKOUT);
        xmlWorkoutBookStorage.saveWorkoutBook(original, filePath);
        readBack = xmlWorkoutBookStorage.readWorkoutBook(filePath).get();
        assertEquals(original, new WorkoutBook(readBack));

        //Save and read without specifying file path
        original.addWorkout(IDA_WORKOUT);
        xmlWorkoutBookStorage.saveWorkoutBook(original); //file path not specified
        readBack = xmlWorkoutBookStorage.readWorkoutBook().get(); //file path not specified
        assertEquals(original, new WorkoutBook(readBack));

    }

    @Test
    public void saveWorkoutBook_nullWorkoutBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveWorkoutBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code workoutBook} at the specified {@code filePath}.
     */
    private void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook, String filePath) {
        try {
            new XmlWorkoutBookStorage(Paths.get(filePath))
                    .saveWorkoutBook(workoutBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveWorkoutBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveWorkoutBook(new WorkoutBook(), null);
    }


}
