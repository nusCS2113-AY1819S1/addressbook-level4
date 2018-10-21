package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_FORMAT_MONTH;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_LOGICAL_MONTH;
import static seedu.planner.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONTH_END;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONTH_START;
import static seedu.planner.logic.commands.SummaryByMonthCommand.MESSAGE_USAGE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.planner.model.Month.MESSAGE_MONTH_CONSTRAINTS;
import static seedu.planner.model.Month.MESSAGE_MONTH_LOGICAL_CONSTRAINTS;

import org.junit.Test;

import seedu.planner.logic.commands.SummaryByMonthCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.model.Month;

public class SummaryByMonthCommandParserTest {

    private SummaryByMonthCommandParser parser = new SummaryByMonthCommandParser();

    /**
     * Tests the standard summary command. Detailed testing for variations in month representations are
     * covered in greater detail in {@code MonthTest}
     */
    @Test
    public void parse_validFields_success() {
        SummaryCommand expectedCommand = new SummaryByMonthCommand(
                new Month(VALID_MONTH_START), new Month(VALID_MONTH_END));

        String test = " " + PREFIX_DATE + " " + VALID_MONTH_START + " " + VALID_MONTH_END;
        assertParseSuccess(parser, test, expectedCommand);

        // leading and trailing whitespaces do not affect
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_MONTH_START
                + PREAMBLE_WHITESPACE + VALID_MONTH_END + PREAMBLE_WHITESPACE;
        assertParseSuccess(parser, test, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // empty command argument
        String test = PREAMBLE_WHITESPACE;
        assertParseFailure(parser, test, expectedMessage);

        // missing date prefix
        test = PREAMBLE_WHITESPACE + " " + VALID_MONTH_START + PREAMBLE_WHITESPACE + VALID_MONTH_END;
        assertParseFailure(parser, test, expectedMessage);

        // incorrect prefix
        test = PREAMBLE_WHITESPACE + " " + PREFIX_MONEYFLOW + " " + VALID_MONTH_START + " " + VALID_MONTH_END;
        assertParseFailure(parser, test, expectedMessage);
    }

    @Test
    public void parse_invalidArgument_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // Single argument after prefix
        String test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_MONTH_END;
        assertParseFailure(parser, test, expectedMessage);

        // More than 2 argument after prefix
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_MONTH_START
                + PREAMBLE_WHITESPACE + VALID_MONTH_START + PREAMBLE_WHITESPACE + VALID_MONTH_END;
        assertParseFailure(parser, test, expectedMessage);

        // Wrong order of arguments
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_MONTH_END
                + PREAMBLE_WHITESPACE + VALID_MONTH_START;
        assertParseFailure(parser, test, expectedMessage);

        // No arguments
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE;
        assertParseFailure(parser, test, expectedMessage);
    }

    @Test
    public void parse_invalidOrIllogicalMonth_failure() {
        // Invalid month format for any field will result in exceptions
        String test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + INVALID_FORMAT_MONTH
                + PREAMBLE_WHITESPACE + INVALID_FORMAT_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_CONSTRAINTS);
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + INVALID_FORMAT_MONTH
                + PREAMBLE_WHITESPACE + VALID_MONTH_END;
        assertParseFailure(parser, test, MESSAGE_MONTH_CONSTRAINTS);
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_MONTH_START
                + PREAMBLE_WHITESPACE + INVALID_FORMAT_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_CONSTRAINTS);

        // Invalid month for any field will result in exceptions
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + INVALID_LOGICAL_MONTH
                + PREAMBLE_WHITESPACE + VALID_MONTH_END;
        assertParseFailure(parser, test, MESSAGE_MONTH_LOGICAL_CONSTRAINTS);
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_MONTH_START
                + PREAMBLE_WHITESPACE + INVALID_LOGICAL_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_LOGICAL_CONSTRAINTS);
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + INVALID_LOGICAL_MONTH
                + PREAMBLE_WHITESPACE + INVALID_LOGICAL_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_LOGICAL_CONSTRAINTS);

        // Exception thrown will depend on which exception is thrown first
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + INVALID_FORMAT_MONTH
                + PREAMBLE_WHITESPACE + INVALID_LOGICAL_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_CONSTRAINTS);
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + INVALID_LOGICAL_MONTH
                + PREAMBLE_WHITESPACE + INVALID_FORMAT_MONTH;
        assertParseFailure(parser, test, MESSAGE_MONTH_LOGICAL_CONSTRAINTS);
    }
}
