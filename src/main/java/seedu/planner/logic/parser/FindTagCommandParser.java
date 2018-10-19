package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.planner.logic.commands.FindTagCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.TagsContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns an FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FindTagCommand(new TagsContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}

