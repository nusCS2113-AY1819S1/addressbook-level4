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
import seedu.address.model.StockList;
import seedu.address.model.Model;
import seedu.address.model.item.NameContainsKeywordsPredicate;
import seedu.address.model.item.Item;
import seedu.address.testutil.EditItemDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_LED_RED = "LED Red";
    public static final String VALID_NAME_LED_YELLOW = "LED Yellow";
    public static final Integer VALID_QUANTITY_LED_RED = 11111111;
    public static final Integer VALID_QUANTITY_LED_YELLOW = 22222222;
    public static final Integer VALID_MIN_QUANTITY_LED_RED = 1;
    public static final Integer VALID_MIN_QUANTITY_LED_YELLOW = 2;
    public static final String VALID_TAG_LAB1 = "lab1";
    public static final String VALID_TAG_LAB2 = "lab2";

    public static final String NAME_DESC_LED_RED = " " + PREFIX_NAME + VALID_NAME_LED_RED;
    public static final String NAME_DESC_LED_YELLOW = " " + PREFIX_NAME + VALID_NAME_LED_YELLOW;
    public static final String QUANTITY_DESC_LED_RED = " " + PREFIX_QUANTITY + VALID_QUANTITY_LED_RED;
    public static final String QUANTITY_DESC_LED_YELLOW = " " + PREFIX_QUANTITY + VALID_QUANTITY_LED_YELLOW;
    public static final String MIN_QUANTITY_DESC_LED_RED = " " + PREFIX_MIN_QUANTITY + VALID_MIN_QUANTITY_LED_RED;
    public static final String MIN_QUANTITY_DESC_LED_YELLOW = " " + PREFIX_MIN_QUANTITY + VALID_MIN_QUANTITY_LED_YELLOW;
    public static final String TAG_DESC_LAB1 = " " + PREFIX_TAG + VALID_TAG_LAB1;
    public static final String TAG_DESC_LAB2 = " " + PREFIX_TAG + VALID_TAG_LAB2;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_QUANTITY_DESC = " " + PREFIX_QUANTITY + "911a"; // 'a' not allowed in quantities
    public static final String INVALID_MIN_QUANTITY_DESC = " " + PREFIX_MIN_QUANTITY + "911b"; // 'b' not allowed in min quantities
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditItemDescriptor DESC_LED_RED;
    public static final EditCommand.EditItemDescriptor DESC_LED_YELLOW;

    static {
        DESC_LED_RED = new EditItemDescriptorBuilder().withName(VALID_NAME_LED_RED)
                .withQuantity(VALID_QUANTITY_LED_RED).withMinQuantity(VALID_MIN_QUANTITY_LED_RED)
                .withTags(VALID_TAG_LAB1).build();
        DESC_LED_YELLOW = new EditItemDescriptorBuilder().withName(VALID_NAME_LED_YELLOW)
                .withQuantity(VALID_QUANTITY_LED_YELLOW).withEmail(VALID_MIN_QUANTITY_LED_YELLOW)
                .withTags(VALID_TAG_LAB2, VALID_TAG_LAB1).build();
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
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
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
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showItemAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredItemList().size());

        Item person = model.getFilteredItemList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredItemList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstItem(Model model) {
        Item firstItem = model.getFilteredItemList().get(0);
        model.deleteItem(firstItem);
        model.commitStockList();
    }

}
