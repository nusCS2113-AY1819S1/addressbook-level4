package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX;
import static seedu.planner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.planner.logic.commands.DeleteCommand.MESSAGE_DELETE_RECORD_SUCCESS;
import static seedu.planner.testutil.TestUtil.getLastIndex;
import static seedu.planner.testutil.TestUtil.getMidIndex;
import static seedu.planner.testutil.TestUtil.getRecord;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.planner.testutil.TypicalRecords.KEYWORD_MATCHING_BURSARY;

import org.junit.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.model.Model;
import seedu.planner.model.record.Record;

public class DeleteCommandSystemTest extends FinancialPlannerSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first record in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_RECORD.getOneBased() + "       ";
        Record deletedRecord = removeRecord(expectedModel, INDEX_FIRST_RECORD);
        String expectedResultMessage = String.format(MESSAGE_DELETE_RECORD_SUCCESS, deletedRecord);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last record in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastRecordIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastRecordIndex);

        /* Case: undo deleting the last record in the list -> last record restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last record in the list -> last record deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeRecord(modelBeforeDeletingLast, lastRecordIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle record in the list -> deleted */
        Index middleRecordIndex = getMidIndex(getModel());
        assertCommandSuccess(middleRecordIndex);

        // Undo previous operation to prepare for further tests

        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered record list, delete index within bounds of financial planner and record list -> deleted */
        showRecordsWithName(KEYWORD_MATCHING_BURSARY);
        Index index = INDEX_FIRST_RECORD;
        assertTrue(index.getZeroBased() < getModel().getFilteredRecordList().size());
        assertCommandSuccess(index);

        /* Case: filtered record list, delete index within bounds of planner book but out of bounds of record list
         * -> rejected
         */
        showRecordsWithName(KEYWORD_MATCHING_BURSARY);
        int invalidIndex = getModel().getFinancialPlanner().getRecordList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a record card is selected ------------------------ */

        /* Case: delete the selected record -> record list panel selects the record before the deleted record */
        showAllRecords();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectRecord(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedRecord = removeRecord(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_RECORD_SUCCESS, deletedRecord);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getFinancialPlanner().getRecordList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Record} at the specified {@code index} in {@code model}'s planner book.
     * @return the removed record
     */
    private Record removeRecord(Model model, Index index) {
        Record targetRecord = getRecord(model, index);
        model.deleteRecord(targetRecord);
        return targetRecord;
    }

    /**
     * Deletes the record at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Record deletedRecord = removeRecord(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_RECORD_SUCCESS, deletedRecord);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the detail card
     * panel and selected card are expected to update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see FinancialPlannerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
