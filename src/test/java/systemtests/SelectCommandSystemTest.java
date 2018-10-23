package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recruit.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.recruit.testutil.TestUtil.getLastIndex;
import static seedu.recruit.testutil.TestUtil.getMidIndex;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.RedoCommand;
import seedu.recruit.logic.commands.SelectCommand;
import seedu.recruit.logic.commands.UndoCommand;
import seedu.recruit.model.Model;

@Ignore("not updated with new UI changes")
public class SelectCommandSystemTest extends CandidateBookSystemTest {
    @Test
    public void select() {
        /* ------------------------ Perform select operations on the shown unfiltered list -------------------------- */

        /* Case: select the first card in the candidate list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST);

        /* Case: select the last card in the candidate list -> selected */
        Index personCount = getLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + personCount.getOneBased();
        assertCommandSuccess(command, personCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the candidate list -> selected */
        Index middleIndex = getMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* ------------------------ Perform select operations on the shown filtered list ---------------------------- */

        /* Case: filtered candidate list, select index within bounds of recruit book but out of bounds of candidate list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getCandidateBook().getCandidateList().size();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered candidate list, select index within bounds of recruit book and candidate list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredCandidateList().size());
        command = SelectCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* ----------------------------------- Perform invalid select operations ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredCandidateList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty recruit book -> rejected */
        deleteAllPersons();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected candidate.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code CandidateBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CandidateBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see CandidateBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code CandidateBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CandidateBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
