package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FilterDepartmentCommand;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

//@@author Woonhian
public class FilterDepartmentCommandParserTest {

    private FilterDepartmentCommandParser parser = new FilterDepartmentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterDepartmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterDepartmentCommand() {
        // no leading and trailing whitespaces
        FilterDepartmentCommand expectedFilterDepartmentCommand =
                new FilterDepartmentCommand(new DepartmentContainsKeywordsPredicate(Arrays.asList("junior", "senior")));
        assertParseSuccess(parser, "junior senior", expectedFilterDepartmentCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n junior \n \t senior  \t", expectedFilterDepartmentCommand);
    }

}
