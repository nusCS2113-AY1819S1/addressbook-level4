package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;

public class FindAddressCommandParserTest {

    private FindAddressCommandParser parser = new FindAddressCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindAddressCommand expectedFindAddressCommand =
                new FindAddressCommand(new AddressContainsKeywordsPredicate(Arrays.asList("College", "Sentosa")));
        assertParseSuccess(parser, "College Sentosa", expectedFindAddressCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n College \n \t Sentosa  \t", expectedFindAddressCommand);
    }

}
