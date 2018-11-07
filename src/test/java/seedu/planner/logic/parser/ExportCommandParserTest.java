package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DIR;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.planner.model.DirectoryPath.WORKING_DIRECTORY_STRING;

import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.model.DirectoryPath;
import seedu.planner.model.record.Date;
import seedu.planner.testutil.TypicalRecords;

//@author nguyenngoclinhchi
public class ExportCommandParserTest {
    private static final String WHITE_SPACE = " ";
    private ExportExcelCommandParser parser = new ExportExcelCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        Date startDate = TypicalRecords.TYPICAL_START_DATE;
        Date endDate = TypicalRecords.TYPICAL_END_DATE;
        DirectoryPath directoryPath = DirectoryPath.WORKING_DIRECTORY;

        CommandParserTestUtil.assertParseFailure(parser, WHITE_SPACE + PREFIX_DATE + " 3A-3B-19C9    ",
                String.format(Date.MESSAGE_DATE_CONSTRAINTS, ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, WHITE_SPACE + PREFIX_DATE + " 3A-3B-1999 13-4-2020",
                String.format(Date.MESSAGE_DATE_CONSTRAINTS, ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + PREFIX_DATE + " 31-03-1999 31-04-2019 31-12-2020",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT + Messages.MESSAGE_INVALID_DATE_REQUIRED,
                        ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + PREFIX_DIR + " unrealistic\\directory",
                String.format(Messages.MESSAGE_UNREALISTIC_DIRECTORY, ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, WHITE_SPACE + WORKING_DIRECTORY_STRING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, WHITE_SPACE + startDate.value + " " + endDate.value,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser,
                WHITE_SPACE + PREFIX_DATE + endDate.value + " " + startDate.value,
                String.format(Messages.MESSAGE_INVALID_STARTDATE_ENDDATE, ExportExcelCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsExportExcelCommand() {
        String startDate = TypicalRecords.TYPICAL_START_DATE.getValue();
        String endDate = TypicalRecords.TYPICAL_END_DATE.getValue();
        String directoryPath = WORKING_DIRECTORY_STRING;
        //Case 1: return ExportExcelCommand()
        ExportExcelCommand expectedExportExcelCommand1 = new ExportExcelCommand();
        assertParseSuccess(parser,
                           WHITE_SPACE,
                           expectedExportExcelCommand1);
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DIR + WHITE_SPACE,
                           expectedExportExcelCommand1);
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + WHITE_SPACE,
                           expectedExportExcelCommand1);
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + WHITE_SPACE + PREFIX_DIR + WHITE_SPACE,
                           expectedExportExcelCommand1);

        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DIR + WHITE_SPACE + PREFIX_DATE + WHITE_SPACE,
                           expectedExportExcelCommand1);

        //Case 2: return ExportExcelCommand(Date startDate, Date endDate)
        ExportExcelCommand expectedExportExcelCommand2 =
                new ExportExcelCommand(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_END_DATE);
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + TypicalRecords.TYPICAL_START_DATE.value
                        + WHITE_SPACE + TypicalRecords.TYPICAL_END_DATE.value,
                expectedExportExcelCommand2);

        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + TypicalRecords.TYPICAL_START_DATE.value
                        + WHITE_SPACE + TypicalRecords.TYPICAL_END_DATE.value + WHITE_SPACE + PREFIX_DIR,
                expectedExportExcelCommand2);

        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DIR + WHITE_SPACE + PREFIX_DATE + TypicalRecords.TYPICAL_START_DATE.value
                        + WHITE_SPACE + TypicalRecords.TYPICAL_END_DATE.value,
                expectedExportExcelCommand2);

        //Case 3: return ExportExcelCommand(Date startDate, Date startDate)
        ExportExcelCommand expectedExportExcelCommand3 =
                new ExportExcelCommand(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_START_DATE);
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + TypicalRecords.TYPICAL_START_DATE.value
                + WHITE_SPACE + TypicalRecords.TYPICAL_START_DATE.value,
                expectedExportExcelCommand3);
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + TypicalRecords.TYPICAL_START_DATE.value,
                expectedExportExcelCommand3);

        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DIR + WHITE_SPACE + PREFIX_DATE + TypicalRecords.TYPICAL_START_DATE.value,
                expectedExportExcelCommand3);

        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DIR + WHITE_SPACE + PREFIX_DATE
                + TypicalRecords.TYPICAL_START_DATE.value + WHITE_SPACE
                + TypicalRecords.TYPICAL_START_DATE.value + WHITE_SPACE,
                expectedExportExcelCommand3);

        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + TypicalRecords.TYPICAL_START_DATE.value + WHITE_SPACE + PREFIX_DIR,
                expectedExportExcelCommand3);

        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + TypicalRecords.TYPICAL_START_DATE.value + WHITE_SPACE
                        + TypicalRecords.TYPICAL_START_DATE.value + WHITE_SPACE + PREFIX_DIR,
                expectedExportExcelCommand3);

        //Case 4: return ExportExcelCommand(Date startDate, Date endDate, String directory)
        ExportExcelCommand expectedExportExcelCommand4 = new ExportExcelCommand(TypicalRecords.TYPICAL_START_DATE,
                TypicalRecords.TYPICAL_END_DATE, directoryPath);
        assertParseSuccess(parser,
                WHITE_SPACE + PREFIX_DATE + startDate + WHITE_SPACE + endDate + WHITE_SPACE
                        + PREFIX_DIR + directoryPath,
                expectedExportExcelCommand4);
    }
}
