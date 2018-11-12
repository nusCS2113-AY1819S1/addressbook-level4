package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.Test;

import seedu.address.logic.commands.DeleteAccountCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteAccountCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteAccountCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteAccountCommandParserTest {

    private DeleteAccountCommandParser parser = new DeleteAccountCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAccountCommand() {
        assertParseSuccess(parser, "1", new DeleteAccountCommand(INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAccountCommand
                .MESSAGE_USAGE));
    }
}
