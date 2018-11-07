package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DIR;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;

import org.junit.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.util.ExcelUtil;
import seedu.planner.logic.commands.ImportExcelCommand;
import seedu.planner.model.DirectoryPath;

//@author nguyenngoclinhchi
public class ImportExcelCommandParserTest {
    private static final String WHITE_SPACE = " ";
    private static final String directoryPath = DirectoryPath.HOME_DIRECTORY_STRING;
    private static final String nameFile = "Financial_Planner_ALL";
    private static final String directoryPathName = ExcelUtil.setPathFile(nameFile, directoryPath);

    private ImportExcelCommandParser parser = new ImportExcelCommandParser();

    @Test
    public void parse_invalidArgs_throwParseException() {
        String unrealisticName = "T09-04-Main";
        String unrealisticDirectory = "We:\\are\\the\\best";

        CommandParserTestUtil.assertParseFailure(parser, directoryPathName,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ImportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + PREFIX_DIR + directoryPath,
                Messages.MESSAGE_UNREALISTIC_DIRECTORY);

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + PREFIX_DIR + directoryPath + WHITE_SPACE + nameFile,
                Messages.MESSAGE_UNREALISTIC_DIRECTORY);

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + directoryPath + WHITE_SPACE + PREFIX_NAME + nameFile,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ImportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + directoryPath + WHITE_SPACE + nameFile,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ImportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + PREFIX_NAME + nameFile,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ImportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + unrealisticDirectory,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ImportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + PREFIX_DIR + unrealisticDirectory,
                Messages.MESSAGE_UNREALISTIC_DIRECTORY);

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + PREFIX_DIR + directoryPath + WHITE_SPACE + PREFIX_NAME + unrealisticName,
                Messages.MESSAGE_UNREALISTIC_DIRECTORY);
    }
}
