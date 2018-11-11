package seedu.address.logic.commands;

import static seedu.address.testutil.drinks.TypicalDrinks.FNN_GRAPE;
import static seedu.address.testutil.inventory.TypicalInventoryList.getTypicalInventoryList;
import static seedu.address.testutil.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.manager.AddDrinkCommand;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.Drink;
import seedu.address.model.transaction.TransactionList;
import seedu.address.model.user.manager.ManagerModel;
import seedu.address.model.user.manager.ManagerModelManager;
import seedu.address.testutil.drinks.DrinkBuilder;
import seedu.address.testutil.inventory.InventoryListBuilder;


public class AddDrinkCommandIntegrationTest {

    private ManagerModel acutalManagerModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        InventoryListBuilder inventoryListBuilder = new InventoryListBuilder();
        inventoryListBuilder.withDrink (FNN_GRAPE);
        acutalManagerModel = new ManagerModelManager (getTypicalInventoryList(), new UserPrefs (),
                                                        new LoginInfoManager (), new TransactionList ());
    }
    @Test
    public void execute_newDrink_success() {
        Drink validDrink = new DrinkBuilder().build ();

        InventoryListBuilder inventoryListBuilder = new InventoryListBuilder();
        ManagerModel expectedModel = new ManagerModelManager (getTypicalInventoryList(), new UserPrefs (),
                                                        new LoginInfoManager (), new TransactionList ());
        expectedModel.addDrink (validDrink);

        assertCommandSuccess(new AddDrinkCommand (validDrink), acutalManagerModel, commandHistory,
                String.format(AddDrinkCommand.MESSAGE_SUCCESS, validDrink.toString ()), expectedModel);
    }


    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Drink drinkInList = acutalManagerModel.getFilteredDrinkList ().get(0);
        assertCommandFailure(new AddDrinkCommand(drinkInList), acutalManagerModel, commandHistory,
                AddDrinkCommand.MESSAGE_DUPLICATE_DRINK);
    }

}
