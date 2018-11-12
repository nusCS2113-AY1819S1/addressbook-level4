package seedu.divelog.logic.commands;

import static seedu.divelog.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.divelog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.divelog.logic.commands.CommandTestUtil.deleteFirstDive;
import static seedu.divelog.testutil.TypicalDiveSessions.getTypicalDiveLog;

import org.junit.Before;
import org.junit.Test;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.Model;
import seedu.divelog.model.ModelManager;
import seedu.divelog.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalDiveLog(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalDiveLog(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstDive(model);
        deleteFirstDive(model);
        model.undoDiveLog();
        model.undoDiveLog();

        deleteFirstDive(expectedModel);
        deleteFirstDive(expectedModel);
        expectedModel.undoDiveLog();
        expectedModel.undoDiveLog();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoDiveLog();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoDiveLog();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
