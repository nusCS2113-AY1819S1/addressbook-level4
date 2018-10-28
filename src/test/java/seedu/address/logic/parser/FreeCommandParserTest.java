package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FreeCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class FreeCommandParserTest {

    private FreeCommandParser parser = new FreeCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        Collection<Index> containsOne = new ArrayList<>();
        containsOne.add(INDEX_FIRST_PERSON);

        Collection<Index> containsOneAndTwo = new ArrayList<>();
        containsOneAndTwo.add(INDEX_FIRST_PERSON);
        containsOneAndTwo.add(INDEX_SECOND_PERSON);

        assertParseSuccess(parser, "1", new FreeCommand(containsOne));
        assertParseSuccess(parser, "1 2", new FreeCommand(containsOneAndTwo));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
    }
}
