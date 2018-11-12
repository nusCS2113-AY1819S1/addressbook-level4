package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalParameters.getTypicalTrackedDataList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackedData;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutBook;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyWorkoutBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitModel();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyWorkoutBook_success() {
        Model model = new ModelManager(getTypicalWorkoutBook(), getTypicalTrackedDataList(), new TrackedData(),
                new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalWorkoutBook(), getTypicalTrackedDataList(), new TrackedData(),
                new UserPrefs());
        expectedModel.resetData(new WorkoutBook());
        expectedModel.commitModel();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
