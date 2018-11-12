package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook_task1And4DiffDeadlines;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

//@@author ChanChunCheong
public class DeferDeadlineCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
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
    public void execute_validIndexUnFilteredList_success() throws Exception {

        Task taskToDefer = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task deferredTask = new TaskBuilder(taskToDefer).withDeadline("10/10/2018").build();
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(INDEX_FIRST_TASK, 1);

        String expectedMessage = String.format(DeferDeadlineCommand.MESSAGE_SUCCESS, taskToDefer);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateTask(taskToDefer , deferredTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(deferDeadlineCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(outOfBoundIndex, 1);

        assertCommandFailure(deferDeadlineCommand, model, commandHistory,
                deferDeadlineCommand.MESSAGE_NONEXISTENT_TASK);
    }


    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskToDefer = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task deferredTask = new TaskBuilder(taskToDefer).withDeadline("10/10/2018").build();
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(INDEX_FIRST_TASK, 1);

        String expectedMessage = String.format(DeferDeadlineCommand.MESSAGE_SUCCESS, taskToDefer);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showTaskAtIndex(expectedModel, INDEX_FIRST_TASK);
        expectedModel.updateTask(taskToDefer, deferredTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(deferDeadlineCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(outOfBoundIndex, 1);
        assertCommandFailure(deferDeadlineCommand, model, commandHistory,
                deferDeadlineCommand.MESSAGE_NONEXISTENT_TASK);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task taskToDefer = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task duplicateTaskWithDiffDeadline = new TaskBuilder(taskToDefer).withDeadline("10/10/2018").build();
        model.addTask(duplicateTaskWithDiffDeadline);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(INDEX_FIRST_TASK, 1);
        assertCommandFailure(deferDeadlineCommand, model, commandHistory,
                deferDeadlineCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_deferredDeadlineNotValid_throwsCommandException() {
        Task taskToDefer = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task duplicateTaskWithDiffDeadline = new TaskBuilder(taskToDefer).withDeadline("31/12/9999").build();
        model.addTask(duplicateTaskWithDiffDeadline);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(INDEX_FIFTH_TASK, 3);
        assertCommandFailure(deferDeadlineCommand, model, commandHistory,
                deferDeadlineCommand.MESSAGE_INVALID_DEFERRED_DEADLINE);
    }

    @Test
    public void execute_sameTaskButDifferentDeadline() {
        Model model = new ModelManager(getTypicalTaskBook_task1And4DiffDeadlines(), new UserPrefs());

        Task taskToDefer = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task deferredTask = new TaskBuilder(taskToDefer).withDeadline("11/10/2018").build();
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(INDEX_FIRST_TASK, 2);


        String expectedMessage = String.format(DeferDeadlineCommand.MESSAGE_SUCCESS, taskToDefer);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateTask(taskToDefer , deferredTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(deferDeadlineCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        int deferredDays = 1;
        DeferDeadlineCommand deferTaskwithFirstIndexCommand = new DeferDeadlineCommand(INDEX_FIRST_TASK, deferredDays);
        DeferDeadlineCommand deferTaskwithSecondIndexCommand = new DeferDeadlineCommand(INDEX_SECOND_TASK,
                deferredDays);

        // same object -> returns true
        Assert.assertTrue(deferTaskwithFirstIndexCommand.equals(deferTaskwithFirstIndexCommand));

        // same values -> returns true
        DeferDeadlineCommand deferTaskwithFirstIndexCommandCopy = new DeferDeadlineCommand(INDEX_FIRST_TASK,
                deferredDays);
        Assert.assertTrue(deferTaskwithFirstIndexCommand.equals(deferTaskwithFirstIndexCommandCopy));

        // different types -> returns false
        assertFalse(deferTaskwithFirstIndexCommand.equals(1));

        // null -> returns false
        assertFalse(deferTaskwithFirstIndexCommand.equals(null));

        // different task -> returns false
        assertFalse(deferTaskwithFirstIndexCommand.equals(deferTaskwithSecondIndexCommand));
    }
}
//@@author
