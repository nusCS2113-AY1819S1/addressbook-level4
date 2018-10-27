package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.MAX_HOURS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.util.logging.Logger;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

//@@author chelseyong
public class CompleteTaskCommandTest {
    private static final Logger logger = LogsCenter.getLogger(CompleteTaskCommand.class);
    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        int completedHours = 1;
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task completedTask = new TaskBuilder(taskToComplete).withCompletedNumOfHours(completedHours).build();
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, completedHours);

        String expectedMessage = String.format(CompleteTaskCommand.MESSAGE_SUCCESS, completedTask);

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.completeTask(taskToComplete, completedHours);
        expectedModel.commitTaskBook();

        assertCommandSuccess(completeTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(outOfBoundIndex, 1);

        assertCommandFailure(completeTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_taskCompletedAlready_throwsCommandException() {
        int completedHours = 1;
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, 1);
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task completedTask = new TaskBuilder(taskToComplete).withCompletedNumOfHours(completedHours).build();

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(taskToComplete, completedTask);
        assertCommandFailure(completeTaskCommand, expectedModel, commandHistory, Messages.MESSAGE_COMPLETED_TASK);
    }

    @Test
    public void execute_taskCompletedZeroHours_throwsCommandException() {
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, 0);
        assertCommandFailure(completeTaskCommand, model, commandHistory, Messages.MESSAGE_ZERO_HOURS_COMPLETION);
    }

    @Test
    public void execute_taskCompletedMaxHours_throwsCommandException() {
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, MAX_HOURS);
        assertCommandFailure(completeTaskCommand, model, commandHistory, Messages.MESSAGE_MAX_HOURS);
    }

    @Test
    public void executeUndoRedo_validIndexFilteredList_success() throws Exception {
        int completedNumOfHours = 1;
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, completedNumOfHours);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        expectedModel.completeTask(taskToComplete, completedNumOfHours);
        expectedModel.commitTaskBook();

        // complete -> completes first task
        completeTaskCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoTaskBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redoTaskBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        int completedNumOfHours = 1;
        CompleteTaskCommand completeFirstCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, completedNumOfHours);
        CompleteTaskCommand completeSecondCommand = new CompleteTaskCommand(INDEX_SECOND_TASK, 2);

        // same object -> returns true
        assertTrue(completeFirstCommand.equals(completeFirstCommand));

        // same values -> returns true
        CompleteTaskCommand completeFirstCommandCopy = new CompleteTaskCommand(INDEX_FIRST_TASK, completedNumOfHours);
        assertTrue(completeFirstCommand.equals(completeFirstCommandCopy));

        // different types -> returns false
        assertFalse(completeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(completeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(completeFirstCommand.equals(completeSecondCommand));
    }

}
