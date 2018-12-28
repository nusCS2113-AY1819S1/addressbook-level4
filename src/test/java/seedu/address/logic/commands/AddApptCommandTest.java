package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddApptCommand.MESSAGE_APPT_CLASH;
import static seedu.address.logic.commands.AddApptCommand.MESSAGE_INVALID_TIME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAppts.APPT_EXAMPLE1;
import static seedu.address.testutil.TypicalAppts.APPT_EXAMPLE2;
import static seedu.address.testutil.TypicalAppts.APPT_EXAMPLE3;
import static seedu.address.testutil.TypicalAppts.APPT_EXAMPLE4;
import static seedu.address.testutil.TypicalAppts.INVALID_APPT_EXAMPLE1;
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

//@@author brandonccm1996
public class AddApptCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addApptUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withAppts(APPT_EXAMPLE1).build();
        AddApptCommand addApptCommand = new AddApptCommand(INDEX_FIRST_PERSON, APPT_EXAMPLE1);
        String expectedMessage = String.format(AddApptCommand.MESSAGE_ADD_APPT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addApptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAppts(APPT_EXAMPLE1).build();
        AddApptCommand addApptCommand = new AddApptCommand(INDEX_FIRST_PERSON, APPT_EXAMPLE1);
        String expectedMessage = String.format(AddApptCommand.MESSAGE_ADD_APPT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addApptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddApptCommand addApptCommand = new AddApptCommand(outOfBoundIndex, APPT_EXAMPLE2);

        assertCommandFailure(addApptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        AddApptCommand addApptCommand = new AddApptCommand(outOfBoundIndex, APPT_EXAMPLE2);

        assertCommandFailure(addApptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_undoRedoValidIndexUnfilteredList_success() throws Exception {
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

        // redo -> same third person modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoRedoInvalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddApptCommand addApptCommand = new AddApptCommand(outOfBoundIndex, APPT_EXAMPLE3);

        // execution failed -> address book state not added into model
        assertCommandFailure(addApptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Adds an appt to a person from a filtered list.
     * 2. Undo the addition.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the addition. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void execute_undoRedoValidIndexFilteredListSameApptDeleted_success() throws Exception {
        AddApptCommand addApptCommand = new AddApptCommand(INDEX_FIRST_PERSON, APPT_EXAMPLE1);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withAppts(APPT_EXAMPLE1).build();
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // appt -> adds appt to third person in unfiltered person list / first person in filtered person list
        addApptCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and unfiltered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> adds same appt to same third person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_addInvalidApptUnfilteredList_failure() {
        AddApptCommand addApptCommand = new AddApptCommand(INDEX_FIRST_PERSON, INVALID_APPT_EXAMPLE1);
        assertCommandFailure(addApptCommand, model, commandHistory, MESSAGE_INVALID_TIME);
    }

    @Test
    public void execute_apptClashUnfilteredList_failure() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withAppts(APPT_EXAMPLE3).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        AddApptCommand addApptCommand = new AddApptCommand(INDEX_FIRST_PERSON, APPT_EXAMPLE4);
        assertCommandFailure(addApptCommand, expectedModel, commandHistory, MESSAGE_APPT_CLASH);
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
