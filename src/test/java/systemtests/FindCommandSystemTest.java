package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.planner.commons.core.Messages.MESSAGE_RECORDS_LISTED_OVERVIEW;
import static seedu.planner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.KEYWORD_MATCHING_BURSARY;
import static seedu.planner.testutil.TypicalRecords.RANDOM;
import static seedu.planner.testutil.TypicalRecords.WORK;
import static seedu.planner.testutil.TypicalRecords.ZT;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.FindCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.model.Model;
import seedu.planner.model.tag.Tag;

@Ignore
public class FindCommandSystemTest extends FinancialPlannerSystemTest {

    @Test
    public void find() {
        /* Case: find multiple records in planner book, command with leading spaces and trailing spaces
         * -> 2 records found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BURSARY + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, WORK, RANDOM); // this names contain "Income"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where record list is displaying the records we are finding
         * -> 2 records found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BURSARY;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find record where record list is not displaying the record we are finding -> 1 record found */
        command = FindCommand.COMMAND_WORD + " caifan";
        ModelHelper.setFilteredList(expectedModel, CAIFAN);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple records in planner book, 2 keywords -> 2 records found */
        command = FindCommand.COMMAND_WORD + " ZT caifan";
        ModelHelper.setFilteredList(expectedModel, CAIFAN, ZT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple records in planner book, 2 keywords in reversed order -> 2 records found */
        command = FindCommand.COMMAND_WORD + " caifan ZT";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple records in planner book, 2 keywords with 1 repeat -> 2 records found */
        command = FindCommand.COMMAND_WORD + " work ZT work";
        ModelHelper.setFilteredList(expectedModel, ZT, WORK);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple records in planner book, 2 matching keywords and 1 non-matching keyword
         * -> 2 records found
         */
        command = FindCommand.COMMAND_WORD + " work ZT NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same records in planner book after deleting 1 of them -> 1 record found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getFinancialPlanner().getRecordList().contains(WORK));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BURSARY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, RANDOM);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find record in planner book, keyword is same as name but of different case -> 1 record found */
        command = FindCommand.COMMAND_WORD + " rAndOm";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find record in planner book, keyword is substring of name -> 0 records found */
        command = FindCommand.COMMAND_WORD + " ran";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find record in planner book, name is substring of keyword -> 0 records found */
        command = FindCommand.COMMAND_WORD + " inc";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find record not in planner book -> 0 records found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find date of record in planner book -> 0 records found */
        command = FindCommand.COMMAND_WORD + " " + ZT.getDate().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find money flow of record in planner book -> 0 records found */
        command = FindCommand.COMMAND_WORD + " " + ZT.getMoneyFlow().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of record in planner book -> 0 records found */
        List<Tag> tags = new ArrayList<>(ZT.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a record is selected -> selected card deselected */
        showAllRecords();
        selectRecord(Index.fromOneBased(1));
        assertFalse(getRecordListPanel().getHandleToSelectedCard().getName().equals(ZT.getName().fullName));
        command = FindCommand.COMMAND_WORD + " ZT";
        ModelHelper.setFilteredList(expectedModel, ZT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find record in empty planner book -> 0 records found */
        deleteAllRecords();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BURSARY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ZT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Income";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_RECORDS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_RECORDS_LISTED_OVERVIEW, expectedModel.getFilteredRecordList().size());


        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
