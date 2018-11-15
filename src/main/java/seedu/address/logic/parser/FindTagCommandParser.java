package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindTagProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTagProductCommand object
 */
public class FindTagCommandParser implements Parser<FindTagProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagProductCommand
     * and returns an FindTagProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagProductCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagProductCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTagProductCommand(new TagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
