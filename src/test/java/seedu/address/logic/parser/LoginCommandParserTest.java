package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;

public class LoginCommandParserTest {

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_validArgs_returnLoginCommand() {
        String firstInput = "manager";
        String secondInput = "employee";
        String thirdInput = "alice@example.com";

        LoginCommand firstExpectedCommand = new LoginCommand(firstInput, 2);
        LoginCommand secondExpectedCommand = new LoginCommand(secondInput, 3);
        LoginCommand thirdExpectedCommand = new LoginCommand(thirdInput, 1);

        // whitespace -> accepted
        assertParseSuccess(parser, "manager", firstExpectedCommand);
        assertParseSuccess(parser, "employee", secondExpectedCommand);
        assertParseSuccess(parser, " manager", firstExpectedCommand);
        assertParseSuccess(parser, " manager ", firstExpectedCommand);
        assertParseSuccess(parser, " employee", secondExpectedCommand);
        assertParseSuccess(parser, " employee ", secondExpectedCommand);
        assertParseSuccess(parser, "as alice@example.com", thirdExpectedCommand);
        assertParseSuccess(parser, " as alice@example.com", thirdExpectedCommand);
        assertParseSuccess(parser, " as  alice@example.com ", thirdExpectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        assertParseFailure(parser, "m@nager", LoginCommand.MESSAGE_INVALID_LOGIN);
        assertParseFailure(parser, "empl0yee", LoginCommand.MESSAGE_INVALID_LOGIN);
        assertParseFailure(parser, "@s alice@example.com", LoginCommand.MESSAGE_INVALID_LOGIN);
        assertParseFailure(parser, "as alice.com", LoginCommand.MESSAGE_INVALID_LOGIN);
        assertParseFailure(parser, "as @example.com", LoginCommand.MESSAGE_INVALID_LOGIN);
    }

}
