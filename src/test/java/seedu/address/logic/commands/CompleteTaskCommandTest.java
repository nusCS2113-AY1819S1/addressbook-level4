package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class CompleteTaskCommandTest {

    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    /*@Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task completedTask = new TaskBuilder(taskToComplete).withCompleted(true).build();

        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(CompleteTaskCommand.MESSAGE_SUCCESS, completedTask);

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(taskToComplete, completedTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(completeTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }*/

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(outOfBoundIndex, 1);

        assertCommandFailure(completeTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_taskCompletedAlready_throwsCommandException() {
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, 1);
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task completedTask = new TaskBuilder(taskToComplete).withCompleted(true).build();
        model.updateTask(taskToComplete, completedTask);
        assertCommandFailure(completeTaskCommand, model, commandHistory, Messages.MESSAGE_COMPLETED_TASK);
    }

    /*@Test
    public void execute_taskCompleted_zeroHours_throwsCommandException() {
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, 0);
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task completedTask = new TaskBuilder(taskToComplete).withCompleted(true).build();
        model.updateTask(taskToComplete, completedTask);
        assertCommandFailure(completeTaskCommand, model, commandHistory, Messages.MESSAGE_COMPLETED_TASK);
    }*/
}
