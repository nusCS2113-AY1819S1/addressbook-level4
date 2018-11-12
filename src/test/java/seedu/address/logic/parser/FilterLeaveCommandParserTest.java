package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FilterLeaveCommand;
import seedu.address.model.leave.NricContainsKeywordsPredicate;

//@@author Hafizuddin-NUS
public class FilterLeaveCommandParserTest {

    private FilterLeaveCommandParser parser = new FilterLeaveCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterLeaveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterLeaveCommand() {
        // no leading and trailing whitespaces
        FilterLeaveCommand expectedFindCommand =
                new FilterLeaveCommand(new NricContainsKeywordsPredicate(Arrays.asList("S1212121A", "S1313131A")));
        assertParseSuccess(parser, "S1212121A S1313131A", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n S1212121A \n \t S1313131A  \t", expectedFindCommand);
    }

}
