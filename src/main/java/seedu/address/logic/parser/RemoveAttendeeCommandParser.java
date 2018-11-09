package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveAttendeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveAttendeeCommand object
 */
public class RemoveAttendeeCommandParser implements Parser<RemoveAttendeeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveAttendeeCommand
     * and returns an RemoveAttendeeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveAttendeeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemoveAttendeeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveAttendeeCommand.MESSAGE_USAGE), pe);
        }
    }
}
