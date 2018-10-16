package systemtests;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.AGE_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.AGE_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.EDUCATION_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.EDUCATION_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.JOB_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.JOB_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.SALARY_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.recruit.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.recruit.testutil.TypicalPersons.ALICE;
import static seedu.recruit.testutil.TypicalPersons.AMY;
import static seedu.recruit.testutil.TypicalPersons.BOB;
import static seedu.recruit.testutil.TypicalPersons.CARL;
import static seedu.recruit.testutil.TypicalPersons.HOON;
import static seedu.recruit.testutil.TypicalPersons.IDA;
import static seedu.recruit.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.AddCandidateCommand;
import seedu.recruit.logic.commands.RedoCommand;
import seedu.recruit.logic.commands.UndoCommand;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.candidate.Name;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.tag.Tag;
import seedu.recruit.testutil.PersonBuilder;
import seedu.recruit.testutil.PersonUtil;

@Ignore("not updated with new UI changes")
public class AddCandidateCommandSystemTest extends CandidateBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /** Case: add a candidate without tags to a non-empty recruit book, command with leading spaces and trailing
         * space -> added
         */
        Candidate toAdd = AMY;
        String command = "   " + AddCandidateCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + " " +  GENDER_DESC_AMY
                + AGE_DESC_AMY + " " + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   "
                + " " + JOB_DESC_AMY + "  " +  EDUCATION_DESC_AMY + " " + SALARY_DESC_AMY + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addCandidate(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a candidate with all fields same as another candidate in the recruit book except name -> added */
        toAdd = new PersonBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddCandidateCommand.COMMAND_WORD + NAME_DESC_BOB + GENDER_DESC_AMY + AGE_DESC_AMY +  PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a candidate with all fields same as another candidate in the recruit book except phone and email
         * -> added
         */
        toAdd = new PersonBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        command = PersonUtil.getAddCandidateCommand(toAdd);
        assertCommandSuccess(command, toAdd);


        /* Case: add to empty recruit book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a candidate with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCandidateCommand.COMMAND_WORD + TAG_DESC_FRIEND + GENDER_DESC_BOB + AGE_DESC_BOB
                +  PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a candidate, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the candidate list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ----------------------- Perform add operation while a candidate card is selected ------------------------- */

        /* Case: selects first card in the candidate list, add a candidate -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate candidate -> rejected */
        command = PersonUtil.getAddCandidateCommand(HOON);
        assertCommandFailure(command, AddCandidateCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate candidate except with different phone -> rejected */
        toAdd = new PersonBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = PersonUtil.getAddCandidateCommand(toAdd);
        assertCommandFailure(command, AddCandidateCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate candidate except with different email -> rejected */
        toAdd = new PersonBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = PersonUtil.getAddCandidateCommand(toAdd);
        assertCommandFailure(command, AddCandidateCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate candidate except with different recruit -> rejected */
        toAdd = new PersonBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
        command = PersonUtil.getAddCandidateCommand(toAdd);
        assertCommandFailure(command, AddCandidateCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate candidate except with different tags -> rejected */
        command = PersonUtil.getAddCandidateCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCandidateCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCandidateCommand.COMMAND_WORD + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCandidateCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCandidateCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCandidateCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCandidateCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCandidateCommand.MESSAGE_USAGE));

        /* Case: missing recruit -> rejected */
        command = AddCandidateCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCandidateCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "add"
                + " " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCandidateCommand.COMMAND_WORD + INVALID_NAME_DESC + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCandidateCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY + INVALID_PHONE_DESC
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCandidateCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY
                + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid recruit -> rejected */
        command = AddCandidateCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCandidateCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCandidateCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCandidateCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code CandidateBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CandidateBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Candidate toAdd) {
        assertCommandSuccess(PersonUtil.getAddCandidateCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Candidate)}. Executes {@code command}
     * instead.
     * @see AddCandidateCommandSystemTest#assertCommandSuccess(Candidate)
     */
    private void assertCommandSuccess(String command, Candidate toAdd) {
        Model expectedModel = getModel();
        expectedModel.addCandidate(toAdd);
        String expectedResultMessage = String.format(AddCandidateCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Candidate)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCandidateCommandSystemTest#assertCommandSuccess(String, Candidate)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
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
