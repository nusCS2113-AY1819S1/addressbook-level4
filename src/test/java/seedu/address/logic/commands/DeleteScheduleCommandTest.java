package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showScheduleAtIndex;
import static seedu.address.model.VersionedModelList.MESSAGE_NO_REDOABLE_STATE_EXCEPTION;
import static seedu.address.model.VersionedModelList.MESSAGE_NO_UNDOABLE_STATE_EXCEPTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SCHEDULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SCHEDULE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedModelList;
import seedu.address.model.schedule.Schedule;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteScheduleCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(), getTypicalScheduleList(),
            getTypicalRecruitmentList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Schedule scheduleToDelete = model.getFilteredScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE);

        String expectedMessage = String.format(
                DeleteScheduleCommand.MESSAGE_DELETE_SCHEDULE_SUCCESS, scheduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());

        expectedModel.deleteSchedule(scheduleToDelete);
        expectedModel.commitScheduleList();

        try {
            assertCommandSuccess(deleteScheduleCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(outOfBoundIndex);

        assertCommandFailure(deleteScheduleCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showScheduleAtIndex(model, INDEX_FIRST_SCHEDULE);
        Schedule scheduleToDelete = model.getFilteredScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE);

        String expectedMessage = String.format(
                DeleteScheduleCommand.MESSAGE_DELETE_SCHEDULE_SUCCESS, scheduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());

        expectedModel.deleteSchedule(scheduleToDelete);
        expectedModel.commitScheduleList();
        showNoSchedule(expectedModel);

        try {
            assertCommandSuccess(deleteScheduleCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showScheduleAtIndex(model, INDEX_FIRST_SCHEDULE);

        Index outOfBoundIndex = INDEX_SECOND_SCHEDULE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getScheduleList().getScheduleList().size());

        DeleteScheduleCommand deleteScheduleCommandCommand = new DeleteScheduleCommand(outOfBoundIndex);

        assertCommandFailure(deleteScheduleCommandCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndo_validIndexUnfilteredList_failure() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(), model.getScheduleList(),
                model.getRecruitmentList(), new UserPrefs());

        // undo -> no more states to undo
        thrown.expect(VersionedModelList.NoUndoableStateException.class);
        thrown.expectMessage(MESSAGE_NO_UNDOABLE_STATE_EXCEPTION);
        expectedModel.getLastCommitType();
        expectedModel.undoModelList();
    }

    @Test
    public void executeRedo_validIndexUnfilteredList_failure() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(), model.getScheduleList(),
                model.getRecruitmentList(), new UserPrefs());
        // redo -> no more states to redo
        thrown.expect(VersionedModelList.NoRedoableStateException.class);
        thrown.expectMessage(MESSAGE_NO_REDOABLE_STATE_EXCEPTION);
        expectedModel.getNextCommitType();
        expectedModel.redoModelList();
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {

        Schedule scheduleToDelete = model.getFilteredScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(), model.getScheduleList(),
                model.getRecruitmentList(), new UserPrefs());
        expectedModel.deleteSchedule(scheduleToDelete);
        expectedModel.commitScheduleList();

        // delete -> first schedule deleted
        deleteScheduleCommand.execute(model, commandHistory);

        // undo -> reverts schedule list back to previous state and filtered schedule list to show all schedules
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
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredScheduleList().size() + 1);
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(outOfBoundIndex);

        // execution failed -> schedule list state not added into model
        assertCommandFailure(deleteScheduleCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);

        // single schedule list state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Schedule} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted schedule in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(), model.getScheduleList(),
                model.getRecruitmentList(), new UserPrefs());

        showScheduleAtIndex(model, INDEX_SECOND_SCHEDULE);
        Schedule scheduleToDelete = model.getFilteredScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());

        expectedModel.deleteSchedule(scheduleToDelete);

        expectedModel.commitScheduleList();
        expectedModel.updateFilteredScheduleList(Model.PREDICATE_SHOW_ALL_SCHEDULES);

        // delete -> deletes second schedule in unfiltered person list / first person in filtered schedule list
        deleteScheduleCommand.execute(model, commandHistory);

        // undo -> reverts schedule list back to previous state and filtered person list to show all schedules
        expectedModel.undoScheduleList();
        expectedModel.undoModelList();

        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(scheduleToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second schedule in unfiltered person list
        expectedModel.redoScheduleList();
        expectedModel.redoModelList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoSchedule(Model model) {
        model.updateFilteredScheduleList(unused -> false);

        assertTrue(model.getFilteredScheduleList().isEmpty());
    }

    @Test
    public void equals() {
        DeleteScheduleCommand deleteFirstCommand = new DeleteScheduleCommand(INDEX_FIRST_PERSON);
        DeleteScheduleCommand deleteSecondCommand = new DeleteScheduleCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteScheduleCommand deleteFirstCommandCopy = new DeleteScheduleCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
