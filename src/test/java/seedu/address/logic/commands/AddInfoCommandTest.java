package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
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
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddInfoCommandTest {

    private static final String NRIC_STUB = "S8010517G";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addNRricUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNric(NRIC_STUB).build();

        AddInfoCommand addInfoCommand = new AddInfoCommand(INDEX_FIRST_PERSON, new Nric(editedPerson.getNric().value));

        String expectedMessage = String.format(AddInfoCommand.MESSAGE_ADD_INFO_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addInfoCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNricUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNric("").build();

        AddInfoCommand addInfoCommand = new AddInfoCommand(INDEX_FIRST_PERSON,
                new Nric(editedPerson.getNric().toString()));

        String expectedMessage = String.format(AddInfoCommand.MESSAGE_DELETE_INFO_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addInfoCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNric(NRIC_STUB).build();

        AddInfoCommand addInfoCommand = new AddInfoCommand(INDEX_FIRST_PERSON, new Nric(editedPerson.getNric().value));

        String expectedMessage = String.format(AddInfoCommand.MESSAGE_ADD_INFO_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addInfoCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddInfoCommand addInfoCommand = new AddInfoCommand(outOfBoundIndex, new Nric(VALID_NRIC_BOB));

        assertCommandFailure(addInfoCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddInfoCommand addInfoCommand = new AddInfoCommand(outOfBoundIndex, new Nric(VALID_NRIC_BOB));

        assertCommandFailure(addInfoCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withNric(NRIC_STUB).build();
        AddInfoCommand addInfoCommand = new AddInfoCommand(INDEX_FIRST_PERSON, new Nric(NRIC_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // nric -> first person nric changed
        addInfoCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddInfoCommand addInfoCommand = new AddInfoCommand(outOfBoundIndex, new Nric(""));

        // execution failed -> address book state not added into model
        assertCommandFailure(addInfoCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        AddInfoCommand addInfoCommand = new AddInfoCommand(INDEX_FIRST_PERSON, new Nric(NRIC_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withNric(NRIC_STUB).build();
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // remark -> modifies second person in unfiltered person list / first person in filtered person list
        addInfoCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> modifies same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final AddInfoCommand standardCommand = new AddInfoCommand(INDEX_FIRST_PERSON, new Nric(VALID_NRIC_AMY));

        // same value -> returns true
        AddInfoCommand commandWithSameValues = new AddInfoCommand(INDEX_FIRST_PERSON, new Nric(VALID_NRIC_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddInfoCommand(INDEX_SECOND_PERSON, new Nric(VALID_NRIC_AMY))));

        // different NRIC -> returns false
        assertFalse(standardCommand.equals(new AddInfoCommand(INDEX_FIRST_PERSON, new Nric(VALID_NRIC_BOB))));
    }
}
