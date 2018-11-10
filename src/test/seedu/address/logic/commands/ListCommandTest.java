package seedu.address.logic.commands;

import static seedu.address.testutil.drinks.TypicalDrinks.getTypicalInventoryList;
import static seedu.address.testutil.index.TypicalIndexes.INDEX_FIRST_DRINK;
import static seedu.address.testutil.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.logic.commands.CommandTestUtil.showDrinkAtIndex;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.TransactionList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalInventoryList(), new UserPrefs(), new LoginInfoManagerStub(),
                new TransactionListStub());
        expectedModel = new ModelManager(model.getInventoryList(), new UserPrefs(), new LoginInfoManagerStub(),
                new TransactionListStub());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                String.format(ListCommand.MESSAGE_SUCCESS, model.getInventoryList().toString()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showDrinkAtIndex(model, INDEX_FIRST_DRINK);
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                String.format(ListCommand.MESSAGE_SUCCESS, model.getInventoryList().toString()), expectedModel);
    }

    private class LoginInfoManagerStub extends LoginInfoManager {
        public LoginInfoManagerStub() {}
    }

    private class TransactionListStub extends TransactionList {
        public TransactionListStub() {}
    }
}
