package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.recruit.logic.commands.SelectCandidateCommand;

/**
 * Test scope: similar to {@code DeleteCandidateCommandParserTest}.
 * @see DeleteCandidateCommandParserTest
 */
public class SelectCandidateCommandParserTest {

    private SelectCandidateCommandParser parser = new SelectCandidateCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCandidateCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectCandidateCommand.MESSAGE_USAGE));
    }
}
