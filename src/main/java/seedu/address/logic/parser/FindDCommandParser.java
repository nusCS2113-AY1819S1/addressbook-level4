package seedu.address.logic.parser;

import seedu.address.logic.commands.FindDCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.distributor.DNameContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindDCommand object
 */
public class FindDCommandParser implements Parser<FindDCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindDCommand(new DNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
