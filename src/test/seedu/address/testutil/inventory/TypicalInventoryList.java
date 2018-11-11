package seedu.address.testutil.inventory;

import static seedu.address.testutil.drinks.TypicalDrinks.FNN_GRAPE;
import static seedu.address.testutil.drinks.TypicalDrinks.GREEN_TEA;
import static seedu.address.testutil.drinks.TypicalDrinks.PEPSI;

import seedu.address.model.InventoryList;

/**
 *  A utility class containing a list of {@code InventoryList} objects to be used in tests.
 */
public class TypicalInventoryList {


    public static InventoryList getTypicalInventoryList() {
        InventoryListBuilder inventoryListBuilder = new InventoryListBuilder();
        inventoryListBuilder.withDrink (FNN_GRAPE)
                            .withDrink (PEPSI)
                            .withDrink (GREEN_TEA);
        return inventoryListBuilder.build ();
    }
}
