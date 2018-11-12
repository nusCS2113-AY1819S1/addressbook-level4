package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.OpenCommand;

/**
 * Test scope: similar to {@code SaveCommandParserTest}.
 * @see SaveCommandParserTest
 */
public class OpenCommandParserTest {

    private OpenCommandParser parser = new OpenCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, ":", String.format(OpenCommand.MESSAGE_INVALID_FILE_NAME,
                OpenCommand.MESSAGE_USAGE));
    }
}
