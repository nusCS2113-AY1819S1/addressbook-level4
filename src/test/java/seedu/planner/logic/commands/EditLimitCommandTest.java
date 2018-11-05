package seedu.planner.logic.commands;

import org.junit.Test;
import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Limit;
import seedu.planner.testutil.LimitBuilder;

import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalLimits.LIMIT_100;
import static seedu.planner.testutil.TypicalLimits.LIMIT_500;
import static seedu.planner.testutil.TypicalLimits.LIMIT_ALL_DIFFERENT;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

public class EditLimitCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_hasSameDatesLimitInside_success() {
        Limit editedLimit = new LimitBuilder(LIMIT_100).build();
        Limit originalLimit = new LimitBuilder(LIMIT_500).build();
        EditLimitCommand editLimitCommand = new EditLimitCommand(editedLimit);
        String expectedMessage = EditLimitCommand.MESSAGE_SUCCESS + "Original Limit:\n"
                + model.generateLimitOutput(model.isExceededLimit(originalLimit),
                model.getTotalSpend(originalLimit), originalLimit)
                + "Modified Limit: \n"
                + model.generateLimitOutput(model.isExceededLimit(editedLimit),
                model.getTotalSpend(editedLimit), editedLimit);
        model.addLimit(originalLimit);
        Model expectedModel = new ModelManager(new FinancialPlanner(model.getFinancialPlanner()), new UserPrefs());
        expectedModel.updateLimit(originalLimit, editedLimit);
        expectedModel.commitFinancialPlanner();

        assertCommandSuccess(editLimitCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_hasNoSameDatesLimitInside_Fail() {
        Limit editedLimit = new LimitBuilder(LIMIT_100).build();
        Limit originalLimit = new LimitBuilder(LIMIT_ALL_DIFFERENT).build();
        EditLimitCommand editLimitCommand = new EditLimitCommand(editedLimit);
        String expectedMessage = Messages.MESSAGE_LIMITS_DO_NOT_EXIST;
        model.addLimit(originalLimit);
        Model expectedModel = new ModelManager(new FinancialPlanner(model.getFinancialPlanner()), new UserPrefs());
        expectedModel.updateLimit(originalLimit, editedLimit);
        expectedModel.commitFinancialPlanner();

        assertCommandFailure(editLimitCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_emptyLimitList_Fail() {
        Limit editedLimit = new LimitBuilder(LIMIT_100).build();
        Limit originalLimit = new LimitBuilder(LIMIT_500).build();
        EditLimitCommand editLimitCommand = new EditLimitCommand(editedLimit);
        String expectedMessage = Messages.MESSAGE_LIMITS_DO_NOT_EXIST;
        model.addLimit(originalLimit);
        Model expectedModel = new ModelManager(new FinancialPlanner(model.getFinancialPlanner()), new UserPrefs());
        expectedModel.updateLimit(originalLimit, editedLimit);
        expectedModel.commitFinancialPlanner();

        assertCommandFailure(editLimitCommand, model, commandHistory, expectedMessage);
    }


}
