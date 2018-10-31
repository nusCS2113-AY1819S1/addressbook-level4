package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.UsernameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindAccountCommandParser implements Parser<FindAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindAccountCommand
     * and returns an FindAccountCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAccountCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAccountCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindAccountCommand(new UsernameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
