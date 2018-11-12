package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecruitmentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUITMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECRUITMENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruitment.Recruitment;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteRecruitmentPostCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(), getTypicalScheduleList(),
            getTypicalRecruitmentList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recruitment recruitmentToDelete = model.getFilteredRecruitmentList().get(
                INDEX_FIRST_RECRUITMENT.getZeroBased());
        DeleteRecruitmentPostCommand deleteRecruitmentPostCommand = new DeleteRecruitmentPostCommand(
                INDEX_FIRST_RECRUITMENT);

        String expectedMessage = String.format(
                DeleteRecruitmentPostCommand.MESSAGE_DELETE_RECRUITMENT_POST_SUCCESS, recruitmentToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());

        expectedModel.deleteRecruitmentPost(recruitmentToDelete);
        expectedModel.commitRecruitmentPostList();

        try {
            assertCommandSuccess(deleteRecruitmentPostCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecruitmentList().size() + 1);
        DeleteRecruitmentPostCommand deleteRecruitmentPostCommand = new DeleteRecruitmentPostCommand(outOfBoundIndex);

        assertCommandFailure(deleteRecruitmentPostCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECRUITMENT_POST_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecruitmentAtIndex(model, INDEX_FIRST_RECRUITMENT);
        Recruitment recruitmentToDelete = model.getFilteredRecruitmentList().get(
                INDEX_FIRST_RECRUITMENT.getZeroBased());
        DeleteRecruitmentPostCommand deleteRecruitmentPostCommand = new DeleteRecruitmentPostCommand(
                INDEX_FIRST_RECRUITMENT);

        String expectedMessage = String.format(
                DeleteRecruitmentPostCommand.MESSAGE_DELETE_RECRUITMENT_POST_SUCCESS, recruitmentToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());

        expectedModel.deleteRecruitmentPost(recruitmentToDelete);
        expectedModel.commitRecruitmentPostList();
        showNoRecruitment(expectedModel);

        try {
            assertCommandSuccess(deleteRecruitmentPostCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecruitmentAtIndex(model, INDEX_FIRST_RECRUITMENT);

        Index outOfBoundIndex = INDEX_SECOND_RECRUITMENT;
        // ensures that outOfBoundIndex is still in bounds of recruitment post list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecruitmentList().getRecruitmentList().size());

        DeleteRecruitmentPostCommand deleteRecruitmentPostCommandCommand = new DeleteRecruitmentPostCommand(
                outOfBoundIndex);

        assertCommandFailure(deleteRecruitmentPostCommandCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECRUITMENT_POST_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Recruitment recruitmentToDelete = model.getFilteredRecruitmentList().get(
                INDEX_FIRST_RECRUITMENT.getZeroBased());
        DeleteRecruitmentPostCommand deleteRecruitmentPostCommand = new DeleteRecruitmentPostCommand(
                INDEX_FIRST_RECRUITMENT);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(), model.getScheduleList(),
                model.getRecruitmentList(), new UserPrefs());
        expectedModel.deleteRecruitmentPost(recruitmentToDelete);
        expectedModel.commitRecruitmentPostList();

        // delete -> first recruitment post deleted
        deleteRecruitmentPostCommand.execute(model, commandHistory);

        // undo -> reverts recruitment list back to previous state and filtered recruitment list to show all posts
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(expectedModel, commandHistory);
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first post deleted again
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.execute(expectedModel, commandHistory);
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecruitmentList().size() + 1);
        DeleteRecruitmentPostCommand deleteRecruitmentPostCommand = new DeleteRecruitmentPostCommand(outOfBoundIndex);

        // execution failed -> recruitment list state not added into model
        assertCommandFailure(deleteRecruitmentPostCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECRUITMENT_POST_DISPLAYED_INDEX);

        // single recruitment list state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoRecruitment(Model model) {
        model.updateFilteredRecruitmentList(unused -> false);

        assertTrue(model.getFilteredRecruitmentList().isEmpty());
    }

    @Test
    public void equals() {
        DeleteRecruitmentPostCommand deleteFirstCommand = new DeleteRecruitmentPostCommand(INDEX_FIRST_RECRUITMENT);
        DeleteRecruitmentPostCommand deleteSecondCommand = new DeleteRecruitmentPostCommand(INDEX_SECOND_RECRUITMENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRecruitmentPostCommand deleteFirstCommandCopy = new DeleteRecruitmentPostCommand(INDEX_FIRST_RECRUITMENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
