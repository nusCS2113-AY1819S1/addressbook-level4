package systemtests;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONEYFLOW_EXPENSE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONEYFLOW_INCOME_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.testutil.TypicalRecords.AMY;
import static seedu.planner.testutil.TypicalRecords.BOB;
import static seedu.planner.testutil.TypicalRecords.BURSARY;
import static seedu.planner.testutil.TypicalRecords.IDA;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.KEYWORD_MATCHING_BURSARY;
import static seedu.planner.testutil.TypicalRecords.ZT;

import org.junit.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.AddCommand;
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

public class AddCommandSystemTest extends FinancialPlannerSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a record without tags to a non-empty planner book, command with leading spaces and trailing spaces
         * -> added
         */
        Record toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + DATE_DESC_AMY + " "
                + MONEYFLOW_INCOME_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addRecord(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a record with all fields same as another record in the planner book except name -> added */
        toAdd = new RecordBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + DATE_DESC_AMY + MONEYFLOW_INCOME_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a record with all fields same as another record in the planner book except date and income
         * -> added
         */
        toAdd = new RecordBuilder(AMY).withDate(VALID_DATE_BOB).withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB).build();
        command = RecordUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty planner book -> added */
        deleteAllRecords();
        assertCommandSuccess(INDO);

        /* Case: add a record with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + DATE_DESC_BOB + MONEYFLOW_EXPENSE_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + MONEYFLOW_EXPENSE_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a record, missing tags -> added */
        assertCommandSuccess(BURSARY);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the record list before adding -> added */
        showRecordsWithName(KEYWORD_MATCHING_BURSARY);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a record card is selected --------------------------- */

        /* Case: selects first card in the record list, add a record -> added, card selection remains unchanged */
        selectRecord(Index.fromOneBased(1));
        assertCommandSuccess(ZT);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate record -> rejected */
        command = RecordUtil.getAddCommand(BURSARY);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RECORD);

        /* Case: add a duplicate record except with different day parameter -> rejected */
        toAdd = new RecordBuilder(BURSARY).withDate(VALID_DATE_BOB).build();
        command = RecordUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RECORD);

        /* Case: add a duplicate record except with different income -> rejected */
        toAdd = new RecordBuilder(BURSARY).withMoneyFlow(VALID_MONEYFLOW_INCOME_AMY).build();
        command = RecordUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RECORD);

        /* Case: add a duplicate record except with different expense -> rejected */
        toAdd = new RecordBuilder(BURSARY).withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB).build();
        command = RecordUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RECORD);

        /* Case: add a duplicate record except with different tags -> rejected */
        command = RecordUtil.getAddCommand(BURSARY) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RECORD);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + DATE_DESC_AMY + MONEYFLOW_INCOME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing date parameter -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + MONEYFLOW_INCOME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing money flow parameter -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DATE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + RecordUtil.getRecordDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + DATE_DESC_AMY + MONEYFLOW_INCOME_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid date parameter -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_DATE_DESC + MONEYFLOW_INCOME_DESC_AMY;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid money flow parameter -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DATE_DESC_AMY + INVALID_MONEYFLOW_DESC;
        assertCommandFailure(command, MoneyFlow.MESSAGE_MONEY_FLOW_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DATE_DESC_AMY + MONEYFLOW_INCOME_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code RecordListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Record toAdd) {
        assertCommandSuccess(RecordUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Record)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Record)
     */
    private void assertCommandSuccess(String command, Record toAdd) {
        Model expectedModel = getModel();
        expectedModel.addRecord(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Record)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code RecordListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Record)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code RecordListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
