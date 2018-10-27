package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExportAllCommand;
import seedu.address.model.Filetype;

//@@author jitwei98
public class ExportAllCommandParserTest {

    private ExportAllCommandParser parser = new ExportAllCommandParser();

    @Test
    public void parseFiletypeSuccess() {
        final String userInput = "csv";
        final Filetype filetype = new Filetype(userInput);
        assertParseSuccess(parser, userInput, new ExportAllCommand(filetype));
    }

    @Test
    public void parseMissingNotNullFieldFailure() {
        // no parameters
        final String userInput = ExportAllCommand.COMMAND_WORD + " ";
        final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAllCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

}
