package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MIN_QUANTITY_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LAB1;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LAB2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_QUANTITY_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_ARDUINO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.KEYWORD_MATCHING_AR;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ItemBuilder;
import seedu.address.testutil.ItemUtil;

public class AddCommandSystemTest extends StockListSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a item without tags to a non-empty stock list, command with leading spaces and trailing spaces
         * -> added
         */
        Item toAdd = ARDUINO;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_ARDUINO + "  " + QUANTITY_DESC_ARDUINO + " "
                + MIN_QUANTITY_DESC_ARDUINO + "   " + TAG_DESC_LAB1 + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Arduino to the list -> Arduino deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Arduino to the list -> Arduino added again */
        command = RedoCommand.COMMAND_WORD;
        model.addItem(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add an item with all fields same as another item in the stock list except name -> added */
        toAdd = new ItemBuilder(ARDUINO).withName(VALID_NAME_ARDUINO).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO
                + MIN_QUANTITY_DESC_ARDUINO + TAG_DESC_LAB1;
        assertCommandSuccess(command, toAdd);

        /* Case: add an item with all fields same as another item in the stock list except quantity and minQuantity
         * -> added
         */
        toAdd = new ItemBuilder(ARDUINO).withQuantity(VALID_QUANTITY_ARDUINO)
                .withMinQuantity(VALID_MIN_QUANTITY_ARDUINO).build();
        command = ItemUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty stock list -> added */
        deleteAllItems();
        assertCommandSuccess(ARDUINO);

        /* Case: add an item with tags, command with parameters in random order -> added */
        toAdd = ARDUINO;
        command = AddCommand.COMMAND_WORD + TAG_DESC_LAB1 + QUANTITY_DESC_ARDUINO
                + MIN_QUANTITY_DESC_ARDUINO + NAME_DESC_ARDUINO
                + TAG_DESC_LAB2;
        assertCommandSuccess(command, toAdd);

        /* Case: add an item, missing tags -> added */
        assertCommandSuccess(ARDUINO);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the stock list before adding -> added */
        showItemsWithName(KEYWORD_MATCHING_AR);
        assertCommandSuccess(ARDUINO);

        /* ------------------------ Perform add operation while a item card is selected --------------------------- */

        /* Case: selects first card in the stock list, add an item -> added, card selection remains unchanged */
        selectItem(Index.fromOneBased(1));
        assertCommandSuccess(ARDUINO);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate item -> rejected */
        command = ItemUtil.getAddCommand(ARDUINO);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: add a duplicate item except with different quantity -> rejected */
        toAdd = new ItemBuilder(ARDUINO).withQuantity(VALID_QUANTITY_ARDUINO).build();
        command = ItemUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: add a duplicate item except with different minQuantity -> rejected */
        toAdd = new ItemBuilder(ARDUINO).withMinQuantity(VALID_MIN_QUANTITY_ARDUINO).build();
        command = ItemUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: add a duplicate item except with different tags -> rejected */
        command = ItemUtil.getAddCommand(ARDUINO) + " " + PREFIX_TAG.getPrefix() + "lab7";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing quantity -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing minQuantity -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + ItemUtil.getItemDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid quantity -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_ARDUINO + INVALID_QUANTITY_DESC + MIN_QUANTITY_DESC_ARDUINO;
        assertCommandFailure(command, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        /* Case: invalid minQuantity -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO + INVALID_MIN_QUANTITY_DESC;
        assertCommandFailure(command, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code ItemListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code StockListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see StockListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Item toAdd) {
        assertCommandSuccess(ItemUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Item)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Item)
     */
    private void assertCommandSuccess(String command, Item toAdd) {
        Model expectedModel = getModel();
        expectedModel.addItem(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Item)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code ItemListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Item)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code ItemListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code StockListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see StockListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
