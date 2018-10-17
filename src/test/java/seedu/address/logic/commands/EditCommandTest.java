package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showItemAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditItemDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StockList;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Item;
import seedu.address.testutil.EditItemDescriptorBuilder;
import seedu.address.testutil.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalStockList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Item editedItem = new ItemBuilder().build();
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new StockList(model.getStockList()), new UserPrefs());
        expectedModel.updateItem(model.getFilteredItemList().get(0), editedItem);
        expectedModel.commitStockList();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastItem = Index.fromOneBased(model.getFilteredItemList().size());
        Item lastItem = model.getFilteredItemList().get(indexLastItem.getZeroBased());

        ItemBuilder itemInList = new ItemBuilder(lastItem);
        Item editedItem = itemInList.withName(VALID_NAME_RPLIDAR).withQuantity(VALID_QUANTITY_RPLIDAR)
                .withTags(VALID_TAG_LAB1).build();
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_RPLIDAR)
                .withQuantity(VALID_QUANTITY_RPLIDAR).withTags(VALID_TAG_LAB1).build();
        EditCommand editCommand = new EditCommand(indexLastItem, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new StockList(model.getStockList()), new UserPrefs());
        expectedModel.updateItem(lastItem, editedItem);
        expectedModel.commitStockList();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, new EditItemDescriptor());
        Item editedItem = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new StockList(model.getStockList()), new UserPrefs());
        expectedModel.commitStockList();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        Item itemInFilteredList = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        Item editedItem = new ItemBuilder(itemInFilteredList).withName(VALID_NAME_RPLIDAR).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM,
                new EditItemDescriptorBuilder().withName(VALID_NAME_RPLIDAR).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new StockList(model.getStockList()), new UserPrefs());
        expectedModel.updateItem(model.getFilteredItemList().get(0), editedItem);
        expectedModel.commitStockList();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateItemUnfilteredList_failure() {
        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(firstItem).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ITEM, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_duplicateItemFilteredList_failure() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        // edit item in filtered list into a duplicate in address book
        Item itemInList = model.getStockList().getItemList().get(INDEX_SECOND_ITEM.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM,
                new EditItemDescriptorBuilder(itemInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_invalidItemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_RPLIDAR).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidItemIndexFilteredList_failure() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getStockList().getItemList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditItemDescriptorBuilder().withName(VALID_NAME_RPLIDAR).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item editedItem = new ItemBuilder().build();
        Item itemToEdit = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, descriptor);
        Model expectedModel = new ModelManager(new StockList(model.getStockList()), new UserPrefs());
        expectedModel.updateItem(itemToEdit, editedItem);
        expectedModel.commitStockList();

        // edit -> first item edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts stocklist back to previous state and filtered item list to show all items
        expectedModel.undoStockList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item edited again
        expectedModel.redoStockList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_RPLIDAR).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Item} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited item in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the item object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameItemEdited() throws Exception {
        Item editedItem = new ItemBuilder().build();
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, descriptor);
        Model expectedModel = new ModelManager(new StockList(model.getStockList()), new UserPrefs());

        showItemAtIndex(model, INDEX_SECOND_ITEM);
        Item itemToEdit = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        expectedModel.updateItem(itemToEdit, editedItem);
        expectedModel.commitStockList();

        // edit -> edits second item in unfiltered item list / first item in filtered item list
        editCommand.execute(model, commandHistory);

        // undo -> reverts stocklist back to previous state and filtered item list to show all items
        expectedModel.undoStockList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased()), itemToEdit);
        // redo -> edits same second item in unfiltered item list
        expectedModel.redoStockList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ITEM, DESC_ARDUINO);

        // same values -> returns true
        EditItemDescriptor copyDescriptor = new EditItemDescriptor(DESC_ARDUINO);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ITEM, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ITEM, DESC_ARDUINO)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ITEM, DESC_RPLIDAR)));
    }

}
