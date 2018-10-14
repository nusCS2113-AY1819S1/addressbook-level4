package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTimeCommand;
import seedu.address.testutil.TypicalTimeSlots;

public class DeleteTimeCommandParserTest {

    private DeleteTimeCommandParser parser = new DeleteTimeCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 monday 08:00-10:00",
                new DeleteTimeCommand(INDEX_FIRST_PERSON, TypicalTimeSlots.MON_8_TO_10));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a monday 08:00-10:00",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTimeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTimeSlot_throwsParseException() {
        assertParseFailure(parser, "1 monday",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTimeCommand.MESSAGE_USAGE));
    }

}
