package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENDITURE;

import org.junit.Test;

import seedu.address.logic.commands.DeleteExpenditureCommand;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteExpenditureCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteExpenditureCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteExpenditureCommandParserTest {

    private DeleteExpenditureCommandParser parser = new DeleteExpenditureCommandParser();

    /*
    @Test
    public void parseValidArgsReturnsDeleteExpenditureCommand() {
        assertParseSuccess(parser, "1", new DeleteExpenditureCommand(INDEX_FIRST_EXPENDITURE));
    }
    */

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteExpenditureCommand.MESSAGE_USAGE));
    }

}
