package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.testutil.Assert;

public class ClearExpensesCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyExpensesList_throwsCommandException() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitExpensesList();

        ClearExpensesCommand clearExpensesCommand = new ClearExpensesCommand();
        Assert.assertThrows(CommandException.class, () -> clearExpensesCommand.execute(model, commandHistory));
        assertCommandFailure(new ClearExpensesCommand(), model,
                commandHistory, ClearExpensesCommand.MESSAGE_FAILURE_CLEARED);
    }

    @Test
    public void execute_nonEmptyExpensesList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(), getTypicalScheduleList(),
                getTypicalRecruitmentList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
                getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());

        expectedModel.resetDataExpenses(new ExpensesList());
        expectedModel.commitExpensesList();

        try {
            assertCommandSuccess(new ClearExpensesCommand(), model,
                    commandHistory, ClearExpensesCommand.MESSAGE_SUCCESS, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

}
