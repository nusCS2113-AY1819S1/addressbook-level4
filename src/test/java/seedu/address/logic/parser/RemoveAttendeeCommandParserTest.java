package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalUsers.ALICE;

import org.junit.Test;

import seedu.address.logic.commands.RemoveAttendeeCommand;

public class RemoveAttendeeCommandParserTest {

    private RemoveAttendeeCommandParser parser = new RemoveAttendeeCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveAttendeeCommand() {
        assertParseSuccess(parser, "1 u/Alice Williams",
                new RemoveAttendeeCommand(INDEX_FIRST_EVENT, ALICE.getUsername()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // only index supplied
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveAttendeeCommand.MESSAGE_USAGE));
        // only username supplied
        assertParseFailure(parser, "u/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveAttendeeCommand.MESSAGE_USAGE));
    }
}
