package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFileNames.TYPICAL_FILE_NAME_1;

import org.junit.Test;

import seedu.address.logic.commands.SaveCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SaveCommandParserTest {

    private SaveCommandParser parser = new SaveCommandParser();

    @Test
    public void parse_validArgs_returnsSaveCommand() {
        assertParseSuccess(parser, TYPICAL_FILE_NAME_1, new SaveCommand(TYPICAL_FILE_NAME_1 + ".xml"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, ":", String.format(SaveCommand.MESSAGE_INVALID_FILE_NAME,
                SaveCommand.MESSAGE_USAGE));
    }
}
