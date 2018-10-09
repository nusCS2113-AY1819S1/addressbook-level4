package seedu.address.testutil;

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
    public static final Item ARDUINO = new ItemBuilder().withName("Arduino")
            .withQuantity("20")
            .withMinQuantity("5")
            .withTags("Lab1").build();

    public static final Item RPLIDAR = new ItemBuilder().withName("RPLidar")
            .withQuantity("25")
            .withMinQuantity("10")
            .withTags("Lab1").build();

    public static final Item MOTOR = new ItemBuilder().withName("Motor")
            .withQuantity("100")
            .withMinQuantity("20").build();

    public static final String KEYWORD_MATCHING_AR = "ar"; // A keyword that matches "ar"

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code StockList} with all the typical items.
     */
    public static StockList getTypicalStockList() {
        StockList ab = new StockList();
        for (Item item : getTypicalItems()) {
            ab.addItem(item);
        }
        return ab;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(ARDUINO, RPLIDAR, MOTOR));
    }
}
