package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAppts.APPT_EXAMPLE1;
import static seedu.address.testutil.TypicalAppts.APPT_EXAMPLE2;
import static seedu.address.testutil.TypicalAppts.APPT_EXAMPLE3;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddApptCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    // First person (Alice) has no appts at start of function, has APPT_EXAMPLE1 at end of function
    @Test
    public void executeAddApptUnfilteredListSuccess() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withAppts(APPT_EXAMPLE1).build();

        AddApptCommand addApptCommand = new AddApptCommand(INDEX_FIRST_PERSON, APPT_EXAMPLE1);

        String expectedMessage = String.format(AddApptCommand.MESSAGE_ADD_APPT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addApptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /* TODO: (Appt) MOVE TO DELETEAPPTCOMMANDTEST ONCE DELETEAPPTCOMMAND IS SETUP
    @Test
    public void executeDeleteRemarkUnfilteredListSuccess() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withAppt("").build();

        AddApptCommand addApptCommand = new AddApptCommand(INDEX_FIRST_PERSON,
                new Appt(editedPerson.getAppt().toString()));

        String expectedMessage = String.format(AddApptCommand.MESSAGE_DELETE_APPT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addApptCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    */

    // First person (Alice) has APPT_EXAMPLE1 at start of function, has APPT_EXAMPLE1 and APPT_EXAMPLE2 at end of
    // function
    @Test
    public void executeFilteredListSuccess() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAppts(APPT_EXAMPLE1, APPT_EXAMPLE2).build();

        AddApptCommand addApptCommand = new AddApptCommand(INDEX_FIRST_PERSON, APPT_EXAMPLE2);

        String expectedMessage = String.format(AddApptCommand.MESSAGE_ADD_APPT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addApptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddApptCommand addApptCommand = new AddApptCommand(outOfBoundIndex, APPT_EXAMPLE2);

        assertCommandFailure(addApptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void executeInvalidPersonIndexFilteredListFailure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddApptCommand addApptCommand = new AddApptCommand(outOfBoundIndex, APPT_EXAMPLE2);

        assertCommandFailure(addApptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    // Third person (Carl) has no appts at start of function, has APPT_EXAMPLE1 at end of function
    @Test
    public void executeUndoRedoValidIndexUnfilteredListSuccess() throws Exception {
        Person personToModify = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withAppts(APPT_EXAMPLE1).build();
        AddApptCommand addApptCommand = new AddApptCommand(INDEX_THIRD_PERSON, APPT_EXAMPLE1);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // appt -> added appt to third person
        addApptCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedoInvalidIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddApptCommand addApptCommand = new AddApptCommand(outOfBoundIndex, APPT_EXAMPLE3);

        // execution failed -> address book state not added into model
        assertCommandFailure(addApptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#appt} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    // Third person (Carl) has APPT_EXAMPLE1 at start of function, has APPT_EXAMPLE1 and APPT_EXAMPLE2 at end of
    // function
    @Test
    public void executeUndoRedoValidIndexFilteredListSamePersonDeleted() throws Exception {
        AddApptCommand addApptCommand = new AddApptCommand(INDEX_FIRST_PERSON, APPT_EXAMPLE2);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withAppts(APPT_EXAMPLE1, APPT_EXAMPLE2).build();
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // appt -> modifies second person in unfiltered person list / first person in filtered person list
        addApptCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> modifies same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final AddApptCommand standardCommand = new AddApptCommand(INDEX_FIRST_PERSON, APPT_EXAMPLE1);

        // same values -> returns true
        AddApptCommand commandWithSameValues = new AddApptCommand(INDEX_FIRST_PERSON, APPT_EXAMPLE1);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddApptCommand(INDEX_SECOND_PERSON, APPT_EXAMPLE1)));

        // different appt -> returns false
        assertFalse(standardCommand.equals(new AddApptCommand(INDEX_FIRST_PERSON, APPT_EXAMPLE2)));
    }
}
