package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteLeaveCommand;

//@@author Hafizuddin-NUS
public class DeleteLeaveCommandParserTest {

    private DeleteLeaveCommandParser parser = new DeleteLeaveCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteLeaveCommand() {
        assertParseSuccess(parser, "1", new DeleteLeaveCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteLeaveCommand.MESSAGE_USAGE));
    }
}
