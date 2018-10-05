package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_QUANTITY_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.StockList;
import seedu.address.model.item.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {

    // Manually added
    public static final Item RPLIDAR = new ItemBuilder().withName("RP Lidar").withQuantity("25")
            .withMinQuantity("10").withTags("Lab 1").build();
    public static final Item MOTOR = new ItemBuilder().withName("Motor").withQuantity("100")
            .withMinQuantity("20").build();

    // Manually added - Item's details found in {@code CommandTestUtil}
    public static final Item ARDUINO = new ItemBuilder().withName("Arduino")
            .withTags("Lab1").withMinQuantity("5")
            .withQuantity("20")
            .withTags("friends").build();

    public static final String KEYWORD_MATCHING_ARDUINO = "Arduino"; // A keyword that matches MEIER

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code StockList} with all the typical persons.
     */
    public static StockList getTypicalStockList() {
        StockList ab = new StockList();
        for (Item person : getTypicalItems()) {
            ab.addItem(person);
        }
        return ab;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(ARDUINO, RPLIDAR, MOTOR));
    }
}
