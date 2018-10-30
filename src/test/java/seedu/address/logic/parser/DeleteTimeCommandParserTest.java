package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTimeCommand;
import seedu.address.testutil.TypicalTimeSlots;

public class DeleteTimeCommandParserTest {

    private DeleteTimeCommandParser parser = new DeleteTimeCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, " monday 08:00-10:00 ",
                new DeleteTimeCommand(TypicalTimeSlots.MON_8_TO_10));
    }

    @Test
    public void parse_invalidTimeSlot_throwsParseException() {
        assertParseFailure(parser, "monday",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTimeCommand.MESSAGE_USAGE));
    }

}
