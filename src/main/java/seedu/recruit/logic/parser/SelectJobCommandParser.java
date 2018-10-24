package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.SelectJobCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectJobCommand object
 */
public class SelectJobCommandParser implements Parser<SelectJobCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectJobCommand
     * and returns an SelectJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectJobCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectJobCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectJobCommand.MESSAGE_USAGE), pe);
        }
    }
}
