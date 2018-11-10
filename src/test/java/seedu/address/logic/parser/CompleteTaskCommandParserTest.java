package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.HOURS_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.HOURS_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOURS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOURS_OVERFLOW;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_DESC_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_DESC_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_OVERFLOW;
import static seedu.address.logic.commands.CommandTestUtil.NEGATIVE_HOURS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_1_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_2_HOURS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_1;
import static seedu.address.logic.commands.CommandTestUtil.ZERO_HOURS_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.CompleteTaskCommand;

//@@author chelseyong
public class CompleteTaskCommandParserTest {

    private CompleteTaskCommandParser parser = new CompleteTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        //Parser parser = new CompleteTaskCommandParser();
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                + INDEX_DESC_1 + HOURS_DESC_1,
                new CompleteTaskCommand(INDEX_FIRST_TASK, Integer.valueOf(VALID_1_HOUR)));

        // Multiple inputs of indexes
        assertParseSuccess(parser, INDEX_DESC_2 + INDEX_DESC_1 + HOURS_DESC_1,
                new CompleteTaskCommand(INDEX_FIRST_TASK, Integer.valueOf(VALID_1_HOUR)));

        // Multiple inputs of hours
        assertParseSuccess(parser, INDEX_DESC_1 + HOURS_DESC_2 + HOURS_DESC_1,
                new CompleteTaskCommand(INDEX_FIRST_TASK, Integer.valueOf(VALID_2_HOURS)));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteTaskCommand.MESSAGE_USAGE);

        // missing index prefix
        assertParseFailure(parser, VALID_INDEX_1 + HOURS_DESC_1, expectedMessage);

        // missing hours prefix
        assertParseFailure(parser, INDEX_DESC_1 + VALID_1_HOUR, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Negative index
        assertParseFailure(parser, INVALID_INDEX_DESC_NEGATIVE + HOURS_DESC_1,
                ParserUtil.MESSAGE_INVALID_INDEX);

        // Zero index
        assertParseFailure(parser, INVALID_INDEX_DESC_ZERO + HOURS_DESC_1, ParserUtil.MESSAGE_INVALID_INDEX);

        // index > INT_MAX
        assertParseFailure(parser, INVALID_INDEX_OVERFLOW + HOURS_DESC_1, ParserUtil.MESSAGE_INVALID_INDEX);

        // hours in words
        assertParseFailure(parser, INDEX_DESC_1 + INVALID_HOURS_DESC, ParserUtil.MESSAGE_INVALID_HOURS);

        // invalid hours > INT_MAX
        assertParseFailure(parser, INDEX_DESC_1 + INVALID_HOURS_OVERFLOW, ParserUtil.MESSAGE_INVALID_HOURS);

        // Negative hours
        assertParseFailure(parser, INDEX_DESC_1 + NEGATIVE_HOURS_DESC, ParserUtil.MESSAGE_INVALID_HOURS);

        // Zero hours
        assertParseFailure(parser, INDEX_DESC_1 + ZERO_HOURS_DESC, ParserUtil.MESSAGE_INVALID_HOURS);

    }
}
