package seedu.planner.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.planner.logic.commands.SummaryByDateCommand;
import seedu.planner.logic.commands.SummaryByMonthCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.Month;
import seedu.planner.model.record.Date;

/**
 * This is an integration test to test the interaction between SummaryCommandParser, SummaryCommandByDateParser
 * and SummaryByMonthCommandParser. The detailed tests will be done in {@code SummaryCommandByDateParserTest} and
 * {@code SummaryCommandByMonthParserTest} separately
 */
public class SummaryCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final SummaryCommandParser parser = new SummaryCommandParser();

    @Test
    public void parse_noKeywordFound_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        parser.parse(" summary ");
    }

    @Test
    public void parse_wrongKeyword_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        parser.parse(" summary mth ");
    }

    @Test
    public void parse_dateKeyword_success() throws Exception {
        Date testStartDate = new Date("10-2-2018");
        Date testEndDate = new Date("11-2-2018");
        SummaryCommand expectedCommand = new SummaryByDateCommand(testStartDate, testEndDate);
        assertEquals(parser.parse(SummaryByDateCommand.COMMAND_MODE + " " + PREFIX_DATE + " " + testStartDate.value + " "
                + testEndDate.value), expectedCommand);
    }

    @Test
    public void parse_monthKeyword_success() throws Exception {
        Month testStartMonth = new Month("apr-2018");
        Month testEndMonth = new Month("may-2018");
        SummaryCommand expectedCommand = new SummaryByMonthCommand(testStartMonth, testEndMonth);
        assertEquals(parser.parse(SummaryByMonthCommand.COMMAND_MODE + " " + PREFIX_DATE + " "
                + testStartMonth.value + " " + testEndMonth.value), expectedCommand);
    }

}
