package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.StudentFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new StudentFindCommand object
 */
public class FindCommandParser implements Parser<StudentFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StudentFindCommand
     * and returns an StudentFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StudentFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudentFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new StudentFindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
