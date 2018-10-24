package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPersonSubCommand;
import seedu.address.logic.commands.FindTagSubCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

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
                new FindPersonSubCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")), false);
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);

        // parsing with exclude option enabled
        FindCommand expectedFindCommand2 =
                new FindPersonSubCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")), true);
        assertParseSuccess(parser, "\\exclude Alice Bob", expectedFindCommand2);

        // parsing with tag option enabled
        FindCommand expectedFindCommand3 =
                new FindTagSubCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "colleagues")));
        assertParseSuccess(parser, "\\tag friends colleagues", expectedFindCommand3);

        // parsing with tag and exclude options enabled
        FindCommand expectedFindCommand4 =
                new FindTagSubCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "colleagues")), true);
        assertParseSuccess(parser, "\\tag friends colleagues", expectedFindCommand4);
    }

    @Test
    public void parse_noKeywords_throwsParseException() {
        //parse tags without keywords
        assertParseFailure(parser, "\\tag",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        //parse with exclude option enabled without keywords
        assertParseFailure(parser, "\\exclude",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        //parse tags with exclude option enabled without keywords
        assertParseFailure(parser, "\\tag \\exclude",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
