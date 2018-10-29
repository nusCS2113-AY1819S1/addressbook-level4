package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstEvent;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstEvent(model);
        deleteFirstEvent(model);
        model.undoEventManager();
        model.undoEventManager();

        deleteFirstEvent(expectedModel);
        deleteFirstEvent(expectedModel);
        expectedModel.undoEventManager();
        expectedModel.undoEventManager();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoEventManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoEventManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
