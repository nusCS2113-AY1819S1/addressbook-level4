package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ViewExpenditureCommand;

public class ViewExpenditureCommandParserTest {

    private ViewExpenditureCommandParser parser = new ViewExpenditureCommandParser();

    @Test
    public void parseBlankFailure() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewExpenditureCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidFailure() {
        assertParseFailure(parser, "invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewExpenditureCommand.MESSAGE_USAGE));
    }

}
