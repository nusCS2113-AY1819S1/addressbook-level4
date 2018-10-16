package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TimeSlot;

/**
 * Parses input arguments and creates a new DeleteTimeCommand object
 */
public class DeleteTimeCommandParser implements Parser<DeleteTimeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTimeCommand
     * and returns an DeleteTimeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTimeCommand parse (String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args.trim().split("\\s", 2)[0]);
            TimeSlot timeSlot = ParserUtil.parseTimeSlot(args.trim().split("\\s", 2)[1]);
            return new DeleteTimeCommand(index, timeSlot);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTimeCommand.MESSAGE_USAGE));
        }
    }
}
