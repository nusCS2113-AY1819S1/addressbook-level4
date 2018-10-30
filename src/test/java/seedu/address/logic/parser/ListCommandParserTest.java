package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        String firstExample = "Admin";
        String secondExample = "Admin Finance";
        String[] firstUserInput = firstExample.trim().split("\\s+");
        String[] secondUserInput = secondExample.trim().split("\\s+");
        ListCommand expectedCommand = new ListCommand(ListCommand.LIST_KEY_ALL, null);


        assertParseSuccess(parser, "all", expectedCommand);

        // whitespace -> accepted
        assertParseSuccess(parser, " all", expectedCommand);

        assertParseSuccess(parser, " all ", expectedCommand);
        assertParseSuccess(parser, "dep Admin", new ListCommand("dep",
                new DepartmentContainsKeywordsPredicate(Arrays.asList(firstUserInput))));
        assertParseSuccess(parser, " dep Admin", new ListCommand("dep",
                new DepartmentContainsKeywordsPredicate(Arrays.asList(firstUserInput))));
        assertParseSuccess(parser, " dep Admin ", new ListCommand("dep",
                new DepartmentContainsKeywordsPredicate(Arrays.asList(firstUserInput))));
        assertParseSuccess(parser, "DeP Admin", new ListCommand("dep",
                new DepartmentContainsKeywordsPredicate(Arrays.asList(firstUserInput))));
        assertParseSuccess(parser, " DeP Admin", new ListCommand("dep",
                new DepartmentContainsKeywordsPredicate(Arrays.asList(firstUserInput))));
        assertParseSuccess(parser, " DeP Admin ", new ListCommand("dep",
                new DepartmentContainsKeywordsPredicate(Arrays.asList(firstUserInput))));
        /*assertParseSuccess(parser, "dep aDmin", new ListCommand("dep",
                new DepartmentContainsKeywordsPredicate(Arrays.asList(firstUserInput))));*/
        assertParseSuccess(parser, "dep Admin Finance", new ListCommand("dep",
                new DepartmentContainsKeywordsPredicate(Arrays.asList(secondUserInput))));
        assertParseSuccess(parser, "dep Admin Finance ", new ListCommand("dep",
                new DepartmentContainsKeywordsPredicate(Arrays.asList(secondUserInput))));
        /*assertParseSuccess(parser, "dep adMiN   fInancE", new ListCommand("dep",
                new DepartmentContainsKeywordsPredicate(Arrays.asList(secondUserInput))));*/

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // missing department prefix
        assertParseFailure(parser, "de Admin", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "dep ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        // missing all prefix
        assertParseFailure(parser,"al", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

}
