package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MIN_QUANTITY_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.MIN_QUANTITY_DESC_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LAB1;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LAB2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.KEYWORD_MATCHING_AR;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ItemBuilder;
import seedu.address.testutil.ItemUtil;

public class EditCommandSystemTest extends StockListSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_ITEM;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_ARDUINO + "  "
                + QUANTITY_DESC_ARDUINO + " " + MIN_QUANTITY_DESC_ARDUINO + "  " + TAG_DESC_LAB1 + " ";
        Item editedItem = new ItemBuilder(ARDUINO).withTags(VALID_TAG_LAB1).build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: undo editing the last item in the list -> last item restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last item in the list -> last item edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateItem(
                getModel().getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased()), editedItem);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a item with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO
                + TAG_DESC_LAB1;
        assertCommandSuccess(command, index, ARDUINO);

        /* Case: edit a item with new values same as another item's values but with different name -> edited */
        assertTrue(getModel().getStockList().getItemList().contains(ARDUINO));
        index = INDEX_SECOND_ITEM;
        assertNotEquals(getModel().getFilteredItemList().get(index.getZeroBased()), ARDUINO);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO
                + TAG_DESC_LAB1;
        editedItem = new ItemBuilder(ARDUINO).withName("Rplidar").build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: edit an item with new values same as another item's values but with different quantity and minQuantity
         * -> edited
         */
        index = INDEX_SECOND_ITEM;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
                + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO
                + TAG_DESC_LAB2;
        editedItem = new ItemBuilder(ARDUINO).withTags(VALID_TAG_LAB2).build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_ITEM;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Item itemToEdit = getModel().getFilteredItemList().get(index.getZeroBased());
        editedItem = new ItemBuilder(itemToEdit).withTags().build();
        assertCommandSuccess(command, index, editedItem);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered item list, edit index within bounds of address book and item list -> edited */
        showItemsWithName(KEYWORD_MATCHING_AR);
        index = INDEX_FIRST_ITEM;
        assertTrue(index.getZeroBased() < getModel().getFilteredItemList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_ARDUINO;
        itemToEdit = getModel().getFilteredItemList().get(index.getZeroBased());
        editedItem = new ItemBuilder(itemToEdit).withName(VALID_NAME_ARDUINO).build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: filtered item list, edit index within bounds of address book but out of bounds of item list
         * -> rejected
         */
        showItemsWithName(KEYWORD_MATCHING_AR);
        int invalidIndex = getModel().getStockList().getItemList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_ARDUINO,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a item card is selected -------------------------- */

        /* Case: selects first card in the item list, edit a item -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllItems();
        index = INDEX_FIRST_ITEM;
        selectItem(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO
                + TAG_DESC_LAB1;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new item's name
        assertCommandSuccess(command, index, ARDUINO, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_ARDUINO,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_ARDUINO,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredItemList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_ARDUINO,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_ARDUINO,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid quantity -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased() + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        /* Case: invalid minQuantity -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_ITEM.getOneBased() + INVALID_MIN_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a item with new values same as another item's values -> rejected */
        executeCommand(ItemUtil.getAddCommand(ARDUINO));
        assertTrue(getModel().getStockList().getItemList().contains(ARDUINO));
        index = INDEX_FIRST_ITEM;
        assertFalse(getModel().getFilteredItemList().get(index.getZeroBased()).equals(ARDUINO));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO
                + TAG_DESC_LAB1;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: edit a item with new values same as another item's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO
                + TAG_DESC_LAB2;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: edit a item with new values same as another item's values but with different quantity ->  rejected*/
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_ARDUINO + QUANTITY_DESC_RPLIDAR + MIN_QUANTITY_DESC_ARDUINO
                + TAG_DESC_LAB1;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: edit a item with new values same as another item's values but with different minQuantity -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO + NAME_DESC_ARDUINO
            + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_RPLIDAR + TAG_DESC_LAB1;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ITEM);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Item, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Item, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Item editedItem) {
        assertCommandSuccess(command, toEdit, editedItem, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the item at index {@code toEdit} being
     * updated to values specified {@code editedItem}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Item editedItem,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateItem(expectedModel.getFilteredItemList().get(toEdit.getZeroBased()), editedItem);
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code StockListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see StockListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see StockListSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
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
