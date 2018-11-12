package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUITMENT;

import org.junit.Test;

import seedu.address.logic.commands.SelectRecruitmentPostCommand;

public class SelectRecruitmentPostCommandParserTest {

    private SelectRecruitmentPostCommandParser parser = new SelectRecruitmentPostCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectRecruitmentPostCommand(INDEX_FIRST_RECRUITMENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectRecruitmentPostCommand.MESSAGE_USAGE));
    }
}
