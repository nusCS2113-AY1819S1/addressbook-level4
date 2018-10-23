package seedu.planner.logic.parser;

import static seedu.planner.logic.commands.CommandTestUtil.INVALID_DATE;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.model.record.Date;
import seedu.planner.testutil.TypicalRecords;

public class ExportCommandParserTest {

    private static final String WHITESPACE = "\t \n \r";

    private ExportExcelCommandParser parser = new ExportExcelCommandParser();

    @Test
    public void parse_emptyArg_success() {
        CommandParserTestUtil.assertParseSuccess(parser, WHITESPACE, new ExportExcelCommand());
    }

    @Test
    public void parse_oneDate_success() {
        // one date,  no leading and trailing whitespaces
        ExportExcelCommand expectedExportExcelCommand1 =
                new ExportExcelCommand(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_START_DATE);
        assertParseSuccess(parser, TypicalRecords.TYPICAL_START_DATE.getValue(), expectedExportExcelCommand1);

        // one date,  multiple whitespaces between keywords
        ExportExcelCommand expectedExportExcelCommand2 =
                new ExportExcelCommand(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_START_DATE);
        assertParseSuccess(parser, WHITESPACE + WHITESPACE
                + TypicalRecords.TYPICAL_START_DATE.getValue() + WHITESPACE, expectedExportExcelCommand2);
    }
    @Test
    public void parse_validTwoDateArgsInCorrectOrder_returnsExportExcelCommand() {
        // two different dates, no leading and trailing whitespaces
        ExportExcelCommand expectedExportExcelCommand1 =
                new ExportExcelCommand(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_END_DATE);
        assertParseSuccess(parser, TypicalRecords.TYPICAL_START_DATE.getValue()
                + WHITESPACE + TypicalRecords.TYPICAL_END_DATE.getValue(), expectedExportExcelCommand1);

        // two different dates, multiple whitespaces between keywords
        ExportExcelCommand expectedExportExcelCommand2 =
                new ExportExcelCommand(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_END_DATE);
        assertParseSuccess(parser, WHITESPACE + TypicalRecords.TYPICAL_START_DATE.getValue()
                + WHITESPACE + WHITESPACE + TypicalRecords.TYPICAL_END_DATE.getValue()
                + WHITESPACE, expectedExportExcelCommand2);

        // two same dates, no leading and trailing whitespaces
        ExportExcelCommand expectedExportExcelCommand3 =
                new ExportExcelCommand(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_START_DATE);
        assertParseSuccess(parser, TypicalRecords.TYPICAL_START_DATE.getValue()
                + WHITESPACE + TypicalRecords.TYPICAL_START_DATE.getValue(), expectedExportExcelCommand3);

        // two same dates, multiple whitespaces between keywords
        ExportExcelCommand expectedExportExcelCommand4 =
                new ExportExcelCommand(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_START_DATE);
        assertParseSuccess(parser, WHITESPACE + TypicalRecords.TYPICAL_START_DATE.getValue()
                + WHITESPACE + WHITESPACE + TypicalRecords.TYPICAL_START_DATE.getValue()
                + WHITESPACE, expectedExportExcelCommand4);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, TypicalRecords.TYPICAL_START_DATE.getValue()
                + WHITESPACE + TypicalRecords.TYPICAL_START_DATE + WHITESPACE + TypicalRecords.TYPICAL_END_DATE,
                String.format(
                        Messages.MESSAGE_INVALID_COMMAND_FORMAT + Messages.MESSAGE_INVALID_DATE_REQUIRED,
                        ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, INVALID_DATE
                + WHITESPACE + TypicalRecords.TYPICAL_START_DATE,
                String.format(Date.MESSAGE_DATE_CONSTRAINTS));

        CommandParserTestUtil.assertParseFailure(parser, INVALID_DATE + INVALID_DATE,
                String.format(Date.MESSAGE_DATE_CONSTRAINTS));

    }
}
