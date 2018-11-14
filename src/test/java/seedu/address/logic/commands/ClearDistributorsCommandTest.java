package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDistributors.getTypicalDistributorBook;
import static seedu.address.testutil.TypicalProducts.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DistributorBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TestStorage;
import seedu.address.model.UserDatabase;
import seedu.address.model.UserPrefs;

public class ClearDistributorsCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyDistributorBook_success() {
        Model model = new ModelManager(new TestStorage());
        Model expectedModel = new ModelManager(new TestStorage());
        expectedModel.commitDistributorBook();

        assertCommandSuccess(new ClearDistributorsCommand(), model, commandHistory,
                ClearDistributorsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyDistributorBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.resetData(new DistributorBook());
        expectedModel.commitDistributorBook();

        assertCommandSuccess(new ClearDistributorsCommand(), model, commandHistory,
                ClearDistributorsCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
