//@@author XiaoYunhan
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.CompleteTaskCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CompleteTaskCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the CompleteTaskCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CompleteTaskCommandParserTest {

    private CompleteTaskCommandParser parser = new CompleteTaskCommandParser();

    @Test
    public void parseValidArgsReturnsCompleteCommand() {
        assertParseSuccess(parser, "1", new CompleteTaskCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parseInvalidArgsThrowsException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CompleteTaskCommand.MESSAGE_USAGE));
    }

}
