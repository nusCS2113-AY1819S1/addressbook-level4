package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITMENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelTypes;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(), getTypicalScheduleList(),
            getTypicalRecruitmentList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());

        Set<ModelTypes> set = new HashSet<>();
        set.add(ModelTypes.ADDRESS_BOOK);
        expectedModel.deletePerson(personToDelete);

        if (deleteCommand.deleteAllSchedulesFromPerson(expectedModel, personToDelete)) {
            set.add(ModelTypes.SCHEDULES_LIST);
        }
        if (deleteCommand.deleteAllExpensesFromPerson(expectedModel, personToDelete)) {
            set.add(ModelTypes.EXPENSES_LIST);
        }
        expectedModel.commitMultipleLists(set);
        expectedModel.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        expectedModel.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);

        try {
            assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());

        Set<ModelTypes> set = new HashSet<>();
        set.add(ModelTypes.ADDRESS_BOOK);
        expectedModel.deletePerson(personToDelete);

        if (deleteCommand.deleteAllSchedulesFromPerson(expectedModel, personToDelete)) {
            set.add(ModelTypes.SCHEDULES_LIST);
        }
        if (deleteCommand.deleteAllExpensesFromPerson(expectedModel, personToDelete)) {
            set.add(ModelTypes.EXPENSES_LIST);
        }
        expectedModel.commitMultipleLists(set);
        expectedModel.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        expectedModel.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);

        showNoPerson(expectedModel);

        try {
            assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(), model.getScheduleList(),
                model.getRecruitmentList(), new UserPrefs());
        Set<ModelTypes> set = new HashSet<>();
        set.add(ModelTypes.ADDRESS_BOOK);
        expectedModel.deletePerson(personToDelete);

        if (deleteCommand.deleteAllSchedulesFromPerson(expectedModel, personToDelete)) {
            set.add(ModelTypes.SCHEDULES_LIST);
        }
        if (deleteCommand.deleteAllExpensesFromPerson(expectedModel, personToDelete)) {
            set.add(ModelTypes.EXPENSES_LIST);
        }
        expectedModel.commitMultipleLists(set);

        // delete -> first person deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(expectedModel, commandHistory);
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.execute(expectedModel, commandHistory);
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(), model.getScheduleList(),
                model.getRecruitmentList(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Set<ModelTypes> set = new HashSet<>();
        set.add(ModelTypes.ADDRESS_BOOK);
        expectedModel.deletePerson(personToDelete);

        if (deleteCommand.deleteAllSchedulesFromPerson(expectedModel, personToDelete)) {
            set.add(ModelTypes.SCHEDULES_LIST);
        }
        if (deleteCommand.deleteAllExpensesFromPerson(expectedModel, personToDelete)) {
            set.add(ModelTypes.EXPENSES_LIST);
        }
        expectedModel.commitMultipleLists(set);
        expectedModel.updateFilteredScheduleList(Model.PREDICATE_SHOW_ALL_SCHEDULES);
        expectedModel.updateFilteredExpensesList(Model.PREDICATE_SHOW_ALL_EXPENSES);

        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        //expectedModel.undoAddressBook();
        Set<ModelTypes> myModelUndoSet = expectedModel.getLastCommitType();
        for (ModelTypes myModel : myModelUndoSet) {

            switch(myModel) {
            case SCHEDULES_LIST:
                if (expectedModel.canUndoScheduleList()) {
                    expectedModel.undoScheduleList();
                    expectedModel.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
                }
                break;

            case EXPENSES_LIST:
                if (expectedModel.canUndoExpensesList()) {
                    expectedModel.undoExpensesList();
                    expectedModel.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
                }
                break;

            case RECRUITMENT_LIST:
                if (expectedModel.canUndoRecruitmentList()) {
                    expectedModel.undoRecruitmentList();
                    expectedModel.updateFilteredRecruitmentList(PREDICATE_SHOW_ALL_RECRUITMENT);
                }
                break;

            case ADDRESS_BOOK:
                if (expectedModel.canUndoAddressBook()) {
                    expectedModel.undoAddressBook();
                    expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                }
                break;

            default:
                break;
            }
        }
        expectedModel.undoModelList();

        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second person in unfiltered person list
        expectedModel.redoAddressBook();
        //expectedModel.redoScheduleList();

        Set<ModelTypes> myModelRedoSet = expectedModel.getNextCommitType();

        for (ModelTypes myModel : myModelRedoSet) {

            switch (myModel) {
            case ADDRESS_BOOK:
                if (expectedModel.canRedoAddressBook()) {
                    expectedModel.redoAddressBook();
                    expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                }
                break;

            case EXPENSES_LIST:
                if (expectedModel.canRedoExpensesList()) {
                    expectedModel.redoExpensesList();
                    expectedModel.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
                }

                break;

            case RECRUITMENT_LIST:
                if (expectedModel.canRedoRecruitmentList()) {
                    expectedModel.redoRecruitmentList();
                    expectedModel.updateFilteredRecruitmentList(PREDICATE_SHOW_ALL_RECRUITMENT);
                }

                break;

            case SCHEDULES_LIST:
                if (expectedModel.canRedoScheduleList()) {
                    expectedModel.redoScheduleList();
                    expectedModel.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
                }
                break;

            default:
                break;
            }
        }
        expectedModel.redoModelList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(unused -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
