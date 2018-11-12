package seedu.address.testutil;

import seedu.address.model.item.Item;
import seedu.address.model.item.ItemLocation;
import seedu.address.model.item.ItemName;
import seedu.address.model.item.ItemQuantity;

/**
 * A utility class to help with building Item objects.
 */
public class ItemBuilder {

    public static final String DEFAULT_ITEM_NAME = "Apple";
    public static final String DEFAULT_ITEM_QUANTITY = "3";
    public static final String DEFAULT_ITEM_LOCATION = "Refridgerator";

    private ItemName itemName;
    private ItemQuantity itemQuantity;
    private ItemLocation itemLocation;

    public ItemBuilder() {
        itemName = new ItemName(DEFAULT_ITEM_NAME);
        itemQuantity = new ItemQuantity(DEFAULT_ITEM_QUANTITY);
        itemLocation = new ItemLocation(DEFAULT_ITEM_LOCATION);
    }

    /**
     * Initializes the ItemBuilder with the data of {@code itemToCopy}.
     */
    public ItemBuilder(Item itemToCopy) {
        itemName = itemToCopy.getItemName();
        itemQuantity = itemToCopy.getItemQuantity();
        itemLocation = itemToCopy.getItemLocation();
    }

    /**
     * Sets the {@code ItemName} of the {@code Item} that we are building.
     */
    public ItemBuilder withItemName(String itemName) {
        this.itemName = new ItemName(itemName);
        return this;
    }

    /**
     * Sets the {@code ItemQuantity} of the {@code Item} that we are building.
     */
    public ItemBuilder withItemQuantity(String itemQuantity) {
        this.itemQuantity = new ItemQuantity(itemQuantity);
        return this;
    }

    /**
     * Sets the {@code ItemLocation} of the {@code Item} that we are building.
     */
    public ItemBuilder withItemLocation(String itemLocation) {
        this.itemLocation = new ItemLocation(itemLocation);
        return this;
    }

    public Item build() {
        return new Item(itemName, itemQuantity, itemLocation);
    }

}
