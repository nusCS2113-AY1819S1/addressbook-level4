package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DIR;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.model.DirectoryPath;
import seedu.planner.model.record.Date;
import seedu.planner.testutil.TypicalRecords;

public class ExportCommandParserTest {
    public static final String WHITE_SPACE = "\t \t \t \t";
    private ExportExcelCommandParser parser = new ExportExcelCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        Date startDate = TypicalRecords.TYPICAL_START_DATE;
        Date endDate = TypicalRecords.TYPICAL_END_DATE;
        DirectoryPath directoryPath = DirectoryPath.HOME_DIRECTORY;

        CommandParserTestUtil.assertParseFailure(parser, "d/ 3A-3B-19C9    ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, "d/ 3A-3B-1999 13-4-2020",
                String.format(Date.MESSAGE_DATE_CONSTRAINTS));

        CommandParserTestUtil.assertParseFailure(parser, "d/ 31-03-1999 31-04-2019 31-12-2020",
                String.format(
                        Messages.MESSAGE_INVALID_COMMAND_FORMAT + Messages.MESSAGE_INVALID_DATE_REQUIRED,
                        ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, startDate + " " + endDate,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, PREFIX_DATE + endDate.value + " " + startDate,
                String.format(Messages.MESSAGE_INVALID_STARTDATE_ENDDATE));
    }

    @Test
    public void parse_validArgs_returnsExportExcelCommand() {
        String startDate = TypicalRecords.TYPICAL_START_DATE.getValue();
        String endDate = TypicalRecords.TYPICAL_END_DATE.getValue();
        String directoryPath = DirectoryPath.HOME_DIRECTORY_STRING;
        ExportExcelCommand expectedExportExcelCommand_1 = new ExportExcelCommand();
        // no leading and trailing whitespaces
        assertParseSuccess(parser,
                WHITE_SPACE,
                expectedExportExcelCommand_1);
        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                "",
                expectedExportExcelCommand_1);

        ExportExcelCommand expectedExportExcelCommand_2_1 =
                new ExportExcelCommand(new Date(startDate), new Date(endDate));

        // no leading and trailing whitespaces
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + WHITE_SPACE + startDate + WHITE_SPACE + endDate + WHITE_SPACE,
                expectedExportExcelCommand_1);

        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + WHITE_SPACE + startDate + WHITE_SPACE + endDate,
                expectedExportExcelCommand_1);

        ExportExcelCommand expectedExportExcelCommand_2_2 =
                new ExportExcelCommand(new Date(startDate), new Date(startDate));
        // no leading and trailing whitespaces only 1 date
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + startDate,
                expectedExportExcelCommand_2_2);

        // multiple whitespaces between keywords only 1 date
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + WHITE_SPACE + startDate,
                expectedExportExcelCommand_2_2);
        // no leading and trailing whitespaces 2 dates
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + startDate + " " + startDate,
                expectedExportExcelCommand_2_2);
        // multiple whitespaces between keywords 2 dates
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + WHITE_SPACE + startDate + WHITE_SPACE + WHITE_SPACE + startDate,
                expectedExportExcelCommand_2_2);

        ExportExcelCommand expectedExportExcelCommand_3 = new ExportExcelCommand(directoryPath);
        // no leading and trailing whitespaces
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DIR + directoryPath,
                expectedExportExcelCommand_3);
        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                    WHITE_SPACE + PREFIX_DIR + WHITE_SPACE + directoryPath,
                expectedExportExcelCommand_3);

        ExportExcelCommand expectedExportExcelCommand_4 =
                new ExportExcelCommand(new Date(startDate), new Date(endDate), directoryPath);
        // no leading and trailing whitespaces
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + startDate + " " + endDate + PREFIX_DIR
                + directoryPath,
                expectedExportExcelCommand_4);
        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + WHITE_SPACE + startDate + " " + endDate + PREFIX_DIR + WHITE_SPACE
                + WHITE_SPACE + directoryPath,
                expectedExportExcelCommand_4);
    }
}
