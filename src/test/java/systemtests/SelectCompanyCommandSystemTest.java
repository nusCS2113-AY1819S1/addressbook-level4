package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recruit.logic.commands.SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS;
import static seedu.recruit.testutil.TestUtil.getCompanyLastIndex;
import static seedu.recruit.testutil.TestUtil.getCompanyMidIndex;
import static seedu.recruit.testutil.TypicalCompanies.KEYWORD_MATCHING_AUDI;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.RedoCompanyBookCommand;
import seedu.recruit.logic.commands.SelectCompanyCommand;
import seedu.recruit.logic.commands.UndoCompanyBookCommand;
import seedu.recruit.model.Model;

@Ignore
public class SelectCompanyCommandSystemTest extends CompanyBookSystemTest {
    @Test
    public void select() {
        /* ------------------------ Perform select operations on the shown unfiltered list -------------------------- */

        /* Case: select the first card in the company list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCompanyCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST);

        /* Case: select the last card in the company list -> selected */
        Index companyCount = getCompanyLastIndex(getModel());
        command = SelectCompanyCommand.COMMAND_WORD + " " + companyCount.getOneBased();
        assertCommandSuccess(command, companyCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCompanyBookCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCompanyBookCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCompanyBookCommand.COMMAND_WORD;
        expectedResultMessage = RedoCompanyBookCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the company list -> selected */
        Index middleIndex = getCompanyMidIndex(getModel());
        command = SelectCompanyCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* ------------------------ Perform select operations on the shown filtered list ---------------------------- */

        /* Case: filtered company list, select index within bounds of recruit book but out of bounds of company list
         * -> rejected
         */
        showCompaniesWithName(KEYWORD_MATCHING_AUDI);
        int invalidIndex = getModel().getCompanyBook().getCompanyList().size();
        assertCommandFailure(SelectCompanyCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);

        /* Case: filtered company list, select index within bounds of recruit book and company list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredCompanyList().size());
        command = SelectCompanyCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* ----------------------------------- Perform invalid select operations ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCompanyCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCompanyCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCompanyCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCompanyCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredCompanyList().size() + 1;
        assertCommandFailure(SelectCompanyCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCompanyCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCompanyCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCompanyCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCompanyCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty recruit book -> rejected */
        deleteAllCompanies();
        assertCommandFailure(SelectCompanyCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected company.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code CompanyBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CompanyBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see CompanyBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_COMPANY_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getCompanyJobDetailsPanel().getSelectedCardIndex();

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
     * {@code CompanyBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CompanyBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
