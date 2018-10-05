package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.FinancialPlanner;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyFinancialPlanner_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitFinancialPlanner();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyFinancialPlanner_success() {
        Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
        expectedModel.resetData(new FinancialPlanner());
        expectedModel.commitFinancialPlanner();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
