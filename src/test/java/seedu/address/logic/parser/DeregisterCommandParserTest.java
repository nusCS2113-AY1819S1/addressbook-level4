package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.LOGIN_INVALID_DESC_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.LOGIN_INVALID_DESC_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.LOGIN_VALID_DESC_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.LOGIN_VALID_DESC_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOGIN_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOGIN_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeregisterCommand;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;

public class DeregisterCommandParserTest {

    private DeregisterCommandParser parser = new DeregisterCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        DeregisterCommand validDeregisterCommand = new DeregisterCommand(new Username(VALID_LOGIN_USERNAME),
                new Password(VALID_LOGIN_PASSWORD));

        //successful input
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD,
                validDeregisterCommand);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD,
                validDeregisterCommand);

        // multiple usernames - last username accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                + LOGIN_VALID_DESC_PASSWORD, validDeregisterCommand);

        // multiple usernames with valid last username - last username accepted
        assertParseSuccess(parser, LOGIN_INVALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                + LOGIN_VALID_DESC_PASSWORD, validDeregisterCommand);

        // multiple passwords with valid last password - last password accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_INVALID_DESC_PASSWORD
                + LOGIN_VALID_DESC_PASSWORD, validDeregisterCommand);

    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeregisterCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, LOGIN_INVALID_DESC_PASSWORD, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, LOGIN_INVALID_DESC_USERNAME, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid username
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD + "@@",
                Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD + "@@",
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeregisterCommand.MESSAGE_USAGE));

    }
}
