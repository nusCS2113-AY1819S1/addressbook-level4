package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindDistributorsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.distributor.DNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindDistributorsCommand object
 */
public class FindDistributorsCommandParser implements Parser<FindDistributorsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDistributorsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDistributorsCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindDistributorsCommand(new DNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
