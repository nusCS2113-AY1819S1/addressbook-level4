package seedu.address.logic.commands;
/*
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalItems.getTypicalAccountList;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Item;
import seedu.address.testutil.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */


public class AddCommandIntegrationTest {
/*
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
    }

    @Test
    public void execute_newItem_success() {
        Item validItem = new ItemBuilder().build();

        Model expectedModel = new ModelManager(model.getStockList(), new UserPrefs(), model.getAccountList());
        expectedModel.addItem(validItem);
        expectedModel.commitStockList();

        assertCommandSuccess(new AddCommand(validItem), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validItem), expectedModel);
    }

    @Test
    public void execute_duplicateItem_throwsCommandException() {
        Item itemInList = model.getStockList().getItemList().get(0);
        assertCommandFailure(new AddCommand(itemInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_ITEM);
    }
*/
}
