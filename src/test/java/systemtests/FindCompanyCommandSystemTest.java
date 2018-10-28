package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.recruit.commons.core.Messages.MESSAGE_COMPANIES_LISTED_OVERVIEW;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recruit.testutil.TypicalCompanies.AUDI;
import static seedu.recruit.testutil.TypicalCompanies.BENTLEY;
import static seedu.recruit.testutil.TypicalCompanies.CHEVROLET;
import static seedu.recruit.testutil.TypicalCompanies.DODGE;
import static seedu.recruit.testutil.TypicalCompanies.KEYWORD_MATCHING_WHEELER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.DeleteCommand;
import seedu.recruit.logic.commands.FindCompanyCommand;
import seedu.recruit.logic.commands.RedoCommand;
import seedu.recruit.logic.commands.UndoCommand;
import seedu.recruit.model.Model;
import seedu.recruit.model.tag.Tag;

@Ignore("not updated with new UI changes")
public class FindCompanyCommandSystemTest extends CompanyBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple companies in recruit book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCompanyCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_WHEELER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setCompanyFilteredList(expectedModel, AUDI, BENTLEY); // first names of Audi and Bentley are "Wheeler"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where candidate list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCompanyCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_WHEELER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /** Case: find company where company list is not
         *  displaying the company we are finding -> 1 company found */
        command = FindCompanyCommand.COMMAND_WORD + " Chevrolet";
        ModelHelper.setCompanyFilteredList(expectedModel, CHEVROLET);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple companies in recruit book, 2 keywords -> 2 companies found */
        command = FindCompanyCommand.COMMAND_WORD + " Bentley Dodge";
        ModelHelper.setCompanyFilteredList(expectedModel, BENTLEY, DODGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple companies in recruit book, 2 companies in reversed order -> 2 companies found */
        command = FindCompanyCommand.COMMAND_WORD + " Dodge Bentley";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple companies in recruit book, 2 keywords with 1 repeat -> 2 companies found */
        command = FindCompanyCommand.COMMAND_WORD + " Dodge Bentley Dodge";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple companies in recruit book, 2 matching keywords and 1 non-matching keyword
         * -> 2 companies found
         */
        command = FindCompanyCommand.COMMAND_WORD + " Dodge Bentley NonMatchingKeyWord";
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

        /* Case: find same companies in recruit book after deleting 1 of them -> 1 company found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getCandidateBook().getCandidateList().contains(BENTLEY));
        command = FindCompanyCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_WHEELER;
        expectedModel = getModel();
        ModelHelper.setCompanyFilteredList(expectedModel, DODGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find company in recruit book, keyword is same as name but of different case -> 1 company found */
        command = FindCompanyCommand.COMMAND_WORD + " WhEeLeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find company in recruit book, keyword is substring of name -> 0 company found */
        command = FindCompanyCommand.COMMAND_WORD + " Whe";
        ModelHelper.setCompanyFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find company in recruit book, name is substring of keyword -> 0 companies found */
        command = FindCompanyCommand.COMMAND_WORD + " Wheele";
        ModelHelper.setCompanyFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find company not in recruit book -> 0 companies found */
        command = FindCompanyCommand.COMMAND_WORD + " Mercedes";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of companies in recruit book -> 0 companies found */
        command = FindCompanyCommand.COMMAND_WORD + " " + DODGE.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of companies in recruit book -> 0 companies found */
        command = FindCompanyCommand.COMMAND_WORD + " " + DODGE.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of candidate in recruit book -> 0 companies found */
        command = FindCompanyCommand.COMMAND_WORD + " " + DODGE.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of companies in recruit book -> 0 companies found */
        List<Tag> tags = new ArrayList<>(DODGE.getTags());
        command = FindCompanyCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a company is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getCompanyListPanel().
                getHandleToSelectedCard().getName().equals(DODGE.getCompanyName().value));
        command = FindCompanyCommand.COMMAND_WORD + " Dodge";
        ModelHelper.setCompanyFilteredList(expectedModel, DODGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find company in empty recruit book -> 0 companies found */
        deleteAllPersons();
        command = FindCompanyCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_WHEELER;
        expectedModel = getModel();
        ModelHelper.setCompanyFilteredList(expectedModel, DODGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd WheElER";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_COMPANIES_LISTED_OVERVIEW} with the number of companies in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code CompanyBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see CompanyBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_COMPANIES_LISTED_OVERVIEW, expectedModel.getFilteredCompanyList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code CompanyBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
