//@@author cqinkai
package seedu.address.logic.commands;

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
    }

    @Test
    public void execute_emptyEventManagerWithoutLogin() {
        model = new ModelManager(new EventManager(), new UserPrefs());
        expectedModel = new ModelManager(new EventManager(), new UserPrefs());

        assertCommandSuccess(new UpdateStatusCommand(), model, commandHistory,
                UpdateStatusCommand.MESSAGE_MISSING_EVENTS, expectedModel);
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
    }
}
