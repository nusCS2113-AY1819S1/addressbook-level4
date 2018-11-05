package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        String firstExample = "Admin";
        String secondExample = "Admin Finance";
        List<String> showAll = new ArrayList<>();

        ListCommand firstExpectedCommand = new ListCommand(ListCommand.LIST_KEY_PEOPLE,
                new DepartmentContainsKeywordsPredicate(showAll));
        ListCommand secondExpectedCommand = new ListCommand(ListCommand.LIST_KEY_DEPARTMENT,
                new DepartmentContainsKeywordsPredicate(showAll));
        ListCommand thirdExpectedCommand = new ListCommand(ListCommand.LIST_KEY_ALL,
                new DepartmentContainsKeywordsPredicate(showAll));
        assertParseSuccess(parser, "all people", firstExpectedCommand);

        // whitespace -> accepted
        assertParseSuccess(parser, " all people", firstExpectedCommand);
        assertParseSuccess(parser, " all people ", firstExpectedCommand);
        assertParseSuccess(parser, "all events", secondExpectedCommand);
        assertParseSuccess(parser, " all events", secondExpectedCommand);
        assertParseSuccess(parser, " all events  ", secondExpectedCommand);
        assertParseSuccess(parser, "all", thirdExpectedCommand);
        assertParseSuccess(parser, " all", thirdExpectedCommand);
        assertParseSuccess(parser, " all ", thirdExpectedCommand);
        assertParseSuccess(parser, "dep Admin", new ListCommand("dep",
                preparePredicate(firstExample)));
        assertParseSuccess(parser, " dep Admin", new ListCommand("dep",
                preparePredicate(firstExample)));
        assertParseSuccess(parser, " dep Admin ", new ListCommand("dep",
                preparePredicate(firstExample)));
        assertParseSuccess(parser, "DeP Admin", new ListCommand("dep",
                preparePredicate(firstExample)));
        assertParseSuccess(parser, " DeP Admin", new ListCommand("dep",
                preparePredicate(firstExample)));
        assertParseSuccess(parser, " DeP Admin ", new ListCommand("dep",
                preparePredicate(firstExample)));
        /*assertParseSuccess(parser, "dep aDmin", new ListCommand("dep",
                preparePredicate(firstExample)));*/
        assertParseSuccess(parser, "dep Admin Finance", new ListCommand("dep",
                preparePredicate(secondExample)));
        assertParseSuccess(parser, "dep Admin Finance ", new ListCommand("dep",
                preparePredicate(secondExample)));
        /*assertParseSuccess(parser, "dep adMiN   fInancE", new ListCommand("dep",
                preparePredicate(secondExample)));*/


    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // missing department prefix
        assertParseFailure(parser, "de Admin", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
        // missing department keywords
        assertParseFailure(parser, "dep ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
        // missing all prefix
        assertParseFailure(parser, "al", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
        // incorrect spelling for people
        assertParseFailure(parser, "all peope", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
        // incorrect spelling for events
        assertParseFailure(parser, "all evens", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }

    private DepartmentContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DepartmentContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
