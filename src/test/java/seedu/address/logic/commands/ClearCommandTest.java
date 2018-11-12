package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StockList;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyStockList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        expectedModel.setLoggedInUser(admin);

        expectedModel.commitStockList();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyStockList_success() {
        Model model = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
        Model expectedModel = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());

        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        expectedModel.setLoggedInUser(admin);

        expectedModel.resetData(new StockList());
        expectedModel.commitStockList();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
