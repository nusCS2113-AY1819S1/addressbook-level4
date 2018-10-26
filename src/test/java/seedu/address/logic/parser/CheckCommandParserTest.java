package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.CheckCommand;
import seedu.address.model.book.QuantityContainsNumberPredicate;

public class CheckCommandParserTest {

    private CheckCommandParser parser = new CheckCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsCheckCommand () {
        // no leading and trailing whitespaces
        CheckCommand expectedCheckCommand =
                new CheckCommand(new QuantityContainsNumberPredicate(Arrays.asList("3", "2", "1")));
        assertParseSuccess(parser, "3", expectedCheckCommand);

        // whitespaces between command and quantity
        assertParseSuccess(parser, "\n \t 3", expectedCheckCommand);
    }
}
