package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnSortCommand() {
        String name = "name";
        String date = "date";
        String starttime = "starttime";
        String endtime = "endtime";

        SortCommand sortByNameCommand = new SortCommand(name);
        SortCommand sortByDateCommand = new SortCommand(date);
        SortCommand sortByStartTimeCommand = new SortCommand(starttime);
        SortCommand sortByEndTimeComand = new SortCommand(endtime);


        // whitespace -> accepted
        assertParseSuccess(parser, "name", sortByNameCommand);
        assertParseSuccess(parser, " name ", sortByNameCommand);
        assertParseSuccess(parser, "date", sortByDateCommand);
        assertParseSuccess(parser, " date ", sortByDateCommand);
        assertParseSuccess(parser, "starttime", sortByStartTimeCommand);
        assertParseSuccess(parser, " starttime ", sortByStartTimeCommand);
        assertParseSuccess(parser, "endtime", sortByEndTimeComand);
        assertParseSuccess(parser, " endtime ", sortByEndTimeComand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        assertParseFailure(parser, "Name!",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "by name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "d@te",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "data",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "startTime",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "start_Time",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "startTime_",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "endtime123 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
