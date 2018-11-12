package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MATCH1;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_MATCH1;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_MATCH1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MATCH1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MATCH1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MATCH1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MATCH1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MatchScheduleCommand;
import seedu.address.model.person.TheDate;
import seedu.address.model.person.Time;

public class MatchScheduleCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchScheduleCommand.MESSAGE_USAGE);

    private MatchScheduleCommandParser parser = new MatchScheduleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DATE_DESC_MATCH1 + START_TIME_DESC_MATCH1 + END_TIME_DESC_MATCH1,
                MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, INDEX_DESC_MATCH1, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndexes_failure() {
        // negative index
        assertParseFailure(parser, DATE_DESC_MATCH1 + START_TIME_DESC_MATCH1 + END_TIME_DESC_MATCH1 + " " +
                PREFIX_INDEX + "-5", ParserUtil.MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, DATE_DESC_MATCH1 + START_TIME_DESC_MATCH1 + END_TIME_DESC_MATCH1 + " " +
                PREFIX_INDEX + "0", ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as index
        assertParseFailure(parser, DATE_DESC_MATCH1 + START_TIME_DESC_MATCH1 + END_TIME_DESC_MATCH1 + " " +
                PREFIX_INDEX + "abc", ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as index
        assertParseFailure(parser, DATE_DESC_MATCH1 + START_TIME_DESC_MATCH1 + END_TIME_DESC_MATCH1 + " " +
                PREFIX_INDEX + "-5 k/bob", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_DATE_DESC + START_TIME_DESC_MATCH1 + END_TIME_DESC_MATCH1 + " " +
                PREFIX_INDEX + "1", TheDate.MESSAGE_DATE_CONSTRAINTS); // invalid date

        assertParseFailure(parser, DATE_DESC_MATCH1 + INVALID_START_TIME_DESC + END_TIME_DESC_MATCH1 + " " +
                PREFIX_INDEX + "1", Time.MESSAGE_TIME_CONSTRAINTS); // invalid start time

        assertParseFailure(parser, DATE_DESC_MATCH1 + START_TIME_DESC_MATCH1 + INVALID_END_TIME_DESC + " " +
                PREFIX_INDEX + "1", Time.MESSAGE_TIME_CONSTRAINTS); // invalid end time

        // valid date followed by invalid date.
        assertParseFailure(parser, DATE_DESC_MATCH1 + INVALID_DATE_DESC + START_TIME_DESC_MATCH1 +
                END_TIME_DESC_MATCH1 + " " + PREFIX_INDEX + "1", TheDate.MESSAGE_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_DATE_DESC + INVALID_START_TIME_DESC + INVALID_END_TIME_DESC + " " +
                PREFIX_INDEX + "1", TheDate.MESSAGE_DATE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput =  DATE_DESC_MATCH1 + START_TIME_DESC_MATCH1 + END_TIME_DESC_MATCH1 + " " +
                PREFIX_INDEX + targetIndex.getOneBased();

        List<Index> listIndex = new ArrayList<>();
        listIndex.add(targetIndex);

        MatchScheduleCommand expectedCommand = new MatchScheduleCommand(new TheDate(VALID_DATE_MATCH1),
                new Time(VALID_START_TIME_MATCH1), new Time (VALID_END_TIME_MATCH1), listIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleIndexSpecified_success() {
        Index targetIndex1 = INDEX_FIRST_PERSON;
        Index targetIndex2 = INDEX_SECOND_PERSON;
        String userInput =  DATE_DESC_MATCH1 + START_TIME_DESC_MATCH1 + END_TIME_DESC_MATCH1 + " " +
                PREFIX_INDEX + targetIndex1.getOneBased() + " " +
                PREFIX_INDEX + targetIndex2.getOneBased();

        List<Index> listIndex = new ArrayList<>();
        listIndex.add(targetIndex1);
        listIndex.add(targetIndex2);

        MatchScheduleCommand expectedCommand = new MatchScheduleCommand(new TheDate(VALID_DATE_MATCH1),
                new Time(VALID_START_TIME_MATCH1), new Time (VALID_END_TIME_MATCH1), listIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput =  INVALID_DATE_DESC + DATE_DESC_MATCH1 + START_TIME_DESC_MATCH1 + END_TIME_DESC_MATCH1 + " " +
                PREFIX_INDEX + targetIndex.getOneBased();

        List<Index> listIndex = new ArrayList<>();
        listIndex.add(targetIndex);

        MatchScheduleCommand expectedCommand = new MatchScheduleCommand(new TheDate(VALID_DATE_MATCH1),
                new Time(VALID_START_TIME_MATCH1), new Time (VALID_END_TIME_MATCH1), listIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
