package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

//Reused from https://github.com/CS2103JAN2018-F14-B1/main/pull/57/files with minor modifications
//@@author Woonhian
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArguments_failure() {
        //more than 1 field entered
        assertParseFailure(parser, "name" + " " + "department" + " " + "asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //invalid field entered
        assertParseFailure(parser, "invalid" + " " + "asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //invalid sort order entered
        assertParseFailure(parser, "name" + " " + "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //no field entered
        assertParseFailure(parser, "asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //no order entered
        assertParseFailure(parser, "name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
