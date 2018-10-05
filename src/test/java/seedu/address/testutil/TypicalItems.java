package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_QUANTITY_LED_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_QUANTITY_LED_YELLOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_LED_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_LED_YELLOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_LED_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_LED_YELLOW;
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

    public static final Item ARDUINO = new ItemBuilder().withName("Arduino")
            .withQuantity(20).withMinQuantity(5)
            .withTags("lab1").build();
    public static final Item RASPBERRY_PI  = new ItemBuilder().withName("Raspberry Pi")
            .withQuantity(50)
            .withMinQuantity(15)
            .withTags("lab2").build();

    // Manually added
    public static final Item RPLIDAR = new ItemBuilder().withName("PR Lidar").withQuantity(25)
            .withMinQuantity(10).build();
    public static final Item MOTOR = new ItemBuilder().withName("Motor").withQuantity(100)
            .withMinQuantity(20).build();

    // Manually added - Item's details found in {@code CommandTestUtil}
    public static final Item LED_RED = new ItemBuilder().withName(VALID_NAME_LED_RED).withQuantity(VALID_QUANTITY_LED_RED)
            .withMinQuantity(VALID_MIN_QUANTITY_LED_RED).withTags(VALID_TAG_LAB1).build();
    public static final Item LED_YELLOW = new ItemBuilder().withName(VALID_NAME_LED_YELLOW).withQuantity(VALID_QUANTITY_LED_YELLOW)
            .withMinQuantity(VALID_MIN_QUANTITY_LED_YELLOW).withTags(VALID_TAG_LAB2, VALID_TAG_LAB1)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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
        return new ArrayList<>(Arrays.asList(ARDUINO,RASPBERRY_PI));
    }
}
