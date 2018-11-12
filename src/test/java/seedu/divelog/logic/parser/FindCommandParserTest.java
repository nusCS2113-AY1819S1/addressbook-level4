package seedu.divelog.logic.parser;

import static seedu.divelog.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.divelog.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.divelog.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.divelog.logic.commands.FindCommand;
import seedu.divelog.model.dive.LocationContainsKeywordPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new LocationContainsKeywordPredicate(Arrays.asList("Tioman", "Bali")));
        assertParseSuccess(parser, "Tioman Bali", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Tioman \n \t Bali  \t", expectedFindCommand);
    }

}
