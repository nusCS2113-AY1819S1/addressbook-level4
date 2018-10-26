package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.LoginUserIdPasswordRoleCommand;

public class LoginUserIdPasswordRoleCommandParserTest {

    private LoginUserIdPasswordRoleCommandParser parser = new LoginUserIdPasswordRoleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LoginUserIdPasswordRoleCommand.MESSAGE_USAGE));
    }
}
