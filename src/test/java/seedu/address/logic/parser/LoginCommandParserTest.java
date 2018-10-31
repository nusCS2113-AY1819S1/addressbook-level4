package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

public class LoginCommandParserTest {
    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Username username = new Username(VALID_USERNAME);
        Password password = new Password(VALID_PASSWORD);
        User expectedUser = new User(username, password);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + USERNAME_DESC + PASSWORD_DESC,
                new LoginCommand(expectedUser));

        // valid username and password
        assertParseSuccess(parser, USERNAME_DESC + PASSWORD_DESC,
                new LoginCommand(expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // missing password prefix
        assertParseFailure(parser, USERNAME_DESC + VALID_PASSWORD, expectedMessage);

        // missing username prefix
        assertParseFailure(parser, VALID_USERNAME + PASSWORD_DESC, expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, VALID_USERNAME + VALID_PASSWORD, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, INVALID_USERNAME_DESC + PASSWORD_DESC,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, USERNAME_DESC + INVALID_PASSWORD_DESC,
                Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_USERNAME_DESC + INVALID_PASSWORD_DESC,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + USERNAME_DESC + PASSWORD_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }
}
