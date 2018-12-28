package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT3;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.DeleteApptCommand.MESSAGE_APPT_NOT_FOUND;
import static seedu.address.testutil.TypicalAppts.APPT_EXAMPLE2;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appt.ApptDateTime;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

//@@author brandonccm1996
public class DeleteApptCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withAppts(APPT_EXAMPLE2).build();
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(INDEX_SECOND_PERSON,
                new ApptDateTime(VALID_START_APPT1));

        String expectedMessage = String.format(DeleteApptCommand.MESSAGE_DELETE_APPT_SUCCESS, personToModify);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteApptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(outOfBoundIndex,
                new ApptDateTime(VALID_START_APPT1));

        assertCommandFailure(deleteApptCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withAppts(APPT_EXAMPLE2).build();
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(INDEX_FIRST_PERSON,
                new ApptDateTime(VALID_START_APPT1));

        String expectedMessage = String.format(DeleteApptCommand.MESSAGE_DELETE_APPT_SUCCESS, personToModify);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteApptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(outOfBoundIndex,
                new ApptDateTime(VALID_START_APPT1));

        assertCommandFailure(deleteApptCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToModify = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withAppts(APPT_EXAMPLE2).build();
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(INDEX_SECOND_PERSON,
                new ApptDateTime(VALID_START_APPT1));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // delete -> appt deleted
        deleteApptCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same appt deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(outOfBoundIndex,
                new ApptDateTime(VALID_START_APPT1));

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteApptCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes an appt to a person from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredListSamePersonDeleted_success() throws Exception {
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(INDEX_FIRST_PERSON,
                new ApptDateTime(VALID_START_APPT1));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withAppts(APPT_EXAMPLE2).build();
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // appt -> deletes appt from second person in unfiltered person list / first person in filtered person list
        deleteApptCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and unfiltered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> deletes same appt from same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_invalidStartTimeUnfilteredList_failure() {
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(INDEX_SECOND_PERSON,
                new ApptDateTime(VALID_START_APPT3));
        assertCommandFailure(deleteApptCommand, model, commandHistory, MESSAGE_APPT_NOT_FOUND);
    }

    @Test
    public void equals() {
        DeleteApptCommand command1 = new DeleteApptCommand(INDEX_FIRST_PERSON, new ApptDateTime(VALID_START_APPT1));

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different index -> returns false
        assertFalse(command1.equals(new DeleteApptCommand(INDEX_SECOND_PERSON, new ApptDateTime(VALID_START_APPT1))));

        // different apptdatetime -> returns false
        assertFalse(command1.equals(new DeleteApptCommand(INDEX_FIRST_PERSON, new ApptDateTime(VALID_START_APPT2))));
    }
}
