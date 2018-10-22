package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

import static seedu.address.logic.commands.CommandTestUtil.*;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class LoginCommandTestParser {
    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Username username = new Username(VALID_USERNAME);
        Password password = new Password(VALID_PASSWORD);
        User expectedUser = new User(username, password);

        assertParseSuccess(parser, "u/bob p/pass", new LoginCommand(expectedUser));
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
}
