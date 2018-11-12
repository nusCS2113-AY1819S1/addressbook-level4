package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLeaveAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalLeave.getTypicalLeaveList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author Hafizuddin-NUS
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListLeaveCommand.
 */
public class ListLeaveCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLeaveList(), new UserPrefs());
        expectedModel = new ModelManager(model.getLeaveList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListLeaveCommand(), model, commandHistory, ListLeaveCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showLeaveAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListLeaveCommand(), model, commandHistory, ListLeaveCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
