package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenditures.getTypicalExpenditureTracker;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTodoList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewTaskCommand.
 * {@code ViewTaskCommand}.
 */
public class ViewTaskCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(), getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(), getTypicalExpenditureTracker(), new UserPrefs());
    }

    @Test
    public void execute_view_showsCompletedSuccess() {

        assertCommandSuccess(new ViewTaskCommand("completed"), model, commandHistory, ViewTaskCommand.MESSAGE_SUCCESS_COMPLETED, expectedModel);
    }
    @Test
    public void execute_view_shows_UncompletedSuccess() {
        assertCommandSuccess(new ViewTaskCommand("uncompleted"),model,commandHistory,ViewTaskCommand.MESSAGE_SUCCESS_UNCOMPLETED,expectedModel);
    }
}
