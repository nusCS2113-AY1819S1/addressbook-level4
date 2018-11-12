package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectScheduleCommand object
 */
public class SelectScheduleCommandParser implements Parser<SelectScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectScheduleCommand
     * and returns an SelectScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectScheduleCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectScheduleCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectScheduleCommand.MESSAGE_USAGE), pe);
        }
    }
}
