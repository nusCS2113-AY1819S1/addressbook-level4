package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ExpenseBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddTaskCommand}.
 */
public class AddTaskCommandIntegrationTest {
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), new ExpenseBook(), new EventBook(),
                getTypicalTaskBook(), new UserPrefs());
    }

    @Test
    public void execute_newTask_success() {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(new AddressBook(), new ExpenseBook(),
                new EventBook(), model.getTaskBook(), new UserPrefs());
        expectedModel.addTask(validTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(new AddTaskCommand(validTask), model, commandHistory,
                String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    /*
    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task taskInList = model.getTaskBook().getTaskList().get(0);
        assertCommandFailure(new AddTaskCommand(taskInList), model, commandHistory,
                AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }*/

}
