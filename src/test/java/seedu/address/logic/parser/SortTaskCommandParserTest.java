package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SORTING_METHOD_HOURS;
import static seedu.address.logic.commands.CommandTestUtil.SORT_PRIORITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SORTING_METHOD_PRIORITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.logging.Logger;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.SortTaskCommand;

//@@author ChanChunCheong
public class SortTaskCommandParserTest {
    private static final Logger logger = LogsCenter.getLogger(SortTaskCommandParserTest.class);
    private SortTaskCommandParser parser = new SortTaskCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        String method = VALID_SORTING_METHOD_PRIORITY;
        assertParseSuccess(parser, SORT_PRIORITY,
                new SortTaskCommand(method));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE);

        // missing Sort prefix
        assertParseFailure(parser, " " , expectedMessage);
    }

    @Test
    public void parse_methodEnteredIsNotOneOfTheFourMethods_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE);

        //method keyed in is not one of the 4 methods
        assertParseFailure(parser, INVALID_SORTING_METHOD_HOURS , expectedMessage);
    }
}
