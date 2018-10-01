package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.GroupCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

public class GroupCommandParserTest {
    private GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_noParameters_failure(){
        String expectedOutput = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE);

        //  without any parameters
        assertParseFailure(parser, GroupCommand.COMMAND_WORD, expectedOutput);
        assertParseFailure(parser, GroupCommand.COMMAND_WORD_2, expectedOutput);
    }
}
