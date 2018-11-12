package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.ImportCommand;

//@@author jitwei98
public class ImportCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_missingNotNullField_failure() {
        // no parameters
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_success() {
        Path filePath;
        ImportCommand expectedCommand;

        filePath = Paths.get("data", "testImportFile.xml");
        expectedCommand = new ImportCommand(filePath);

        // parse user input without whitespaces
        assertParseSuccess(parser, " testImportFile.xml", expectedCommand);

        // parse user input with whitespaces
        assertParseSuccess(parser, "  testImportFile.xml    ", expectedCommand);
    }

    @Test
    public void parse_invalidFilename_throwsParseException() {
        // wrong filetype
        assertParseFailure(parser, "testImportFile.pdf", MESSAGE_INVALID_FORMAT);

        // no extension
        assertParseFailure(parser, "testImportFile", MESSAGE_INVALID_FORMAT);
    }

}
