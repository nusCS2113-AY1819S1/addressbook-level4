package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_DESCRIPTION_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showExpenditureAtIndex;
import static seedu.address.testutil.TypicalExpenditures.getTypicalExpenditureTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENDITURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENDITURE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTodoList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.testutil.EditExpenditureDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditExpenditureCommand.
 */
public class EditExpenditureCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(),
            getTypicalExpenditureTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /*
    @Test
    public void executeAllFieldsSpecifiedUnfilteredListSuccess() {
        Expenditure editedExpenditure = new ExpenditureBuilder().build();
        EditExpenditureCommand.EditExpenditureDescriptor descriptor =
                new EditExpenditureDescriptorBuilder(editedExpenditure).build();
        EditExpenditureCommand editExpenditureCommand = new EditExpenditureCommand(INDEX_FIRST_EXPENDITURE, descriptor);

        String expectedMessage =
                String.format(EditExpenditureCommand.MESSAGE_EDIT_EXPENDITURE_SUCCESS, editedExpenditure);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(),
                new ExpenditureTracker(model.getExpenditureTracker()), new UserPrefs());
        expectedModel.updateExpenditure(model.getFilteredExpenditureList().get(0), editedExpenditure);
        expectedModel.commitExpenditureList();

        assertCommandSuccess(editExpenditureCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    */

    /*
    @Test
    public void executeSomeFieldsSpecifiedUnfilteredListSuccess() {
        Index indexLastExpenditure = Index.fromOneBased(model.getFilteredExpenditureList().size());
        Expenditure lastExpenditure = model.getFilteredExpenditureList().get(indexLastExpenditure.getZeroBased());

        ExpenditureBuilder expenditureInList = new ExpenditureBuilder(lastExpenditure);
        Expenditure editedExpenditure = expenditureInList.withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES)
                .withDate(VALID_EXPENDITURE_DATE_CLOTHES)
                .withCategory(VALID_EXPENDITURE_CATEGORY_CLOTHES).build();

        EditExpenditureCommand.EditExpenditureDescriptor descriptor =
                new EditExpenditureDescriptorBuilder().withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES)
                .withDate(VALID_EXPENDITURE_DATE_CLOTHES).withCategory(VALID_EXPENDITURE_CATEGORY_CLOTHES).build();
        EditExpenditureCommand editExpenditureCommand =
                new EditExpenditureCommand(indexLastExpenditure, descriptor);

        String expectedMessage =
                String.format(EditExpenditureCommand.MESSAGE_EDIT_EXPENDITURE_SUCCESS, editedExpenditure);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(),
                new ExpenditureTracker(model.getExpenditureTracker()), new UserPrefs());
        expectedModel.updateExpenditure(lastExpenditure, editedExpenditure);
        expectedModel.commitExpenditureList();

        assertCommandSuccess(editExpenditureCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    */

    /*
    @Test
    public void executeNoFieldSpecifiedUnfilteredListSuccess() {
        EditExpenditureCommand editExpenditureCommand =
                new EditExpenditureCommand(INDEX_FIRST_EXPENDITURE,
                        new EditExpenditureCommand.EditExpenditureDescriptor());
        Expenditure editedExpenditure =
                model.getFilteredExpenditureList().get(INDEX_FIRST_EXPENDITURE.getZeroBased());

        String expectedMessage =
                String.format(EditExpenditureCommand.MESSAGE_EDIT_EXPENDITURE_SUCCESS, editedExpenditure);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.commitExpenditureList();

        assertCommandSuccess(editExpenditureCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    */

    /*
    @Test
    public void executeFilteredListSuccess() {
        showExpenditureAtIndex(model, INDEX_FIRST_EXPENDITURE);

        Expenditure expenditureInFilteredList =
                model.getFilteredExpenditureList().get(INDEX_FIRST_EXPENDITURE.getZeroBased());
        Expenditure editedExpenditure =
                new ExpenditureBuilder(expenditureInFilteredList)
                        .withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES).build();
        EditExpenditureCommand editExpenditureCommand = new EditExpenditureCommand(INDEX_FIRST_EXPENDITURE,
                new EditExpenditureDescriptorBuilder().withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES).build());

        String expectedMessage = String.format(EditExpenditureCommand
                .MESSAGE_EDIT_EXPENDITURE_SUCCESS, editedExpenditure);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.updateExpenditure(model.getFilteredExpenditureList().get(0), editedExpenditure);
        expectedModel.commitExpenditureList();

        assertCommandSuccess(editExpenditureCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    */

    @Test
    public void executeDuplicateExpenditureUnfilteredListFailure() {
        Expenditure firstExpenditure = model.getFilteredExpenditureList().get(INDEX_FIRST_EXPENDITURE.getZeroBased());
        EditExpenditureCommand.EditExpenditureDescriptor descriptor =
                new EditExpenditureDescriptorBuilder(firstExpenditure).build();
        EditExpenditureCommand editExpenditureCommand =
                new EditExpenditureCommand(INDEX_SECOND_EXPENDITURE, descriptor);

        assertCommandFailure(editExpenditureCommand, model, commandHistory,
                EditExpenditureCommand.MESSAGE_DUPLICATE_EXPENDITURE);
    }

    @Test
    public void executeDuplicateExpenditureFilteredListFailure() {
        showExpenditureAtIndex(model, INDEX_FIRST_EXPENDITURE);

        // edit expenditure in filtered list into a duplicate in expenditure tracker
        Expenditure expenditureInList =
                model.getExpenditureTracker().getExpenditureList().get(INDEX_SECOND_EXPENDITURE.getZeroBased());
        EditExpenditureCommand editExpenditureCommand = new EditExpenditureCommand(INDEX_FIRST_EXPENDITURE,
                new EditExpenditureDescriptorBuilder(expenditureInList).build());

        assertCommandFailure(editExpenditureCommand, model, commandHistory,
                EditExpenditureCommand.MESSAGE_DUPLICATE_EXPENDITURE);
    }

    @Test
    public void executeInvalidExpenditureIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenditureList().size() + 1);
        EditExpenditureCommand.EditExpenditureDescriptor descriptor =
                new EditExpenditureDescriptorBuilder().withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES).build();
        EditExpenditureCommand editExpenditureCommand = new EditExpenditureCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editExpenditureCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of expenditure tracker
     */

    @Test
    public void executeInvalidExpenditureIndexFilteredListFailure() {
        showExpenditureAtIndex(model, INDEX_FIRST_EXPENDITURE);
        Index outOfBoundIndex = INDEX_SECOND_EXPENDITURE;
        // ensures that outOfBoundIndex is still in bounds of expenditure tracker list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getExpenditureTracker()
                .getExpenditureList().size());

        EditExpenditureCommand editExpenditureCommand = new EditExpenditureCommand(outOfBoundIndex,
                new EditExpenditureDescriptorBuilder().withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES).build());

        assertCommandFailure(editExpenditureCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
    }
    /*
    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);
        expectedModel.commitAddressBook();

        // edit -> first person edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    @Test
    public void executeUndoRedoInvalidIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenditureList().size() + 1);
        EditExpenditureCommand.EditExpenditureDescriptor descriptor = new EditExpenditureDescriptorBuilder()
                .withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES).build();
        EditExpenditureCommand editExpenditureCommand = new EditExpenditureCommand(outOfBoundIndex, descriptor);

        // execution failed -> expenditure tracker state not added into model
        assertCommandFailure(editExpenditureCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);

        // single expenditure tracker state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
    /*
    /**
     * 1. Edits a {@code Person} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    /*
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.updatePerson(personToEdit, editedPerson);
        expectedModel.commitAddressBook();

        // edit -> edits second person in unfiltered person list / first person in filtered person list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
        // redo -> edits same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/


    @Test
    public void equals() {
        final EditExpenditureCommand standardCommand = new EditExpenditureCommand(INDEX_FIRST_EXPENDITURE, DESC_IPHONE);

        // same values -> returns true
        EditExpenditureCommand.EditExpenditureDescriptor copyDescriptor =
                new EditExpenditureCommand.EditExpenditureDescriptor(DESC_IPHONE);
        EditExpenditureCommand commandWithSameValues =
                new EditExpenditureCommand(INDEX_FIRST_EXPENDITURE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditExpenditureCommand(INDEX_SECOND_EXPENDITURE, DESC_IPHONE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditExpenditureCommand(INDEX_FIRST_EXPENDITURE, DESC_CLOTHES)));
    }
}
