package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.TodoList;
import seedu.address.testutil.TypicalTasks;

public class XmlSerializableTodoListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableTodoListTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksTodoList.xml");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskTodoList.xml");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskTodoList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        XmlSerializableTodoList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_TASKS_FILE,
                XmlSerializableTodoList.class);
        TodoList todoListFromFile = dataFromFile.toModelType();
        TodoList typicalTasksTodoList = TypicalTasks.getTypicalTodoList();
        assertEquals(todoListFromFile, typicalTasksTodoList);
    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        XmlSerializableTodoList dataFromFile = XmlUtil.getDataFromFile(INVALID_TASK_FILE,
                XmlSerializableTodoList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateTasks_throwsIllegalValueException() throws Exception {
        XmlSerializableTodoList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_TASK_FILE,
                XmlSerializableTodoList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableTodoList.MESSAGE_DUPLICATE_TASK);
        dataFromFile.toModelType();
    }

}
