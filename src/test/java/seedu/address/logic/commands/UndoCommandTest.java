package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstEvent;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(),
            getTypicalEventList(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);
        deleteFirstEvent(model);
        deleteFirstEvent(model);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        deleteFirstEvent(expectedModel);
        deleteFirstEvent(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable EventList states in model
        expectedModel.undoEventList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable EventList state in model
        expectedModel.undoEventList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // multiple undoable AddressBook states in model
        expectedModel.undoBothState();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable AddressBook state in model
        expectedModel.undoBothState();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
