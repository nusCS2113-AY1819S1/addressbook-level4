package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class ClearScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //@Test
    //    public void executeNotExistingPerson() {
    //        Model model = new ModelManager();
    //        Model expectedModel = new ModelManager();
    //        expectedModel.commitAddressBook();
    //
    //        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //
    //        assertCommandSuccess(new ClearScheduleCommand(INDEX_FIRST_PERSON), model,
    //                commandHistory, ClearScheduleCommand.MESSAGE_USAGE, expectedModel);
    //    }
}
