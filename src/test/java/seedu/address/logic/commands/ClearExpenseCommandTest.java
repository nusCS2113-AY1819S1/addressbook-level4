package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ExpenseBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;

public class ClearExpenseCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyExpenseBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitExpenseBook();

        assertCommandSuccess(new ClearExpenseCommand(), model, commandHistory,
                ClearExpenseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(new AddressBook(), getTypicalExpenseBook(), new EventBook(),
                new TaskBook(), new UserPrefs());
        Model expectedModel = new ModelManager(new AddressBook(), getTypicalExpenseBook(), new EventBook(),
                new TaskBook(), new UserPrefs());
        expectedModel.resetData(new ExpenseBook());
        expectedModel.commitExpenseBook();

        assertCommandSuccess(new ClearExpenseCommand(), model, commandHistory,
                ClearExpenseCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
