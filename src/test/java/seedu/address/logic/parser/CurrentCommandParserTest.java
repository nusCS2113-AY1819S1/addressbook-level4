package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WORKOUT;

import org.junit.Test;

import seedu.address.logic.commands.CurrentCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class CurrentCommandParserTest {

    private CurrentCommandParser parser = new CurrentCommandParser();

    @Test
    public void parse_validArgs_returnsCurrentCommand() {
        assertParseSuccess(parser, "1", new CurrentCommand(INDEX_FIRST_WORKOUT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CurrentCommand.MESSAGE_USAGE));
    }
}
