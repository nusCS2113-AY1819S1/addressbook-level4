package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TimeSlot;

/**
 * Parses input arguments and creates a new AddTimeCommand object
 */
public class AddTimeCommandParser implements Parser<AddTimeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTimeCommand
     * and returns an AddTimeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTimeCommand parse (String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args.trim().split("\\s", 2)[0]);
            TimeSlot timeSlot = ParserUtil.parseTimeSlot(args.trim().split("\\s", 2)[1]);
            return new AddTimeCommand(index, timeSlot);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimeCommand.MESSAGE_USAGE));
        }
    }
}
