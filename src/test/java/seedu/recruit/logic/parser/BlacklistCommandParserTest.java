package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.BlacklistCommand;

public class BlacklistCommandParserTest {

    private BlacklistCommandParser parser = new BlacklistCommandParser();

    @Test
    public void parse_validArguments_success() {
        Index index = Index.fromOneBased(1);
        assertParseSuccess(parser, "1", new BlacklistCommand(false, index));
        assertParseSuccess(parser, "rm 1", new BlacklistCommand(true, index));
    }

    @Test
    public void parse_invalidArguments_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlacklistCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "a", expectedMessage);
        assertParseFailure(parser, "0", expectedMessage);
        assertParseFailure(parser, " ", expectedMessage);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, "rm", expectedMessage);
        assertParseFailure(parser, "rm 0", expectedMessage);
        assertParseFailure(parser, "1 rm", expectedMessage);
    }
}
