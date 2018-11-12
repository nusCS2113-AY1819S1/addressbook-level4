package seedu.planner.logic.commands;

import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;

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
