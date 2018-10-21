package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.CommandTestUtil.ILLOGICAL_DATE;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_DATE;
import static seedu.planner.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_END;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_START;
import static seedu.planner.logic.commands.SummaryByDateCommand.MESSAGE_USAGE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.planner.model.record.Date.MESSAGE_DATE_CONSTRAINTS;
import static seedu.planner.model.record.Date.MESSAGE_DATE_LOGICAL_CONSTRAINTS;

import org.junit.Test;

import seedu.planner.logic.commands.SummaryByDateCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.model.record.Date;

public class SummaryByDateCommandParserTest {
    private SummaryByDateCommandParser parser = new SummaryByDateCommandParser();

    /**
     * Tests the standard summary command. Detailed testing for variations in Date representations are
     * covered in greater detail in {@code DateTest}
     */
    @Test
    public void parse_validFields_success() {
        SummaryCommand expectedCommand = new SummaryByDateCommand(
                new Date(VALID_DATE_START), new Date(VALID_DATE_END));

        String test = " " + PREFIX_DATE + " " + VALID_DATE_START + " " + VALID_DATE_END;
        assertParseSuccess(parser, test, expectedCommand);

        // leading and trailing whitespaces do not affect
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_DATE_START
                + PREAMBLE_WHITESPACE + VALID_DATE_END + PREAMBLE_WHITESPACE;
        assertParseSuccess(parser, test, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // empty command argument
        String test = PREAMBLE_WHITESPACE;
        assertParseFailure(parser, test, expectedMessage);

        // missing date prefix
        test = PREAMBLE_WHITESPACE + " " + VALID_DATE_START + PREAMBLE_WHITESPACE + VALID_DATE_END;
        assertParseFailure(parser, test, expectedMessage);

        // incorrect prefix
        test = PREAMBLE_WHITESPACE + " " + PREFIX_MONEYFLOW + " " + VALID_DATE_START + " " + VALID_DATE_END;
        assertParseFailure(parser, test, expectedMessage);
    }

    @Test
    public void parse_invalidArgument_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // Single argument after prefix
        String test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_DATE_END;
        assertParseFailure(parser, test, expectedMessage);

        // More than 2 argument after prefix
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_DATE_START
                + PREAMBLE_WHITESPACE + VALID_DATE_START + PREAMBLE_WHITESPACE + VALID_DATE_END;
        assertParseFailure(parser, test, expectedMessage);

        // Wrong order of arguments
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_DATE_END
                + PREAMBLE_WHITESPACE + VALID_DATE_START;
        assertParseFailure(parser, test, expectedMessage);

        // No arguments
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE;
        assertParseFailure(parser, test, expectedMessage);
    }

    @Test
    public void parse_invalidOrIllogicalDate_failure() {
        // Invalid Date format for any field will result in exceptions
        String test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + INVALID_DATE
                + PREAMBLE_WHITESPACE + INVALID_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_CONSTRAINTS);
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + INVALID_DATE
                + PREAMBLE_WHITESPACE + VALID_DATE_END;
        assertParseFailure(parser, test, MESSAGE_DATE_CONSTRAINTS);
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_DATE_START
                + PREAMBLE_WHITESPACE + INVALID_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_CONSTRAINTS);

        // Invalid Date for any field will result in exceptions
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + ILLOGICAL_DATE
                + PREAMBLE_WHITESPACE + VALID_DATE_END;
        assertParseFailure(parser, test, MESSAGE_DATE_LOGICAL_CONSTRAINTS);
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + VALID_DATE_START
                + PREAMBLE_WHITESPACE + ILLOGICAL_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_LOGICAL_CONSTRAINTS);
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + ILLOGICAL_DATE
                + PREAMBLE_WHITESPACE + ILLOGICAL_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_LOGICAL_CONSTRAINTS);

        // Exception thrown will depend on which exception is thrown first
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + INVALID_DATE
                + PREAMBLE_WHITESPACE + ILLOGICAL_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_CONSTRAINTS);
        test = PREAMBLE_WHITESPACE + " " + PREFIX_DATE + PREAMBLE_WHITESPACE + ILLOGICAL_DATE
                + PREAMBLE_WHITESPACE + INVALID_DATE;
        assertParseFailure(parser, test, MESSAGE_DATE_LOGICAL_CONSTRAINTS);
    }
}
