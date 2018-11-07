package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccessWithoutYear;
import static seedu.address.logic.parser.SelectDeadlineCommandParser.arePrefixesPresent;
import static seedu.address.logic.parser.SelectDeadlineCommandParser.parseWithoutPrefixes;
import static seedu.address.testutil.TypicalDeadlines.DAY_DESC_01;
import static seedu.address.testutil.TypicalDeadlines.DAY_DESC_1;
import static seedu.address.testutil.TypicalDeadlines.DAY_DESC_2;
import static seedu.address.testutil.TypicalDeadlines.MONTH_DESC_01;
import static seedu.address.testutil.TypicalDeadlines.MONTH_DESC_1;
import static seedu.address.testutil.TypicalDeadlines.MONTH_DESC_2;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_2018_WITHOUT_PREFIX;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_WITHOUT_PREFIX;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_WITHOUT_YEAR;
import static seedu.address.testutil.TypicalDeadlines.VALID_DAY_1;
import static seedu.address.testutil.TypicalDeadlines.VALID_MONTH_JAN;
import static seedu.address.testutil.TypicalDeadlines.VALID_YEAR_2018;
import static seedu.address.testutil.TypicalDeadlines.YEAR_DESC_02018;
import static seedu.address.testutil.TypicalDeadlines.YEAR_DESC_2018;
import static seedu.address.testutil.TypicalDeadlines.YEAR_DESC_2019;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SelectDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;

//@@author emobeany
public class SelectDeadlineCommandParserTest {
    private static final Logger logger = LogsCenter.getLogger(SelectDeadlineCommandParserTest.class);
    private SelectDeadlineCommandParser parser = new SelectDeadlineCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Parser parser = new SelectDeadlineCommandParser();
        Deadline expectedDeadline = VALID_1ST_JAN_2018;

        // preamble only contains whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple days - last day accepted
        assertParseSuccess(parser, DAY_DESC_2 + DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple months - last month accepted
        assertParseSuccess(parser, DAY_DESC_1 + MONTH_DESC_2 + MONTH_DESC_1 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple years - last year accepted
        assertParseSuccess(parser, DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2019 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // 0 in front of day to be selected - preceding 0 trimmed
        assertParseSuccess(parser, DAY_DESC_01 + MONTH_DESC_1 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // 0 in front of month to be selected - preceding 0 trimmed
        assertParseSuccess(parser, DAY_DESC_1 + MONTH_DESC_01 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // 0 in front of year to be selected - preceding 0 trimmed
        assertParseSuccess(parser, DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_02018,
                new SelectDeadlineCommand(expectedDeadline));
    }

    @Test
    public void parse_emptyYearField_success() {
        ParserWithoutYear parser = new ParserWithoutYear();
        String selectedYear = VALID_YEAR_2018;
        Deadline expectedDeadline = VALID_1ST_JAN_WITHOUT_YEAR;

        // preamble only contains whitespace
        assertParseSuccessWithoutYear(parser, selectedYear, PREAMBLE_WHITESPACE + DAY_DESC_1 + MONTH_DESC_1,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple days - last day accepted
        assertParseSuccessWithoutYear(parser, selectedYear, DAY_DESC_2 + DAY_DESC_1 + MONTH_DESC_1,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple months - last month accepted
        assertParseSuccessWithoutYear(parser, selectedYear, DAY_DESC_1 + MONTH_DESC_2 + MONTH_DESC_1,
                new SelectDeadlineCommand(expectedDeadline));
    }

    //Test dd/mm/yyyy format
    @Test
    public void parse_allFieldsPresentWithoutPrefix_success() {
        Parser parser = new SelectDeadlineCommandParser();

        // preamble only contains whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_1ST_JAN_2018_WITHOUT_PREFIX,
                new SelectDeadlineCommand(VALID_1ST_JAN_2018));
    }

    //Test dd/mm format
    @Test
    public void parse_emptyYearFieldWithoutPrefix_success() {
        ParserWithoutYear parser = new ParserWithoutYear();

        // preamble only contains whitespace
        assertParseSuccessWithoutYear(parser, VALID_YEAR_2018, PREAMBLE_WHITESPACE
                + VALID_1ST_JAN_WITHOUT_PREFIX , new SelectDeadlineCommand(VALID_1ST_JAN_WITHOUT_YEAR));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeadlineCommand.MESSAGE_USAGE);

        // missing day prefix
        assertParseFailure(parser, VALID_DAY_1 + MONTH_DESC_1 + YEAR_DESC_2018, expectedMessage);

        // missing month prefix
        assertParseFailure(parser, DAY_DESC_1 + VALID_MONTH_JAN + YEAR_DESC_2018, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2018,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeadlineCommand.MESSAGE_USAGE));
    }

    /**
     * Since SelectDeadlineCommand can only work with ModelManager
     * which sets the year if it was missing in the userInput, parsing has to do the
     * setting of year here.
     */
    public static class ParserWithoutYear extends SelectDeadlineCommandParserTest {
        private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

        /**
         * An overloading method that parses
         *
         * @param userInput with day and month
         * @param year      will be set in the deadline
         * @return SelectDeadlineCommand
         * @throws ParseException if parsing is invalid
         */
        public Command parse(String userInput, String year) throws ParseException {

            Deadline deadlineWithoutPrefixes = parseWithoutPrefixes(userInput);
            if (deadlineWithoutPrefixes != null) {
                deadlineWithoutPrefixes.setYear(year);
                return new SelectDeadlineCommand(deadlineWithoutPrefixes);
            }
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(userInput, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR);
            if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SelectDeadlineCommand.MESSAGE_USAGE));
            }

            String day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).orElse(""));
            String month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).orElse(""));

            if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
                year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
            } else {
                Deadline deadline = new Deadline(day, month);
                deadline.setYear(year);
                return new SelectDeadlineCommand(deadline);
            }
            Deadline deadline = new Deadline(day, month, year);
            return new SelectDeadlineCommand(deadline);
        }
    }
}
