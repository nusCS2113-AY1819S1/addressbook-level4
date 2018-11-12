package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LogoutCommand;

public class LogoutCommandParserTest {
    private LogoutCommandParser parser = new LogoutCommandParser();

    @Test
    public void parse_validArgs_returnLogoutCommand() {
        LogoutCommand logoutcommand = new LogoutCommand();

        // whitespace -> accepted
        assertParseSuccess(parser, "logout", logoutcommand);
        assertParseSuccess(parser, "logout ", logoutcommand);

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        assertParseFailure(parser, "logout me", LogoutCommandParser.MESSAGE_INVALID_LOGOUT);
        assertParseFailure(parser, "logout/", LogoutCommandParser.MESSAGE_INVALID_LOGOUT);
    }
}
