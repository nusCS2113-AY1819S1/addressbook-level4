//@@author cqinkai
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.EventManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.User;
import seedu.address.testutil.UserBuilder;

/**
 * Tests for execution of {@code UpdateStatusCommand}
 * with integration of {@code UndoCommand} and {@code RedoCommand}
 * which should not work with {@code UpdateStatusCommand}.
 */
public class UpdateStatusCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_nonEmptyEventManagerWithoutLogin() {
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());

        assertCommandSuccess(new UpdateStatusCommand(), model, commandHistory, UpdateStatusCommand.MESSAGE_SUCCESS,
                expectedModel);

        // login to execute undo/redo commands
        User user = new UserBuilder().build();
        model.logUser(user);

        // undo/redo stack does not record UpdateStatusCommand
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_nonEmptyEventMangerWithLogin() {
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        User user = new UserBuilder().build();
        model.logUser(user);
        expectedModel.logUser(user);

        assertCommandSuccess(new UpdateStatusCommand(), model, commandHistory, UpdateStatusCommand.MESSAGE_SUCCESS,
                expectedModel);

        // undo/redo stack does not record UpdateStatusCommand
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_emptyEventManagerWithoutLogin() {
        model = new ModelManager(new EventManager(), new UserPrefs());
        expectedModel = new ModelManager(new EventManager(), new UserPrefs());

        assertCommandSuccess(new UpdateStatusCommand(), model, commandHistory,
                UpdateStatusCommand.MESSAGE_MISSING_EVENTS, expectedModel);

        // login to execute undo/redo commands
        User user = new UserBuilder().build();
        model.logUser(user);

        // undo/redo stack does not record UpdateStatusCommand
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_emptyEventManagerWithLogin() {
        model = new ModelManager(new EventManager(), new UserPrefs());
        expectedModel = new ModelManager(new EventManager(), new UserPrefs());
        User user = new UserBuilder().build();
        model.logUser(user);
        expectedModel.logUser(user);

        assertCommandSuccess(new UpdateStatusCommand(), model, commandHistory,
                UpdateStatusCommand.MESSAGE_MISSING_EVENTS, expectedModel);

        // undo/redo stack does not record UpdateStatusCommand
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
