package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MONTHANDYEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MONTH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.TimeType;

public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgsWithoutDate_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgsWithDate_success() {
        Index index = INDEX_FIRST_PERSON;
        String userInput = index.getOneBased() + DATE_DESC;
        assertParseSuccess(parser, userInput, new SelectCommand(INDEX_FIRST_PERSON, VALID_DATE, TimeType.DAY));
    }

    @Test
    public void parse_validArgsWithYear_success() {
        Index index = INDEX_FIRST_PERSON;
        String userInput = index.getOneBased() + YEAR_DESC;
        assertParseSuccess(parser, userInput, new SelectCommand(INDEX_FIRST_PERSON, VALID_YEAR, TimeType.YEAR));
    }

    @Test
    public void parse_validArgsWithMonth_success() {
        Index index = INDEX_FIRST_PERSON;
        String userInput = index.getOneBased() + MONTH_DESC;
        assertParseSuccess(parser, userInput, new SelectCommand(INDEX_FIRST_PERSON, VALID_MONTH, TimeType.MONTH));
    }

    @Test
    public void parse_validArgsWithMonthAndYear_success() {
        Index index = INDEX_FIRST_PERSON;
        String userInput = index.getOneBased() + MONTHANDYEAR_DESC;
        String content = VALID_YEAR + "-" + VALID_MONTH;
        assertParseSuccess(parser, userInput, new SelectCommand(INDEX_FIRST_PERSON, content, TimeType.MONTH_AND_YEAR));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateArgs_throwsParseException() {
        //incorrect date
        assertParseFailure(parser, "1" + INVALID_DATE_DESC , EventDate.MESSAGE_DATE_CONSTRAINTS);

        //incorrect month
        assertParseFailure(parser, "1" + INVALID_MONTH_DESC , ParserUtil.MESSAGE_INVALID_MONTH);
        assertParseFailure(parser, "1" + YEAR_DESC + INVALID_MONTH_DESC, ParserUtil.MESSAGE_INVALID_MONTH);

        //incorrect year
        assertParseFailure(parser, "1" + INVALID_YEAR_DESC , ParserUtil.MESSAGE_INVALID_YEAR);
        assertParseFailure(parser, "1" + MONTH_DESC + INVALID_YEAR_DESC , ParserUtil.MESSAGE_INVALID_YEAR);

        //date with month or year
        assertParseFailure(parser, "1" + DATE_DESC + MONTH_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + DATE_DESC + YEAR_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + MONTHANDYEAR_DESC + DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
