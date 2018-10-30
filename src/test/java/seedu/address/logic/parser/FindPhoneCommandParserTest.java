package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

public class FindPhoneCommandParserTest {

    private FindPhoneCommandParser parser = new FindPhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindPhoneCommand expectedFindPhoneCommand =
                new FindPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("98835761", "13371337")));
        assertParseSuccess(parser, "98835761 13371337", expectedFindPhoneCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 98835761 \n \t 13371337  \t", expectedFindPhoneCommand);
    }

}
