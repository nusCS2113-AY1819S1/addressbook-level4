package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.planner.logic.commands.FindTagCommand;
import seedu.planner.model.record.TagsContainsKeywordsPredicate;

public class FindTagCommandParserTest {

    private FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        String userInputKeywords = "friends owesmoney";
        String[] userInputArray = userInputKeywords.split("\\s+");
        FindTagCommand expectedFindTagCommand =
                new FindTagCommand(new TagsContainsKeywordsPredicate(Arrays.asList(userInputArray)), userInputArray);
        assertParseSuccess(parser, "friends owesmoney", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t owesmoney  \t", expectedFindTagCommand);
    }

}
