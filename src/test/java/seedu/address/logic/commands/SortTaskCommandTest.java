package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

public class SortTaskCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    @Test
    public void constructor_nullDeadline_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeferDeadlineCommand(null, 0);
    }

    @Test
    public void execute_sortedByModuleCode_success() {
        String method = "module";
        SortTaskCommand sortTaskCommand = new SortTaskCommand(method);
        String expectedMessage = String.format(sortTaskCommand.MESSAGE_SUCCESS, method);

        ModelManager expectedModel = new ModelManager(getTypicalTaskBook(), new UserPrefs());
        expectedModel.sortTask(method);
        expectedModel.commitTaskBook();
        assertCommandSuccess(sortTaskCommand, model, commandHistory, expectedMessage, expectedModel);
        Task task1 = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task task2 = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        Task task3 = model.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        Task task4 = model.getFilteredTaskList().get(INDEX_FOURTH_TASK.getZeroBased());
        assertEquals("CS2101", task1.getModuleCode().toString());
        assertEquals("CS2102", task2.getModuleCode().toString());
        assertEquals("CS2113", task3.getModuleCode().toString());
        assertEquals("CS2114", task4.getModuleCode().toString());
    }


    @Test
    public void execute_sortedByTitle_success() {
        String method = "title";
        SortTaskCommand sortTaskCommand = new SortTaskCommand(method);
        String expectedMessage = String.format(sortTaskCommand.MESSAGE_SUCCESS, method);

        ModelManager expectedModel = new ModelManager(getTypicalTaskBook(), new UserPrefs());
        expectedModel.sortTask(method);
        expectedModel.commitTaskBook();
        assertCommandSuccess(sortTaskCommand, model, commandHistory, expectedMessage, expectedModel);
        Task task1 = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task task2 = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        Task task3 = model.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        Task task4 = model.getFilteredTaskList().get(INDEX_FOURTH_TASK.getZeroBased());
        assertEquals("CS2113", task1.getModuleCode().toString());
        assertEquals("CS2101", task2.getModuleCode().toString());
        assertEquals("CS2102", task3.getModuleCode().toString());
        assertEquals("CS2114", task4.getModuleCode().toString());
    }

    @Test
    public void execute_sortedByPriority_success() {
        String method = "priority";
        SortTaskCommand sortTaskCommand = new SortTaskCommand(method);
        String expectedMessage = String.format(sortTaskCommand.MESSAGE_SUCCESS, method);

        ModelManager expectedModel = new ModelManager(getTypicalTaskBook(), new UserPrefs());
        expectedModel.sortTask(method);
        expectedModel.commitTaskBook();
        assertCommandSuccess(sortTaskCommand, model, commandHistory, expectedMessage, expectedModel);
        Task task1 = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task task2 = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        Task task3 = model.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        Task task4 = model.getFilteredTaskList().get(INDEX_FOURTH_TASK.getZeroBased());
        assertEquals("CS2101", task1.getModuleCode().toString());
        assertEquals("CS2114", task2.getModuleCode().toString());
        assertEquals("CS2102", task3.getModuleCode().toString());
        assertEquals("CS2113", task4.getModuleCode().toString());
    }


    @Test
    public void execute_sortedByDeadlines_success() {
        String method = "deadline";
        SortTaskCommand sortTaskCommand = new SortTaskCommand(method);
        String expectedMessage = String.format(sortTaskCommand.MESSAGE_SUCCESS, method);

        ModelManager expectedModel = new ModelManager(getTypicalTaskBook(), new UserPrefs());
        expectedModel.sortTask(method);
        expectedModel.commitTaskBook();
        assertCommandSuccess(sortTaskCommand, model, commandHistory, expectedMessage, expectedModel);
        Task task1 = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task task2 = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        Task task3 = model.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        Task task4 = model.getFilteredTaskList().get(INDEX_FOURTH_TASK.getZeroBased());
        assertEquals("CS2113", task1.getModuleCode().toString());
        assertEquals("CS2114", task2.getModuleCode().toString());
        assertEquals("CS2101", task3.getModuleCode().toString());
        assertEquals("CS2102", task4.getModuleCode().toString());
    }

    @Test
    public void execute_stableSorting_success() {
        String priority = "priority";
        String deadlines = "deadline";
        SortTaskCommand sortTaskCommandPriority = new SortTaskCommand(priority);
        String expectedMessagePriority = String.format(sortTaskCommandPriority.MESSAGE_SUCCESS, priority);
        SortTaskCommand sortTaskCommandDeadlines = new SortTaskCommand(deadlines);
        String expectedMessageDeadlines = String.format(sortTaskCommandPriority.MESSAGE_SUCCESS, deadlines);

        ModelManager expectedModel = new ModelManager(getTypicalTaskBook(), new UserPrefs());
        //CS2114 will be in front of CS2113.

        expectedModel.sortTask(priority);
        expectedModel.commitTaskBook();
        assertCommandSuccess(sortTaskCommandPriority, model, commandHistory, expectedMessagePriority, expectedModel);

        //CS2114 remains in front of CS2113. Different to the order in execute_sortedByDeadlines_success()
        expectedModel.sortTask(deadlines);
        expectedModel.commitTaskBook();
        assertCommandSuccess(sortTaskCommandDeadlines, model, commandHistory, expectedMessageDeadlines,
                expectedModel);
        Task task1 = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task task2 = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        Task task3 = model.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        Task task4 = model.getFilteredTaskList().get(INDEX_FOURTH_TASK.getZeroBased());
        assertEquals("CS2114", task1.getModuleCode().toString());
        assertEquals("CS2113", task2.getModuleCode().toString());
        assertEquals("CS2101", task3.getModuleCode().toString());
        assertEquals("CS2102", task4.getModuleCode().toString());
    }

    @Test
    public void equals() {
        String title = "title";
        String module = "module";
        SortTaskCommand sortByTitleCommand = new SortTaskCommand(title);
        SortTaskCommand sortByModuleCommand = new SortTaskCommand(module);

        // same object -> returns true
        assertTrue(sortByTitleCommand.equals(sortByTitleCommand));

        // same values -> returns true
        SortTaskCommand sortByTitleCommandCopy = new SortTaskCommand(title);
        assertTrue(sortByTitleCommand.equals(sortByTitleCommandCopy));

        // different types -> returns false
        assertFalse(sortByTitleCommand.equals(1));

        // null -> returns false
        assertFalse(sortByTitleCommand.equals(null));

        // different tag selected -> returns false
        assertFalse(sortByTitleCommand.equals(sortByModuleCommand));
    }
}
