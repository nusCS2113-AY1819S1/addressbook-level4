package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.CommandTestUtil.ILLOGICAL_DATE;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_DATE;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_FORMAT_MONTH;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_LOGICAL_MONTH;
import static seedu.planner.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_END;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_START;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONTH_END;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONTH_START;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.planner.model.Month.MESSAGE_MONTH_CONSTRAINTS;
import static seedu.planner.model.Month.MESSAGE_MONTH_LOGICAL_CONSTRAINTS;
import static seedu.planner.model.record.Date.MESSAGE_DATE_CONSTRAINTS;
import static seedu.planner.model.record.Date.MESSAGE_DATE_LOGICAL_CONSTRAINTS;

import org.junit.Test;

import seedu.planner.logic.commands.SummaryByDateCommand;
import seedu.planner.logic.commands.SummaryByMonthCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.model.Month;
import seedu.planner.model.record.Date;

public class SummaryCommandParserTest {

    private final SummaryCommandParser parser = new SummaryCommandParser();
    private final String invalidKeyword = "mth";

    @Test
    public void parse_dateModeValid_success() {
        SummaryCommand expectedCommand = new SummaryByDateCommand(
                new Date(VALID_DATE_START), new Date(VALID_DATE_END));

        String test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " "
                + PREFIX_DATE + " " + VALID_DATE_START + " " + VALID_DATE_END;
        assertParseSuccess(parser, test, expectedCommand);

        // leading and trailing whitespaces do not affect
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + PREAMBLE_WHITESPACE
                + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + " " + VALID_DATE_START
                + PREAMBLE_WHITESPACE + " " + VALID_DATE_END + PREAMBLE_WHITESPACE;
        assertParseSuccess(parser, test, expectedCommand);
    }

    @Test
    public void parse_monthModeValid_success() {
        SummaryCommand expectedCommand = new SummaryByMonthCommand(
                new Month(VALID_MONTH_START), new Month(VALID_MONTH_END));

        String test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD
                + " " + PREFIX_DATE + " " + VALID_MONTH_START + " " + VALID_MONTH_END;
        assertParseSuccess(parser, test, expectedCommand);

        // leading and trailing whitespaces do not affect
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + PREAMBLE_WHITESPACE
                + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + " " + VALID_MONTH_START
                + PREAMBLE_WHITESPACE + " " + VALID_MONTH_END + PREAMBLE_WHITESPACE;
        assertParseSuccess(parser, test, expectedCommand);
    }

