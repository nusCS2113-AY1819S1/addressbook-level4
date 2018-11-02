package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenditures.getTypicalExpenditureTracker;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTodoList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortTaskCommand.
 * {@code SortTaskCommand}.
 */
public class SortTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(),
            getTypicalExpenditureTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(),
            getTypicalExpenditureTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    @Test
    public void execute_sort_showsDeadlineSuccess() {
        assertCommandSuccess(new SortTaskCommand("date"), model, commandHistory, SortTaskCommand.MESSAGE_SUCCESS_DATE, expectedModel);
    }
    @Test
    public void execute_sort_showsPrioritySuccess() {
        assertCommandSuccess(new SortTaskCommand("date"), model, commandHistory, SortTaskCommand.MESSAGE_SUCCESS_PRIORITY, expectedModel);
    }
    @Test
    public void execute_sort_showsModuleSuccess() {
        assertCommandSuccess(new SortTaskCommand("date"), model, commandHistory, SortTaskCommand.MESSAGE_SUCCESS_MODULE, expectedModel);
    }
}
