package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDistributors.getTypicalDistributorBook;
import static seedu.address.testutil.TypicalProducts.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ProductDatabase;
import seedu.address.model.TestStorage;
import seedu.address.model.UserDatabase;
import seedu.address.model.UserPrefs;

public class ClearProductCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager(new TestStorage());
        Model expectedModel = new ModelManager(new TestStorage());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearProductCommand(),
                model, commandHistory, ClearProductCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.resetData(new ProductDatabase());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearProductCommand(),
                model, commandHistory, ClearProductCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
