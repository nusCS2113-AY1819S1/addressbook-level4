package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DistributeCommandIntegrationTest {
    private Model model;
    private AddressBook addressBook;
    private UserPrefs userPrefs;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        addressBook = getTypicalAddressBook();
        userPrefs = new UserPrefs();
        model = new ModelManager(addressBook, userPrefs);
    }

//    @Test
//    public void execute_distributionCommand_success() throws ParseException {
//        Distribute validDistribute = new DistributeBuilder().build();
//        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//
//        assertCommandSuccess(new DistributeCommand(validDistribute), model, commandHistory,
//                String.format(DistributeCommand.MESSAGE_SUCCESS, validDistribute), expectedModel);
//    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {

    }
}
