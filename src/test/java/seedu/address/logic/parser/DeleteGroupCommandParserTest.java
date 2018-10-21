package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;

import org.junit.Test;

import seedu.address.logic.commands.DeleteGroupCommand;

public class DeleteGroupCommandParserTest {

    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteGroupCommand() {
        assertParseSuccess(parser, "1", new DeleteGroupCommand(INDEX_FIRST_GROUP));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, DeleteGroupCommand.COMMAND_WORD, expectedMessage);
    }

}
