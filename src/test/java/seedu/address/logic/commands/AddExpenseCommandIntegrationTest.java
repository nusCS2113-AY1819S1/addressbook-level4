package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddExpenseCommand}.
 */
public class AddExpenseCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), getTypicalExpenseBook(), new EventBook(),
                new TaskBook(), new UserPrefs());
    }

    @Test
    public void execute_newExpense_success() {
        Expense validExpense = new ExpenseBuilder().build();

        Model expectedModel = new ModelManager(new AddressBook(), getTypicalExpenseBook(), new EventBook(),
                new TaskBook(), new UserPrefs());
        expectedModel.addExpense(validExpense);
        expectedModel.commitExpenseBook();

        assertCommandSuccess(new AddExpenseCommand(validExpense), model, commandHistory,
                String.format(AddExpenseCommand.MESSAGE_SUCCESS, validExpense), expectedModel);
    }

}
