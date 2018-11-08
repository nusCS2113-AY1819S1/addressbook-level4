package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewAttendeesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewAttendeeCommand object
 */
public class ViewAttendeesCommandParser implements Parser<ViewAttendeesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewAttendeeCommand
     * and returns a ViewAttendeeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewAttendeesCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewAttendeesCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAttendeesCommand.MESSAGE_USAGE), pe);
        }
    }

}
