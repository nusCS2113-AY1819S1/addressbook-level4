package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.recruit.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalPersons.AMY;
import static seedu.recruit.testutil.TypicalPersons.BOB;
import static seedu.recruit.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.EditCandidateCommand;
import seedu.recruit.logic.commands.RedoCandidateBookCommand;
import seedu.recruit.logic.commands.UndoCandidateBookCommand;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.candidate.Name;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.tag.Tag;
import seedu.recruit.testutil.PersonBuilder;
import seedu.recruit.testutil.PersonUtil;

@Ignore
public class EditCandidateCommandSystemTest extends CandidateBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST;
        String command = " " + EditCandidateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + NAME_DESC_BOB + "  " + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ADDRESS_DESC_BOB + " "
                + TAG_DESC_HUSBAND + " ";
        Candidate editedCandidate = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedCandidate);

        /* Case: undo editing the last candidate in the list -> last candidate restored */
        command = UndoCandidateBookCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCandidateBookCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last candidate in the list -> last candidate edited again */
        command = RedoCandidateBookCommand.COMMAND_WORD;
        expectedResultMessage = RedoCandidateBookCommand.MESSAGE_SUCCESS;
        model.updateCandidate(
                getModel().getFilteredCandidateList().get(INDEX_FIRST.getZeroBased()), editedCandidate);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a candidate with new values same as existing values -> edited */
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a candidate with new values same as another candidate's values but with different name -> edited*/
        assertTrue(getModel().getCandidateBook().getCandidateList().contains(BOB));
        index = INDEX_SECOND;
        assertNotEquals(getModel().getFilteredCandidateList().get(index.getZeroBased()), BOB);
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedCandidate = new PersonBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedCandidate);

        /* Case: edit a candidate with new values same as another candidate's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND;
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedCandidate = new PersonBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        assertCommandSuccess(command, index, editedCandidate);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST;
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Candidate candidateToEdit = getModel().getFilteredCandidateList().get(index.getZeroBased());
        editedCandidate = new PersonBuilder(candidateToEdit).withTags().build();
        assertCommandSuccess(command, index, editedCandidate);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered candidate list, edit index within bounds of recruit book and candidate list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST;
        assertTrue(index.getZeroBased() < getModel().getFilteredCandidateList().size());
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        candidateToEdit = getModel().getFilteredCandidateList().get(index.getZeroBased());
        editedCandidate = new PersonBuilder(candidateToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedCandidate);

        /* Case: filtered candidate list, edit index within bounds of recruit book but out of bounds of candidate list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getCandidateBook().getCandidateList().size();
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* ------------------- Performing edit operation while a candidate card is selected ------------------------ */

        /** Case: selects first card in the candidate list, edit a candidate -> edited, card selection remains unchanged
         *  but browser url changes
         */
        showAllPersons();
        index = INDEX_FIRST;
        selectPerson(index);
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new candidate's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCandidateCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCandidateCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredCandidateList().size() + 1;
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCandidateCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                EditCandidateCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_EMAIL_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid recruit -> rejected */
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCandidateCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a candidate with new values same as another candidate's values -> rejected */
        executeCommand(PersonUtil.getAddCandidateCommand(BOB));
        assertTrue(getModel().getCandidateBook().getCandidateList().contains(BOB));
        index = INDEX_FIRST;
        assertFalse(getModel().getFilteredCandidateList().get(index.getZeroBased()).equals(BOB));
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCandidateCommand.MESSAGE_DUPLICATE_PERSON);

        /** Case: edit a candidate with new values same as another
         *  candidate's values but with different tags -> rejected */
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCandidateCommand.MESSAGE_DUPLICATE_PERSON);

        /** Case: edit a candidate with new values same as another
         *  candidate's values but with different recruit -> rejected */
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCandidateCommand.MESSAGE_DUPLICATE_PERSON);

        /** Case: edit a candidate with new values same as another
        *   candidate's values but with different phone -> rejected */
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCandidateCommand.MESSAGE_DUPLICATE_PERSON);

        /** Case: edit a candidate with new values same as another
         *  candidate's values but with different email -> rejected */
        command = EditCandidateCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCandidateCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Candidate, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCandidateCommandSystemTest#assertCommandSuccess(String, Index, Candidate, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Candidate editedCandidate) {
        assertCommandSuccess(command, toEdit, editedCandidate, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCandidateCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the candidate at index {@code toEdit} being
     * updated to values specified {@code editedCandidate}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCandidateCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Candidate editedCandidate,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateCandidate(expectedModel.getFilteredCandidateList().get(toEdit.getZeroBased()),
                editedCandidate);
        expectedModel.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCandidateCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedCandidate),
                expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCandidateCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
     * {@code CandidateBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CandidateBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see CandidateBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);
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
     * {@code CandidateBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CandidateBookSystemTest#assertApplicationDisplaysExpected(Stri  ng, String, Model)
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
