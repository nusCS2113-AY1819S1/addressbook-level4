package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;

import org.junit.Test;

import seedu.address.logic.commands.FinishTodoCommand;

//@@author linnnruoo
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the FinishTodoCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the FinishTodoCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class FinishTodoCommandParserTest {

    private FinishTodoCommandParser parser = new FinishTodoCommandParser();

    @Test
    public void parse_validArgs_returnsFinishTodoCommand() {
        assertParseSuccess(parser, "1", new FinishTodoCommand(INDEX_FIRST_TODO));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinishTodoCommand.MESSAGE_USAGE));
    }
}
