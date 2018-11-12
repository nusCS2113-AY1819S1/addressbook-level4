package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_APPLE; //boob
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HEALTHY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalDistributors.getTypicalDistributorBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalProducts.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditProductCommand.EditProductDescriptor;
import seedu.address.model.DistributorBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ProductDatabase;
import seedu.address.model.TestStorage;
import seedu.address.model.UserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.Product;
import seedu.address.testutil.EditProductDescriptorBuilder;
import seedu.address.testutil.ProductBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoProductCommand and RedoProductCommand) and
 * unit tests for EditProductCommand.
 */
public class EditProductCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(), new UserPrefs(),
            new UserDatabase(), new TestStorage());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Product editedPerson = new ProductBuilder().build();
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder(editedPerson).build();
        EditProductCommand editCommand = new EditProductCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditProductCommand.MESSAGE_EDIT_PRODUCT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateProduct(model.getFilteredProductList().get(0), editedPerson);
        expectedModel.commitProductDatabase();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredProductList().size());
        Product lastPerson = model.getFilteredProductList().get(indexLastPerson.getZeroBased());

        ProductBuilder personInList = new ProductBuilder(lastPerson);
        Product editedPerson = personInList.withName(VALID_NAME_APPLE).withSerialNumber(VALID_SERIAL_NUMBER_APPLE)
                .withTags(VALID_TAG_HEALTHY).build();

        EditProductDescriptor descriptor = new EditProductDescriptorBuilder().withName(VALID_NAME_APPLE)
                .withPhone(VALID_SERIAL_NUMBER_APPLE).withTags(VALID_TAG_HEALTHY).build();
        EditProductCommand editCommand = new EditProductCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditProductCommand.MESSAGE_EDIT_PRODUCT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateProduct(lastPerson, editedPerson);
        expectedModel.commitProductDatabase();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditProductCommand editCommand = new EditProductCommand(INDEX_FIRST, new EditProductDescriptor());
        Product editedPerson = model.getFilteredProductList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditProductCommand.MESSAGE_EDIT_PRODUCT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.commitProductDatabase();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Product personInFilteredList = model.getFilteredProductList().get(INDEX_FIRST.getZeroBased());
        Product editedPerson = new ProductBuilder(personInFilteredList).withName(VALID_NAME_APPLE).build();
        EditProductCommand editCommand = new EditProductCommand(INDEX_FIRST,
                new EditProductDescriptorBuilder().withName(VALID_NAME_APPLE).build());

        String expectedMessage = String.format(EditProductCommand.MESSAGE_EDIT_PRODUCT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateProduct(model.getFilteredProductList().get(0), editedPerson);
        expectedModel.commitProductDatabase();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Product firstPerson = model.getFilteredProductList().get(INDEX_FIRST.getZeroBased());
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder(firstPerson).build();
        EditProductCommand editCommand = new EditProductCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditProductCommand.MESSAGE_DUPLICATE_PRODUCT);
    }


    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProductList().size() + 1);
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder().withName(VALID_NAME_APPLE).build();
        EditProductCommand editCommand = new EditProductCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
    }


    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Product editedPerson = new ProductBuilder().build();
        Product personToEdit = model.getFilteredProductList().get(INDEX_FIRST.getZeroBased());
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder(editedPerson).build();
        EditProductCommand editCommand = new EditProductCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateProduct(personToEdit, editedPerson);
        expectedModel.commitProductDatabase();

        // edit -> first person edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoProductCommand(), model, commandHistory,
                UndoProductCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoProductDatabase();
        assertCommandSuccess(new RedoProductCommand(), model, commandHistory,
                RedoProductCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProductList().size() + 1);
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder().withName(VALID_NAME_APPLE).build();
        EditProductCommand editCommand = new EditProductCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoProductCommand(), model, commandHistory, UndoProductCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoProductCommand(), model, commandHistory, RedoProductCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Person} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoProductCommand} edits the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Product editedPerson = new ProductBuilder().build();
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder(editedPerson).build();
        EditProductCommand editCommand = new EditProductCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());

        showPersonAtIndex(model, INDEX_SECOND);
        Product personToEdit = model.getFilteredProductList().get(INDEX_FIRST.getZeroBased());
        expectedModel.updateProduct(personToEdit, editedPerson);
        expectedModel.commitProductDatabase();

        // edit -> edits second person in unfiltered person list / first person in filtered person list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoProductCommand(), model, commandHistory,
                UndoProductCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredProductList().get(INDEX_FIRST.getZeroBased()), personToEdit);
        // redo -> edits same second person in unfiltered person list
        expectedModel.redoProductDatabase();
        assertCommandSuccess(new RedoProductCommand(), model, commandHistory,
                RedoProductCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditProductCommand standardCommand = new EditProductCommand(INDEX_FIRST, DESC_APPLE);

        // same values -> returns true
        EditProductDescriptor copyDescriptor = new EditProductDescriptor(DESC_APPLE);
        EditProductCommand commandWithSameValues = new EditProductCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearProductCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditProductCommand(INDEX_SECOND, DESC_APPLE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditProductCommand(INDEX_FIRST, DESC_BANANA)));
    }

}
