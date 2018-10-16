package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unrefactored.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import unrefactored.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddTaskCommand}.
 */
public class AddTaskCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTask(validTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(new AddTaskCommand(validTask), model, commandHistory,
                String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Task taskInList = model.getAddressBook().getTaskList().get(0);
        assertCommandFailure(new AddTaskCommand(taskInList), model, commandHistory,
                AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

}
