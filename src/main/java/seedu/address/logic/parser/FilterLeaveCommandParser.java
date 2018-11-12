package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FilterLeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.NricContainsKeywordsPredicate;

//@@author Hafizuddin-NUS
/**
 * Parses input arguments and creates a new FilterLeaveCommand object
 */
public class FilterLeaveCommandParser implements Parser<FilterLeaveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterLeaveCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterLeaveCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FilterLeaveCommand(new NricContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
