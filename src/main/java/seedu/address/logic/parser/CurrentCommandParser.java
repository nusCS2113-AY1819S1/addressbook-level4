package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CurrentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CurrentCommand object
 */
public class CurrentCommandParser implements Parser<CurrentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CurrentCommand
     * and returns an CurrentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CurrentCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CurrentCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CurrentCommand.MESSAGE_USAGE), pe);
        }
    }
}
