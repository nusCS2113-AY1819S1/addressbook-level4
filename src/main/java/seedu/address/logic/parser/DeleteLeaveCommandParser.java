package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteLeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Hafizuddin-NUS
/**
 * Parses input arguments and creates a new DeleteLeaveCommand object
 */
public class DeleteLeaveCommandParser implements Parser<DeleteLeaveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLeaveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteLeaveCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLeaveCommand.MESSAGE_USAGE), pe);
        }
    }
}
