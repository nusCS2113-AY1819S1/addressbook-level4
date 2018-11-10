package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

//@@author jitwei98
public class ExportCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_missingNotNullField_failure() {
        // no parameters
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_success() {
        Path filePath;
        ExportCommand expectedCommand;

        // parse csv filetype
        filePath = Paths.get("data", "testExportFile.xml");
        expectedCommand = new ExportCommand(filePath);

        // parse user input without whitespaces
        assertParseSuccess(parser, " testExportFile.xml", expectedCommand);

        // parse user input with whitespaces
        assertParseSuccess(parser, "  testExportFile.xml    ", expectedCommand);
    }

    @Test
    public void parse_invalidFilename_throwsParseException() {
        // wrong filetype
        assertParseFailure(parser, "testExportFile.pdf", MESSAGE_INVALID_FORMAT);

        // no extension
        assertParseFailure(parser, "testExportFile", MESSAGE_INVALID_FORMAT);
    }

}
