package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.commands.CommandTestUtil.ADDRESS_DESC_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.ADDRESS_DESC_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.EMAIL_DESC_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.EMAIL_DESC_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.recruit.logic.commands.CommandTestUtil.NAME_DESC_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.NAME_DESC_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.PHONE_DESC_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.PHONE_DESC_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_BMW;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recruit.testutil.TypicalCompanies.BMW;

import org.junit.Test;

import seedu.recruit.logic.commands.AddCompanyCommand;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.company.Company;
import seedu.recruit.testutil.CompanyBuilder;


public class AddCompanyCommandParserTest {
    private AddCompanyCommandParser parser = new AddCompanyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        Company expectedCompany = new CompanyBuilder(BMW).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BMW + ADDRESS_DESC_BMW + EMAIL_DESC_BMW
                + PHONE_DESC_BMW, new AddCompanyCommand(expectedCompany));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_ALFA + NAME_DESC_BMW + ADDRESS_DESC_BMW + EMAIL_DESC_BMW
                + PHONE_DESC_BMW, new AddCompanyCommand(expectedCompany));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BMW + ADDRESS_DESC_BMW + EMAIL_DESC_BMW
                + PHONE_DESC_ALFA + PHONE_DESC_BMW, new AddCompanyCommand(expectedCompany));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BMW + ADDRESS_DESC_BMW + EMAIL_DESC_ALFA + EMAIL_DESC_BMW
                + PHONE_DESC_BMW, new AddCompanyCommand(expectedCompany));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BMW + ADDRESS_DESC_ALFA + ADDRESS_DESC_BMW + EMAIL_DESC_BMW
                + PHONE_DESC_BMW, new AddCompanyCommand(expectedCompany));

    }

    @Test
    public void parse_fieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCompanyCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BMW + ADDRESS_DESC_BMW + EMAIL_DESC_BMW
                + PHONE_DESC_BMW, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BMW + VALID_ADDRESS_BMW + EMAIL_DESC_BMW
                + PHONE_DESC_BMW, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BMW + ADDRESS_DESC_BMW + VALID_EMAIL_BMW
                + PHONE_DESC_BMW, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BMW + ADDRESS_DESC_BMW + EMAIL_DESC_BMW
                + VALID_PHONE_BMW, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BMW + VALID_ADDRESS_BMW + VALID_EMAIL_BMW
                + VALID_PHONE_BMW, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid phone
        assertParseFailure(parser, NAME_DESC_BMW + ADDRESS_DESC_BMW + EMAIL_DESC_BMW
                + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);
        // invalid address
        assertParseFailure(parser, NAME_DESC_BMW + INVALID_ADDRESS_DESC + EMAIL_DESC_BMW
                + PHONE_DESC_BMW, Address.MESSAGE_ADDRESS_CONSTRAINTS);
        // invalid email
        assertParseFailure(parser, NAME_DESC_BMW + ADDRESS_DESC_BMW + INVALID_EMAIL_DESC
                + PHONE_DESC_BMW, Email.MESSAGE_EMAIL_CONSTRAINTS);
    }


}
