package seedu.address.logic.commands;


import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;


/**
 * Contains integration tests (interaction with the Model) for {@code GenListCommand}.
 */
public class GenListCommandTest {
    Model model = new ModelManager(getTypicalAddressBook(),new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyObservableList_throwsCommandException() {
        Model model = new ModelManager();
        String expectedMessage = String.format(GenListCommand.MESSAGE_EMPTY_LIST);

        GenListCommand genListCommand = new GenListCommand();
        assertCommandFailure(genListCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_genList_success(){
        GenListCommand genListCommand=new GenListCommand();
        String expectedMessage=String.format(GenListCommand.MESSAGE_GENERATE_ATTENDANCE_LIST);
        assertEquals(expectedMessage, GenListCommand.MESSAGE_GENERATE_ATTENDANCE_LIST);
    }
}
