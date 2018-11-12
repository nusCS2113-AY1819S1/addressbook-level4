package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackedData;
import seedu.address.model.TrackedDataList;
import seedu.address.model.UserPrefs;


public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalWorkoutBook(), new TrackedDataList(), new TrackedData(),
                new UserPrefs());
        expectedModel = new ModelManager(model.getWorkoutBook(), new TrackedDataList(), new TrackedData(),
                new UserPrefs());
    }

    @Test
    public void execute_showsSortedList() {
        expectedModel.sortFilteredWorkoutList();
        expectedModel.commitModel();
        assertCommandSuccess(new SortCommand(), model, commandHistory, SortCommand.MESSAGE_USAGE, expectedModel);
    }
}
