package t13g2.forum.logic.commands;

import org.junit.Before;
import org.junit.Test;

import t13g2.forum.logic.CommandHistory;
import t13g2.forum.model.Model;
import t13g2.forum.model.ModelManager;
import t13g2.forum.model.UserPrefs;
import t13g2.forum.testutil.TypicalPersons;

public class RedoCommandTest {

    private final Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        CommandTestUtil.deleteFirstPerson(model);
        CommandTestUtil.deleteFirstPerson(model);
        model.undoForumBook();
        model.undoForumBook();

        CommandTestUtil.deleteFirstPerson(expectedModel);
        CommandTestUtil.deleteFirstPerson(expectedModel);
        expectedModel.undoForumBook();
        expectedModel.undoForumBook();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoForumBook();
        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoForumBook();
        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
