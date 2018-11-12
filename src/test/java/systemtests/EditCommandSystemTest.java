package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_MONEYFLOW_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.MONEYFLOW_EXPENSE_DESC_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.MONEYFLOW_INCOME_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.planner.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONEYFLOW_INCOME_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_RECORDS;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.planner.testutil.TypicalIndexes.INDEX_SECOND_RECORD;
import static seedu.planner.testutil.TypicalRecords.AMY;
import static seedu.planner.testutil.TypicalRecords.BOB;
import static seedu.planner.testutil.TypicalRecords.KEYWORD_MATCHING_BURSARY;

import org.junit.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.EditCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Name;
import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.testutil.RecordUtil;

public class EditCommandSystemTest extends FinancialPlannerSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_RECORD;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + DATE_DESC_BOB + " " + MONEYFLOW_EXPENSE_DESC_BOB + "  " + TAG_DESC_HUSBAND + " ";
        Record editedRecord = new RecordBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedRecord);

        /* Case: undo editing the last record in the list -> last record restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last record in the list -> last record edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateRecord(
                getModel().getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased()), editedRecord);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a record with new values same as existing values but with different tags -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + DATE_DESC_BOB
                + MONEYFLOW_EXPENSE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a record with new values same as another record's values but with different name -> edited */
        assertTrue(getModel().getFinancialPlanner().getRecordList().contains(BOB));
        index = INDEX_SECOND_RECORD;
        assertNotEquals(getModel().getFilteredRecordList().get(index.getZeroBased()), BOB);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + DATE_DESC_BOB
                + MONEYFLOW_EXPENSE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedRecord = new RecordBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedRecord);

        /* Case: edit a record with new values same as another record's values but with different date
         * -> edited
         */
        index = INDEX_SECOND_RECORD;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + DATE_DESC_AMY
                + MONEYFLOW_EXPENSE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedRecord = new RecordBuilder(BOB).withDate(VALID_DATE_AMY).build();
        assertCommandSuccess(command, index, editedRecord);

        /* Case: edit a record with new values same as another record's values but with different moneyflow
         * -> edited
         */
        index = INDEX_SECOND_RECORD;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + DATE_DESC_BOB
                + MONEYFLOW_INCOME_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedRecord = new RecordBuilder(BOB).withMoneyFlow(VALID_MONEYFLOW_INCOME_AMY).build();
        assertCommandSuccess(command, index, editedRecord);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_RECORD;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Record recordToEdit = getModel().getFilteredRecordList().get(index.getZeroBased());
        editedRecord = new RecordBuilder(recordToEdit).withTags().build();
        assertCommandSuccess(command, index, editedRecord);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered record list, edit index within bounds of planner book and record list -> edited */
        showRecordsWithName(KEYWORD_MATCHING_BURSARY);
        index = INDEX_FIRST_RECORD;
        assertTrue(index.getZeroBased() < getModel().getFilteredRecordList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        recordToEdit = getModel().getFilteredRecordList().get(index.getZeroBased());
        editedRecord = new RecordBuilder(recordToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedRecord);

        /* Case: filtered record list, edit index within bounds of planner book but out of bounds of record list
         * -> rejected
         */
        showRecordsWithName(KEYWORD_MATCHING_BURSARY);
        int invalidIndex = getModel().getFinancialPlanner().getRecordList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a record card is selected -------------------------- */

        /* Case: selects first card in the record list, edit a record -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllRecords();
        index = INDEX_FIRST_RECORD;
        selectRecord(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + DATE_DESC_AMY
                + MONEYFLOW_INCOME_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new record's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredRecordList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased()
                + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased()
                + INVALID_DATE_DESC, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid money flow -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased()
                + INVALID_MONEYFLOW_DESC, MoneyFlow.MESSAGE_MONEY_FLOW_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased()
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a record with new values same as another record's values -> rejected */
        executeCommand(RecordUtil.getAddCommand(BOB));
        assertTrue(getModel().getFinancialPlanner().getRecordList().contains(BOB));
        index = INDEX_FIRST_RECORD;
        assertFalse(getModel().getFilteredRecordList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + DATE_DESC_BOB
                + MONEYFLOW_EXPENSE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_RECORD);

        /* Case: edit a record with new values same as another record's values -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + DATE_DESC_BOB
                + MONEYFLOW_EXPENSE_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_RECORD);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Record, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Record, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Record editedRecord) {
        assertCommandSuccess(command, toEdit, editedRecord, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the record at index {@code toEdit} being
     * updated to values specified {@code editedRecord}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Record editedRecord,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateRecord(expectedModel.getFilteredRecordList().get(toEdit.getZeroBased()), editedRecord);
        expectedModel.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedRecord), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see FinancialPlannerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
