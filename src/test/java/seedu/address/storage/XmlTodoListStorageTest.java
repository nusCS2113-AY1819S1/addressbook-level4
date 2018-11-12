//@@author arty9
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalTasks.ASSIGNMENT;
import static seedu.address.testutil.TypicalTasks.ESSAY;
import static seedu.address.testutil.TypicalTasks.REFLECTION;
import static seedu.address.testutil.TypicalTasks.getTypicalTodoList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTodoList;
import seedu.address.model.TodoList;

public class XmlTodoListStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlTodoListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTodoList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTodoList(null);
    }

    private java.util.Optional<ReadOnlyTodoList> readTodoList(String filePath) throws Exception {
        return new XmlTodoListStorage(Paths.get(filePath)).readTodoList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTodoList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readTodoList("NotXmlFormatTodoList.xml");
    }

    @Test
    public void readTodoList_invalidTaskTodoList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTodoList("invalidTaskTodoList.xml");
    }

    @Test
    public void readTodoList_invalidAndValidTaskTodoList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTodoList("invalidAndValidTaskTodoList.xml");
    }

    @Test
    public void readAndSaveTodoList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempTodoList.xml");
        TodoList original = getTypicalTodoList();
        XmlTodoListStorage xmlTodoListStorage = new XmlTodoListStorage(filePath);

        //Save in new file and read back
        xmlTodoListStorage.saveTodoList(original, filePath);
        ReadOnlyTodoList readBack = xmlTodoListStorage.readTodoList(filePath).get();
        assertEquals(original, new TodoList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(ESSAY);
        original.removeTask(ASSIGNMENT);
        xmlTodoListStorage.saveTodoList(original, filePath);
        readBack = xmlTodoListStorage.readTodoList(filePath).get();
        assertEquals(original, new TodoList(readBack));

        //Save and read without specifying file path
        original.addTask(REFLECTION);
        xmlTodoListStorage.saveTodoList(original); //file path not specified
        readBack = xmlTodoListStorage.readTodoList().get(); //file path not specified
        assertEquals(original, new TodoList(readBack));

    }

    @Test
    public void saveTodoList_nullTodoList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTodoList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code todoList} at the specified {@code filePath}.
     */
    private void saveTodoList(ReadOnlyTodoList todoList, String filePath) {
        try {
            new XmlTodoListStorage(Paths.get(filePath))
                    .saveTodoList(todoList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTodoList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTodoList(new TodoList(), null);
    }

}
