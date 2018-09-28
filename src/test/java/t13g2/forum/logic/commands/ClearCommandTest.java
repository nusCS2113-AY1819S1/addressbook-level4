package t13g2.forum.logic.commands;

import org.junit.Test;

import t13g2.forum.logic.CommandHistory;
import t13g2.forum.model.ForumBook;
import t13g2.forum.model.Model;
import t13g2.forum.model.ModelManager;
import t13g2.forum.model.UserPrefs;
import t13g2.forum.testutil.TypicalPersons;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitForumBook();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        expectedModel.resetData(new ForumBook());
        expectedModel.commitForumBook();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
