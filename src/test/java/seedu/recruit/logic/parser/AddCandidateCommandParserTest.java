package seedu.recruit.logic.parser;

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
import static seedu.recruit.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.recruit.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.recruit.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.SALARY_DESC_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.recruit.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recruit.testutil.TypicalPersons.AMY;
import static seedu.recruit.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.recruit.logic.commands.AddCandidateCommand;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.candidate.Name;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.tag.Tag;
import seedu.recruit.testutil.PersonBuilder;

public class AddCandidateCommandParserTest {
    private AddCandidateCommandParser parser = new AddCandidateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Candidate expectedCandidate = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCandidateCommand(expectedCandidate));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCandidateCommand(expectedCandidate));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_AMY
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCandidateCommand(expectedCandidate));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCandidateCommand(expectedCandidate));

        // multiple addresses - last recruit accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCandidateCommand(expectedCandidate));

        // multiple tags - all accepted
        Candidate expectedCandidateMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB  + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCandidateCommand(expectedCandidateMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Candidate expectedCandidate = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + JOB_DESC_AMY + EDUCATION_DESC_AMY + SALARY_DESC_AMY,
                new AddCandidateCommand(expectedCandidate));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCandidateCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // missing recruit prefix
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_BOB + AGE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + INVALID_PHONE_DESC
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB  + PHONE_DESC_BOB
                + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid recruit
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB  + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB  + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_BOB + AGE_DESC_BOB  + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + JOB_DESC_BOB + EDUCATION_DESC_BOB + SALARY_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOB_DESC_BOB + EDUCATION_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCandidateCommand.MESSAGE_USAGE));
    }
}
