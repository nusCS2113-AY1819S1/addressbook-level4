package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PATH_TOO_LONG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {
    //this string is 251 char long; enough to cause the ParseException.
    private static final String INVALID_USER_INPUT_PATH_TOO_LONG = "longlonglonglonglonglonglonglonglonglong"
            + "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong"
            + "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong"
            + "longlonglonglonglonglonglonglonglonglonglonglonglon";
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnsImportCommand() {
        String applicationPath = System.getProperty("user.dir");
        String userInput = "filename";
        String fullFileName = "filename.ics";
        String filePath = applicationPath + "\\" + ParserUtil.IMPORT_EXPORT_FOLDER + "\\" + fullFileName;

        assertParseSuccess(parser, userInput, new ImportCommand(Paths.get(filePath)));
    }

    @Test
    public void parse_validArgsForwardSlash_returnsImportCommand() {
        String applicationPath = System.getProperty("user.dir");
        String userInput = "parent_folder/filename";
        String parentFolder = "parent_folder";
        String fullFileName = "filename.ics";
        String filePath =
                applicationPath + "\\" + ParserUtil.IMPORT_EXPORT_FOLDER + "\\" + parentFolder + "\\" + fullFileName;

        assertParseSuccess(parser, userInput, new ImportCommand(Paths.get(filePath)));
    }

    @Test
    public void parse_validArgsBackwardSlash_returnsImportCommand() {
        String applicationPath = System.getProperty("user.dir");
        String userInput = "parent_folder\\filename";
        String parentFolder = "parent_folder";
        String fullFileName = "filename.ics";
        String filePath =
                applicationPath + "\\" + ParserUtil.IMPORT_EXPORT_FOLDER + "\\" + parentFolder + "\\" + fullFileName;

        assertParseSuccess(parser, userInput, new ImportCommand(Paths.get(filePath)));
    }

    @Test
    public void parse_invalidArgsBlank_failure() {
        String applicationPath = System.getProperty("user.dir");
        String userInput = "   ";

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_invalidArgsPathTooLong_failure() {
        String applicationPath = System.getProperty("user.dir");
        String userInput = INVALID_USER_INPUT_PATH_TOO_LONG;

        assertParseFailure(parser, userInput, MESSAGE_PATH_TOO_LONG);
    }


}
