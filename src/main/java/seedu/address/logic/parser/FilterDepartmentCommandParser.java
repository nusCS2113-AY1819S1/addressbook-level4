package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FilterDepartmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

//@@author Woonhian
/**
 * Parses input arguments and creates a new FilterDepartmentCommand object
 */
public class FilterDepartmentCommandParser implements Parser<FilterDepartmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterDepartmentCommand
     * and returns an FilterDepartmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterDepartmentCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        trimmedArgs = trimmedArgs.toLowerCase();
        if (trimmedArgs.isEmpty() || trimmedArgs.contains("management")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterDepartmentCommand.MESSAGE_USAGE));
        }

        String[] departmentKeywords = trimmedArgs.split("\\s+");

        return new FilterDepartmentCommand(new DepartmentContainsKeywordsPredicate(Arrays.asList(departmentKeywords)));
    }

}
