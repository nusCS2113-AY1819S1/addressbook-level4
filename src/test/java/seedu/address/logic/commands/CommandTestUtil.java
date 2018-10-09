package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StockList;
import seedu.address.model.item.Item;
import seedu.address.model.item.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditItemDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ARDUINO = "Arduino";
    public static final String VALID_QUANTITY_ARDUINO = "20";
    public static final String VALID_MIN_QUANTITY_ARDUINO = "5";
    public static final String VALID_TAG_LAB1 = "Lab1";
    public static final String VALID_TAG_LAB2 = "Lab2";

    public static final String VALID_NAME_RPLIDAR = "RP Lidar";
    public static final String VALID_QUANTITY_RPLIDAR = "30";
    public static final String VALID_MIN_QUANTITY_RPLIDAR = "10";

    public static final String NAME_DESC_ARDUINO = " " + PREFIX_NAME + VALID_NAME_ARDUINO;
    public static final String QUANTITY_DESC_ARDUINO = " " + PREFIX_QUANTITY + VALID_QUANTITY_ARDUINO;
    public static final String MIN_QUANTITY_DESC_ARDUINO = " " + PREFIX_MIN_QUANTITY + VALID_MIN_QUANTITY_ARDUINO;
    public static final String TAG_DESC_LAB1 = " " + PREFIX_TAG + VALID_TAG_LAB1;
    public static final String TAG_DESC_LAB2 = " " + PREFIX_TAG + VALID_TAG_LAB2;
    public static final String NAME_DESC_RPLIDAR = " " + PREFIX_NAME + VALID_NAME_RPLIDAR;
    public static final String QUANTITY_DESC_RPLIDAR = " " + PREFIX_MIN_QUANTITY + VALID_QUANTITY_RPLIDAR;
    public static final String MIN_QUANTITY_DESC_RPLIDAR = " " + PREFIX_MIN_QUANTITY + VALID_MIN_QUANTITY_RPLIDAR;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Arduino&"; // '&' not allowed in names
    public static final String INVALID_QUANTITY_DESC =
            " " + PREFIX_QUANTITY + "-1"; // '-' not allowed in qty; cannot be negative
    public static final String INVALID_MIN_QUANTITY_DESC =
            " " + PREFIX_MIN_QUANTITY + "5!"; // '!' not allowed in min qty
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "Lab1*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditItemDescriptor DESC_ARDUINO;
    public static final EditCommand.EditItemDescriptor DESC_RPLIDAR;


    static {
        DESC_ARDUINO = new EditItemDescriptorBuilder().withName(VALID_NAME_ARDUINO)
                .withQuantity(VALID_QUANTITY_ARDUINO).withMinQuantity(VALID_MIN_QUANTITY_ARDUINO)
                .withTags(VALID_TAG_LAB1).build();
        DESC_RPLIDAR = new EditItemDescriptorBuilder().withName(VALID_NAME_RPLIDAR)
                .withQuantity(VALID_QUANTITY_RPLIDAR).withMinQuantity(VALID_MIN_QUANTITY_RPLIDAR)
                .withTags(VALID_TAG_LAB1).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered item list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        StockList expectedStockList = new StockList(actualModel.getStockList());
        List<Item> expectedFilteredList = new ArrayList<>(actualModel.getFilteredItemList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedStockList, actualModel.getStockList());
            assertEquals(expectedFilteredList, actualModel.getFilteredItemList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the item at the given {@code targetIndex} in the
     * {@code model}'s stock list.
     */
    public static void showItemAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredItemList().size());

        Item item = model.getFilteredItemList().get(targetIndex.getZeroBased());
        final String[] splitName = item.getName().fullName.split("\\s+");

        model.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredItemList().size());
    }

    /**
     * Deletes the first item in {@code model}'s filtered list from {@code model}'s stock list.
     */
    public static void deleteFirstItem(Model model) {
        Item firstItem = model.getFilteredItemList().get(0);
        model.deleteItem(firstItem);
        model.commitStockList();
    }

}
