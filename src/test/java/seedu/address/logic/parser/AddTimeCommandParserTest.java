package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddTimeCommand;
import seedu.address.testutil.TypicalTimeSlots;

public class AddTimeCommandParserTest {

    private AddTimeCommandParser parser = new AddTimeCommandParser();

    @Test
    public void parse_validArgs_returnsAddTimeCommand() {
        assertParseSuccess(parser, " monday 08:00-10:00 ",
                new AddTimeCommand(TypicalTimeSlots.MON_8_TO_10));
    }

    @Test
    public void parse_invalidTimeSlot_throwsParseException() {
        assertParseFailure(parser, "monday",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimeCommand.MESSAGE_USAGE));
    }

}