    @Test
    public void parse_noKeywordFound_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE);

        // Correct argument but missing "date"
        String test = " " + PREFIX_DATE + " " + VALID_DATE_START + " " + VALID_DATE_END;
        assertParseFailure(parser, test, expectedMessage);

        // Correct argument but missing "month"
        test = " " + PREFIX_DATE + " " + VALID_MONTH_START + " " + VALID_MONTH_END;
        assertParseFailure(parser, test, expectedMessage);
    }

    @Test
    public void parse_wrongKeyword_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE);
        String test = " " + invalidKeyword + " " + PREFIX_DATE + " "
                + VALID_DATE_START + " " + VALID_DATE_END;
        assertParseFailure(parser, test, expectedMessage);
    }

    @Test
    public void parse_invalidPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE);

        // empty command argument
        String test = PREAMBLE_WHITESPACE;
        assertParseFailure(parser, test, expectedMessage);

        // missing date prefix
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " "
                + " " + VALID_DATE_START + " " + VALID_DATE_END;
        assertParseFailure(parser, test, expectedMessage);
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " "
                + " " + VALID_MONTH_START + " " + VALID_MONTH_END;
        assertParseFailure(parser, test, expectedMessage);

        // incorrect prefix
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " "
                + PREFIX_MONEYFLOW + " " + VALID_DATE_START + " " + VALID_DATE_END;
        assertParseFailure(parser, test, expectedMessage);
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " "
                + PREFIX_MONEYFLOW + " " + VALID_MONTH_START + " " + VALID_MONTH_END;
        assertParseFailure(parser, test, expectedMessage);
    }

    @Test
    public void parse_invalidArgumentCount_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE);

        // Single argument after prefix (Date mode)
        String test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + VALID_DATE_END;
        assertParseFailure(parser, test, expectedMessage);

        // Single argument after prefix (Month mode)
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + VALID_MONTH_END;
        assertParseFailure(parser, test, expectedMessage);

        // More than 2 argument after prefix (Date mode)
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + VALID_DATE_START
                + " " + VALID_DATE_START + " " + VALID_DATE_END;
        assertParseFailure(parser, test, expectedMessage);

        // More than 2 argument after prefix (Month mode)
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + VALID_MONTH_START
                + " " + VALID_MONTH_START + " " + VALID_MONTH_END;
        assertParseFailure(parser, test, expectedMessage);

        // No arguments (Date mode)
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " ";
        assertParseFailure(parser, test, expectedMessage);

        // No arguments (Date mode)
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD
                + " " + " " + PREFIX_DATE + " ";
        assertParseFailure(parser, test, expectedMessage);
    }

    @Test
    public void parse_dateModeInvalidArgumentOrder_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryByDateCommand.MESSAGE_USAGE);
        String test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + VALID_DATE_END
                + " " + VALID_DATE_START;
        assertParseFailure(parser, test, expectedMessage);
    }

    @Test
    public void parse_monthModeInvalidArgumentOrder_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryByMonthCommand.MESSAGE_USAGE);
        String test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + " " + PREFIX_DATE + " " + VALID_MONTH_END
                + " " + VALID_MONTH_START;
        assertParseFailure(parser, test, expectedMessage);
    }

    @Test
    public void parse_dateModeInvalidDate_failure() {
        // both dates have invalid format
        String test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + INVALID_DATE
                + " " + INVALID_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_CONSTRAINTS);

        // 1 date has invalid format
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + INVALID_DATE
                + " " + VALID_DATE_END;
        assertParseFailure(parser, test, MESSAGE_DATE_CONSTRAINTS);
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + VALID_DATE_START
                + " " + INVALID_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_CONSTRAINTS);

        // both dates are invalid
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + ILLOGICAL_DATE
                + " " + ILLOGICAL_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_LOGICAL_CONSTRAINTS);

        // 1 date is invalid
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + ILLOGICAL_DATE
                + " " + VALID_DATE_END;
        assertParseFailure(parser, test, MESSAGE_DATE_LOGICAL_CONSTRAINTS);
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + VALID_DATE_START
                + " " + ILLOGICAL_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_LOGICAL_CONSTRAINTS);
    }

    @Test
    public void parse_monthModeInvalidMonth_failure() {
        // both months have invalid format
        String test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + INVALID_FORMAT_MONTH
                + " " + INVALID_FORMAT_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_CONSTRAINTS);

        // 1 month has invalid format
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + INVALID_FORMAT_MONTH
                + " " + VALID_MONTH_END;
        assertParseFailure(parser, test, MESSAGE_MONTH_CONSTRAINTS);
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + VALID_MONTH_START
                + " " + INVALID_FORMAT_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_CONSTRAINTS);

        // both months are invalid
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + INVALID_LOGICAL_MONTH
                + " " + INVALID_LOGICAL_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_LOGICAL_CONSTRAINTS);

        // 1 month is invalid
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + INVALID_LOGICAL_MONTH
                + " " + VALID_MONTH_END;
        assertParseFailure(parser, test, MESSAGE_MONTH_LOGICAL_CONSTRAINTS);
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + VALID_MONTH_START
                + " " + INVALID_LOGICAL_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_LOGICAL_CONSTRAINTS);
    }

    @Test
    public void parse_monthModeInvalidDateInvalidFormat_throwsFirstException() {
        String test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + INVALID_DATE
                + " " + ILLOGICAL_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_CONSTRAINTS);
        test = " " + SummaryByDateCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + ILLOGICAL_DATE
                + " " + INVALID_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_LOGICAL_CONSTRAINTS);
    }

    @Test
    public void parse_monthModeInvalidMonthInvalidFormat_throwsFirstException() {
        String test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + INVALID_FORMAT_MONTH
                + " " + INVALID_LOGICAL_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_CONSTRAINTS);
        test = " " + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + INVALID_LOGICAL_MONTH
                + " " + INVALID_FORMAT_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_LOGICAL_CONSTRAINTS);
    }
}
