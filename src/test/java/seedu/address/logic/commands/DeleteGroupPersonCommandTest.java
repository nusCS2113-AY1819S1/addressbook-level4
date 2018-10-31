package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBookWithGroupPersons;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteGroupPersonCommand.
 */
public class DeleteGroupPersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithGroupPersons(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Group group = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());

        Set<Person> personSet = group.getPersons();
        List<Person> personList = new ArrayList<>(personSet);
        Person personToDelete = personList.get(INDEX_FIRST_PERSON.getZeroBased());

        DeleteGroupPersonCommand deleteGroupPersonCommand =
                new DeleteGroupPersonCommand(INDEX_FIRST_GROUP, INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteGroupPersonCommand.MESSAGE_DELETE_GROUP_PERSON_SUCCESS,
                INDEX_FIRST_GROUP.getOneBased(), INDEX_FIRST_PERSON.getOneBased());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroupPerson(group, personToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteGroupPersonCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Group group = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());

        Set<Person> personSet = group.getPersons();
        List<Person> personList = new ArrayList<>(personSet);

        Index outOfBoundIndex = Index.fromOneBased(personList.size() + 1);
        DeleteGroupPersonCommand deleteGroupPersonCommand =
                new DeleteGroupPersonCommand(INDEX_FIRST_GROUP, outOfBoundIndex);

        assertCommandFailure(deleteGroupPersonCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Group group = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());

        Set<Person> personSet = group.getPersons();
        List<Person> personList = new ArrayList<>(personSet);
        Person personToDelete = personList.get(INDEX_FIRST_PERSON.getZeroBased());

        DeleteGroupPersonCommand deleteGroupPersonCommand =
                new DeleteGroupPersonCommand(INDEX_FIRST_GROUP, INDEX_FIRST_PERSON);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroupPerson(group, personToDelete);
        expectedModel.commitAddressBook();

        // delete -> first person deleted
        deleteGroupPersonCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Group group = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());

        Set<Person> personSet = group.getPersons();
        List<Person> personList = new ArrayList<>(personSet);

        Index outOfBoundIndex = Index.fromOneBased(personList.size() + 1);
        DeleteGroupPersonCommand deleteGroupPersonCommand =
                new DeleteGroupPersonCommand(INDEX_FIRST_GROUP, outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteGroupPersonCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeleteGroupPersonCommand deleteFirstCommand = new DeleteGroupPersonCommand(INDEX_FIRST_GROUP, INDEX_FIRST_PERSON);
        DeleteGroupPersonCommand deleteSecondCommand = new DeleteGroupPersonCommand(INDEX_FIRST_GROUP, INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteGroupPersonCommand deleteFirstCommandCopy = new DeleteGroupPersonCommand(INDEX_FIRST_GROUP, INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
