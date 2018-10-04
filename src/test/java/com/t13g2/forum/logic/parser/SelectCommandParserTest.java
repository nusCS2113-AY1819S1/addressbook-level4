package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.t13g2.forum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static com.t13g2.forum.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import com.t13g2.forum.logic.commands.SelectCommand;
import com.t13g2.forum.testutil.TypicalIndexes;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCommand(TypicalIndexes.INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
