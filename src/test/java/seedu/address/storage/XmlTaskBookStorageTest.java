package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalTasks.CG1112_homework;
import static seedu.address.testutil.TypicalTasks.CG2271_homework;
import static seedu.address.testutil.TypicalTasks.CS2101_homework;
import static seedu.address.testutil.TypicalTasks.CS2113_TASK_1;
import static seedu.address.testutil.TypicalTasks.CS2113_TASK_2;
import static seedu.address.testutil.TypicalTasks.CS2113_homework;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyTaskBook;

public class XmlTaskBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlTaskBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTaskBook(null);
    }

    private java.util.Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws Exception {
        return new XmlTaskBookStorage(Paths.get(filePath)).readTaskBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readTaskBook_invalidTaskBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTaskBook("invalidTaskBook.xml");
    }

    @Test
    public void readTaskBook_invalidAndValidTaskBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTaskBook("invalidAndValidTaskBook.xml");
    }

    @Test
    public void readAndSaveTaskBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempTaskBook.xml");
        AddressBook original = getTypicalTaskBook();
        XmlTaskBookStorage xmlTaskBookStorage = new XmlTaskBookStorage(filePath);

        //Save in new file and read back
        xmlTaskBookStorage.saveTaskBook(original, filePath);
        ReadOnlyTaskBook readBack = xmlTaskBookStorage.readTaskBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(CG2271_homework);
        original.removeTask(CS2113_homework);
        xmlTaskBookStorage.saveTaskBook(original, filePath);
        readBack = xmlTaskBookStorage.readTaskBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        //Save and read without specifying file path
        original.addTask(CG1112_homework);
        xmlTaskBookStorage.saveTaskBook(original); //file path not specified
        readBack = xmlTaskBookStorage.readTaskBook().get(); //file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveTaskBook_nullTaskBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTaskBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) {
        try {
            new XmlTaskBookStorage(Paths.get(filePath))
                    .saveTaskBook(taskBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTaskBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTaskBook(new AddressBook(), null);
    }


}
