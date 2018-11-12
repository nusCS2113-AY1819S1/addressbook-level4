package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_LOCATION_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_LOCATION_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_QUANTITY_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_QUANTITY_BANANA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.item.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {

    public static final Item ACCORDION = new ItemBuilder().withItemName("Accordion")
            .withItemQuantity("1").withItemLocation("Cabinet").build();
    public static final Item BUGLE = new ItemBuilder().withItemName("Bugle")
            .withItemQuantity("4").withItemLocation("Floor").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Item APPLE = new ItemBuilder().withItemName(VALID_ITEM_NAME_APPLE)
            .withItemQuantity(VALID_ITEM_QUANTITY_APPLE).withItemLocation(VALID_ITEM_LOCATION_APPLE).build();
    public static final Item BANANA = new ItemBuilder().withItemName(VALID_ITEM_NAME_BANANA)
            .withItemQuantity(VALID_ITEM_QUANTITY_BANANA).withItemLocation(VALID_ITEM_LOCATION_BANANA).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical items.
     */
    public static AddressBook getTypicalItemList() {
        AddressBook ab = new AddressBook();
        for (Item item : getTypicalItems()) {
            ab.addItem(item);
        }
        return ab;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(ACCORDION, BUGLE));
    }
}
