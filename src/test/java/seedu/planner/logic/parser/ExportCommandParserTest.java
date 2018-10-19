package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;

public class ExportCommandParserTest {
    private ExportExcelCommandParser parser = new ExportExcelCommandParser();

    @Test
    public void parse_emptyOrInvalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser,"  ",
                String.format
                        (Messages.MESSAGE_INVALID_COMMAND_FORMAT, ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, "31-03-1999    ",
                String.format(
                        Messages.MESSAGE_INVALID_COMMAND_FORMAT + Messages.MESSAGE_INVALID_DATE_REQUIRED,
                        ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, "31-03-1999 31-04-2019 31-12-2020",
                String.format(
                        Messages.MESSAGE_INVALID_COMMAND_FORMAT + Messages.MESSAGE_INVALID_DATE_REQUIRED,
                        ExportExcelCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, "31A-03B-1999 13-4-2020",
                String.format(Date.MESSAGE_DATE_CONSTRAINTS));
    }

    @Test
    public void parse_validArgs_returnsExportExcelCommand() {
        // no leading and trailing whitespaces
        ExportExcelCommand expectedExportExcelCommand =
                new ExportExcelCommand(new DateIsWithinIntervalPredicate(new Date("31-03-1999"), new Date("31-3-2019")));
        assertParseSuccess(parser, "31-03-1999 31-3-2019", expectedExportExcelCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 31-03-1999 \n \t 31-3-2019  \t", expectedExportExcelCommand);
    }

}
