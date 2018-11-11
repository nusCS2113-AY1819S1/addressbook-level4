package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEFERDEADLINE_TWODAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEFERDAYS_EXCEEDED;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEFERDAYS_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_STRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEFERDAYS_INT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_STRING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.DeferDeadlineCommandParser.MESSAGE_INVALID_DEFERRED_DAYS_EXCEEDED;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DEFERRED_DAYS;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.logging.Logger;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.DeferDeadlineCommand;

//@@author ChanChunCheong
public class DeferDeadlineCommandParserTest {
    private static final Logger logger = LogsCenter.getLogger(DeferDeadlineCommandParserTest.class);
    private DeferDeadlineCommandParser parser = new DeferDeadlineCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, VALID_INDEX_STRING + DEFERDEADLINE_TWODAY,
                new DeferDeadlineCommand(VALID_INDEX, VALID_DEFERDAYS_INT));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeferDeadlineCommand.MESSAGE_USAGE);

        // missing index prefix
        assertParseFailure(parser, DEFERDEADLINE_TWODAY, expectedMessage);

        // missing deferDeadline prefix
        assertParseFailure(parser, VALID_INDEX_STRING, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid index
        assertParseFailure(parser, INVALID_INDEX_STRING + DEFERDEADLINE_TWODAY,
                MESSAGE_INVALID_INDEX);
        // invalid deferDays as it exceeds 31 days
        assertParseFailure(parser, VALID_INDEX_STRING + INVALID_DEFERDAYS_EXCEEDED,
                MESSAGE_INVALID_DEFERRED_DAYS_EXCEEDED);
        // invalid deferDays as it is not a non-zero unsigned integer
        assertParseFailure(parser, VALID_INDEX_STRING + INVALID_DEFERDAYS_NEGATIVE,
                MESSAGE_INVALID_DEFERRED_DAYS);
    }
}
//@@author
