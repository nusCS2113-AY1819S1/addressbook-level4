package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.planner.testutil.TypicalRecords.TYPICAL_END_DATE;
import static seedu.planner.testutil.TypicalRecords.TYPICAL_START_DATE;

import org.junit.Test;

import seedu.planner.logic.commands.ListCommand;
import seedu.planner.model.record.Date;

public class ListCommandParserTest {

    private static final String WHITESPACE = "\t \n \r";
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ListCommand.MESSAGE_USAGE);

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_nonEmptyArgWithoutDatePrefix_failure() {
        assertParseFailure(parser, " " + TYPICAL_START_DATE + WHITESPACE + TYPICAL_END_DATE,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyArgument_success() {
        assertParseSuccess(parser, WHITESPACE, new ListCommand());
    }

    @Test
    public void parse_oneArgument_success() {
        assertParseSuccess(parser, " " + PREFIX_DATE + WHITESPACE
                + TYPICAL_START_DATE, new ListCommand(TYPICAL_START_DATE, TYPICAL_START_DATE));
    }


    @Test
    public void parse_twoArgumentWithDatePrefixInCorrectOrder_success() {
        ListCommand expectedListCommand = new ListCommand(TYPICAL_START_DATE, TYPICAL_END_DATE);
        assertParseSuccess(parser, " " + PREFIX_DATE + WHITESPACE + TYPICAL_START_DATE
                + WHITESPACE + TYPICAL_END_DATE, expectedListCommand);
        assertParseSuccess(parser, " " + PREFIX_DATE + WHITESPACE + TYPICAL_START_DATE
                + WHITESPACE + TYPICAL_START_DATE, new ListCommand(TYPICAL_START_DATE, TYPICAL_START_DATE));
    }

    @Test
    public void parse_twoArgumentWithDatePrefixInWrongOrder_failure() {
        assertParseFailure(parser, " " + TYPICAL_END_DATE + WHITESPACE
                + TYPICAL_START_DATE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgumentWithDatePrefix_failure() {
        // More than 2 arguments are not allowed
        assertParseFailure(parser, " " + PREFIX_DATE + WHITESPACE + TYPICAL_START_DATE + WHITESPACE
                + TYPICAL_END_DATE + WHITESPACE + TYPICAL_END_DATE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " " + PREFIX_DATE + WHITESPACE + TYPICAL_START_DATE + WHITESPACE
                + INVALID_DATE, Date.MESSAGE_DATE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_DATE + WHITESPACE + INVALID_DATE + WHITESPACE
                + TYPICAL_END_DATE, Date.MESSAGE_DATE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_DATE + WHITESPACE + INVALID_DATE + WHITESPACE
                + INVALID_DATE, Date.MESSAGE_DATE_CONSTRAINTS);
    }
}
