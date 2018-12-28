package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalMedHistory.MEDHISTORY1;
import static seedu.address.testutil.TypicalMedHistory.MEDHISTORY2;
import static seedu.address.testutil.TypicalMedHistory.MEDHISTORY_INVALID_DATE;
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
import seedu.address.model.util.DateTimeUtil;
import seedu.address.testutil.PersonBuilder;

//@@author xShaivan
/**
 * Contains integration tests (interaction with the Model) and unit tests for AddHistCommand.
 */

public class AddHistCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addMedHistoryUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withMedHistories(MEDHISTORY1).build();
        AddHistCommand addHistCommand = new AddHistCommand(INDEX_FIRST_PERSON, MEDHISTORY1);
        String expectedMessage = String.format(AddHistCommand.MESSAGE_ADD_MEDHISTORY_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addHistCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withMedHistories(MEDHISTORY1).build();
        AddHistCommand addHistCommand = new AddHistCommand(INDEX_FIRST_PERSON, MEDHISTORY1);
        String expectedMessage = String.format(AddHistCommand.MESSAGE_ADD_MEDHISTORY_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addHistCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddHistCommand addHistCommand = new AddHistCommand(outOfBoundIndex, MEDHISTORY2);
        assertCommandFailure(addHistCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of Health Book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        AddHistCommand addHistCommand = new AddHistCommand(outOfBoundIndex, MEDHISTORY2);
        assertCommandFailure(addHistCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withMedHistories(MEDHISTORY1).build();
        AddHistCommand addHistCommand = new AddHistCommand(INDEX_FIRST_PERSON, MEDHISTORY1);
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
    /**
     * 1. Modifies {@code Person#addhist} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        AddHistCommand addHistCommand = new AddHistCommand(INDEX_FIRST_PERSON, MEDHISTORY1);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withMedHistories(MEDHISTORY1).build();
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
                MEDHISTORY1);
        // same values -> returns true
        AddHistCommand commandWithSameValues = new AddHistCommand(INDEX_FIRST_PERSON,
                MEDHISTORY1);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand == null);
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new AddHistCommand(INDEX_SECOND_PERSON, MEDHISTORY1)));
        // different remark -> returns false
        assertFalse(standardCommand.equals(new AddHistCommand(INDEX_FIRST_PERSON, MEDHISTORY2)));
    }

    @Test
    public void isDuplicateMedHistory() {
        // not duplicate Medical History -> returns false
        assertFalse(AddHistCommand.isDuplicateMedHistory(MEDHISTORY1, MEDHISTORY2));
        // duplicate Medical History -> returns true
        assertTrue(AddHistCommand.isDuplicateMedHistory(MEDHISTORY1, MEDHISTORY1));
    }

    @Test
    public void isDuplicateMedHistDate() {
        // not duplicate Medical History Date -> returns false
        assertFalse(AddHistCommand.isDuplicateMedHistDate(MEDHISTORY1, MEDHISTORY2));
        // duplicate Medical History Date -> returns true
        assertTrue(AddHistCommand.isDuplicateMedHistDate(MEDHISTORY1, MEDHISTORY1));
    }

    @Test
    public void isInvalidMedHistDate() {
        // not invalid Medical History Date -> returns false
        assertFalse(DateTimeUtil.isInvalidMedHistDate(MEDHISTORY1));
        // invalid Medical History Date -> returns true
        assertTrue(DateTimeUtil.isInvalidMedHistDate(MEDHISTORY_INVALID_DATE));
    }
}
