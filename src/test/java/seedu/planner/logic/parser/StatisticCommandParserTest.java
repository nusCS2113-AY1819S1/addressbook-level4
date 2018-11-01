package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.planner.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.planner.logic.commands.StatisticCommand;
import seedu.planner.model.record.Date;

public class StatisticCommandParserTest {
    private final String sampleStartDate = "1-1-2018";
    private final String sampleEndDate = "2-9-2018";
    private final String invalidDate = "29-2-2018";
    private final String incorrectFormatDate = "1--1-2018";
    private final String defaultParseErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            StatisticCommand.MESSAGE_USAGE);
    private StatisticCommandParser parser = new StatisticCommandParser();

    @Test
    public void parse_invalidPrefixes_throwParseException() {
        String argsMissingPrefix = " " + sampleStartDate + PREAMBLE_WHITESPACE + sampleEndDate;
        assertParseFailure(parser, argsMissingPrefix, defaultParseErrorMessage);

        String argsInvalidPrefix = " " + PREFIX_MONEYFLOW + PREAMBLE_WHITESPACE + sampleStartDate
                + PREAMBLE_WHITESPACE + sampleEndDate;
        assertParseFailure(parser, argsInvalidPrefix, defaultParseErrorMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_throwParseException() {
        String argsNonEmptyPreAmble = PREAMBLE_NON_EMPTY + PREFIX_DATE + PREAMBLE_WHITESPACE + sampleStartDate
                + PREAMBLE_WHITESPACE + sampleEndDate;
        assertParseFailure(parser, argsNonEmptyPreAmble, defaultParseErrorMessage);
    }

    @Test
    public void parse_emptyPreamble_throwParseException() {
        String argsEmpty = " " + PREFIX_DATE;
        assertParseFailure(parser, argsEmpty, defaultParseErrorMessage);
    }

    @Test
    public void parse_nonEmptyArgNotSize2_throwParseException() {
        String argsWithLessThan2 = " " + PREFIX_DATE + PREAMBLE_WHITESPACE + sampleStartDate;
        assertParseFailure(parser, argsWithLessThan2, defaultParseErrorMessage);

        String argsWithMoreThan2 = " " + PREFIX_DATE + PREAMBLE_WHITESPACE + sampleStartDate + PREAMBLE_WHITESPACE
                + sampleEndDate + PREAMBLE_WHITESPACE + sampleEndDate;
        assertParseFailure(parser, argsWithMoreThan2, defaultParseErrorMessage);
    }

    @Test
    public void parse_invalidDateOrder_throwParseException() {
        String argsWrongOrder = " " + PREFIX_DATE + PREAMBLE_WHITESPACE + sampleEndDate + PREAMBLE_WHITESPACE
                + sampleStartDate;
        assertParseFailure(parser, argsWrongOrder, defaultParseErrorMessage);
    }

    @Test
    public void parse_validDateArgs_success() {
        String argsValid = " " + PREFIX_DATE + PREAMBLE_WHITESPACE + sampleStartDate + PREAMBLE_WHITESPACE
                + sampleEndDate;
        assertParseSuccess(parser, argsValid, new StatisticCommand(new Date(sampleStartDate), new Date(sampleEndDate)));

        argsValid = " " + PREFIX_DATE + PREAMBLE_WHITESPACE + sampleStartDate + PREAMBLE_WHITESPACE
                + sampleStartDate;
        assertParseSuccess(parser, argsValid, new StatisticCommand(new Date(sampleStartDate),
                new Date(sampleStartDate)));
    }
}
