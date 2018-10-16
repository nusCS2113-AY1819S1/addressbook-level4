package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExportAllCommand;

//@@author jitwei98
public class ExportAllCommandParserTest {

    private ExportAllCommandParser parser = new ExportAllCommandParser();

    @Test
    public void parseFiletypeSuccess() {
        final String userInput = "csv";
        ExportAllCommand expectedCommand = new ExportAllCommand(userInput);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseMissingNotNullFieldFailure() {
        // no parameters
        final String userInput = ExportAllCommand.COMMAND_WORD + " ";
        final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAllCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

}
