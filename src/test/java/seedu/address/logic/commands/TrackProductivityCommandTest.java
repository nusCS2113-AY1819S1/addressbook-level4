package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.TrackProductivityCommand.MESSAGE_NO_COMPLETED_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

//@@author chelseyong
public class TrackProductivityCommandTest {
    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_noCompletedTask_commandException() {
        TrackProductivityCommand trackProductivityCommand = new TrackProductivityCommand();
        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.trackProductivity();
        assertCommandFailure(trackProductivityCommand, expectedModel, commandHistory, MESSAGE_NO_COMPLETED_TASK);
    }

    @Test
    public void execute_withCompletedTask_success() {
        TrackProductivityCommand trackProductivityCommand = new TrackProductivityCommand();
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task completedTask = new TaskBuilder(taskToComplete).withCompletedNumOfHours(1).build();
        model.updateTask(taskToComplete, completedTask);
        model.trackProductivity();
        double productivity = trackProductivityCommand.calculateProductivity(model.getFilteredTaskList());
        String prodInPercentage = Integer.toString((int) (productivity * 100)) + " %";
        String expectedMessage = String.format(TrackProductivityCommand.MESSAGE_SUCCESS, prodInPercentage);

        try {
            CommandResult result = trackProductivityCommand.execute(model, commandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }
}
