package seedu.address.model.testutil;

import seedu.address.model.InventoryList;
import seedu.address.model.drink.Drink;

/**
 * A utility class to help with building InventoryList objects.
 * Example usage: <br>
 * {@code InventoryList list = new InventoryListBuilder().withDrink(coke).build();}
 */
public class InventoryListBuilder {
    private InventoryList inventoryList;

    public InventoryListBuilder() {
        inventoryList = new InventoryList();
    }

    public InventoryListBuilder(InventoryList inventoryList) {
        this.inventoryList = inventoryList;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     *
     * @param drink
     */
    public InventoryListBuilder withDrink(Drink drink) {
        inventoryList.addDrink(drink);
        return this;
    }

    public InventoryList build() {
        return inventoryList;
    }
}
