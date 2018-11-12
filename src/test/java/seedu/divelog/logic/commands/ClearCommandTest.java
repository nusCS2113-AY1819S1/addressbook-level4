package seedu.divelog.logic.commands;

import static seedu.divelog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.divelog.testutil.TypicalDiveSessions.getTypicalDiveLog;

import org.junit.Test;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.Model;
import seedu.divelog.model.ModelManager;
import seedu.divelog.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyDiveLogBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitDiveLog();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyDiveLogBook_success() {
        Model model = new ModelManager(getTypicalDiveLog(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalDiveLog(), new UserPrefs());
        expectedModel.resetData(new DiveLog());
        expectedModel.commitDiveLog();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
