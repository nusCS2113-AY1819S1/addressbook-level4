package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteGroupPersonCommand;

/**
 * Test different boundary values of DeleteGroupPersonCommandParser
 */
public class DeleteGroupPersonCommandParserTest {

    private DeleteGroupPersonCommandParser parser = new DeleteGroupPersonCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteGroupPersonCommand() {
        assertParseSuccess(parser, " g/1 p/1",
                new DeleteGroupPersonCommand(INDEX_FIRST_GROUP, INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupPersonCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, DeleteGroupPersonCommand.COMMAND_WORD, expectedMessage);
    }

}
