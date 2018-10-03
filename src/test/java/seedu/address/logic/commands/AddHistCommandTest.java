package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MedHistory;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddHistCommand.
 */

public class AddHistCommandTest {

    private static final String MEDHISTORY_STUB = "Some medical history";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeaddRemarkUnfilteredListsuccess() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withMedHistory(MEDHISTORY_STUB).build();
        AddHistCommand addHistCommand = new AddHistCommand(INDEX_FIRST_PERSON,
                new MedHistory(editedPerson.getMedHistory().value));
        String expectedMessage = String.format(AddHistCommand.MESSAGE_ADD_MEDHISTORY_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addHistCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void executedeleteRemarkUnfilteredListsuccess() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withMedHistory("").build();
        AddHistCommand addHistCommand = new AddHistCommand(INDEX_FIRST_PERSON,
                new MedHistory(editedPerson.getMedHistory().toString()));
        String expectedMessage = String.format(AddHistCommand.MESSAGE_DELETE_MEDHISTORY_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addHistCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void executefilteredListsuccess() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withMedHistory(MEDHISTORY_STUB).build();
        AddHistCommand addHistCommand = new AddHistCommand(INDEX_FIRST_PERSON,
                new MedHistory(editedPerson.getMedHistory().value));
        String expectedMessage = String.format(AddHistCommand.MESSAGE_ADD_MEDHISTORY_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addHistCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeinvalidPersonIndexUnfilteredListfailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddHistCommand addHistCommand = new AddHistCommand(outOfBoundIndex, new MedHistory(VALID_HISTORY_BOB));
        assertCommandFailure(addHistCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of Health Book
     */
    @Test
    public void executeinvalidPersonIndexFilteredListfailure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        AddHistCommand addHistCommand = new AddHistCommand(outOfBoundIndex, new MedHistory(VALID_HISTORY_BOB));
        assertCommandFailure(addHistCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void executeUndoRedovalidIndexUnfilteredListsuccess() throws Exception {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withMedHistory(MEDHISTORY_STUB).build();
        AddHistCommand addHistCommand = new AddHistCommand(INDEX_FIRST_PERSON, new MedHistory(MEDHISTORY_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();
        // addhist -> first patient's medical history changed
        addHistCommand.execute(model, commandHistory);
        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        // redo -> same first person modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void executeUndoRedoinvalidIndexUnfilteredListfailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddHistCommand addHistCommand = new AddHistCommand(outOfBoundIndex, new MedHistory(""));
        // execution failed -> address book state not added into model
        assertCommandFailure(addHistCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
    /**
     * 1. Modifies {@code Person#addhist} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedovalidIndexFilteredListsamePersonDeleted() throws Exception {
        AddHistCommand addHistCommand = new AddHistCommand(INDEX_FIRST_PERSON, new MedHistory(MEDHISTORY_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withMedHistory(MEDHISTORY_STUB).build();
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();
        // remark -> modifies second person in unfiltered person list / first person in filtered person list
        addHistCommand.execute(model, commandHistory);
        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> modifies same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

    }

    @Test
    public void equals() {
        final AddHistCommand standardCommand = new AddHistCommand(INDEX_FIRST_PERSON,
                new MedHistory(VALID_HISTORY_AMY));
        // same values -> returns true
        AddHistCommand commandWithSameValues = new AddHistCommand(INDEX_FIRST_PERSON,
                new MedHistory(VALID_HISTORY_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand == null);
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new AddHistCommand(INDEX_SECOND_PERSON, new MedHistory(VALID_HISTORY_AMY))));
        // different remark -> returns false
        assertFalse(standardCommand.equals(new AddHistCommand(INDEX_FIRST_PERSON, new MedHistory(VALID_HISTORY_BOB))));
    }
}
