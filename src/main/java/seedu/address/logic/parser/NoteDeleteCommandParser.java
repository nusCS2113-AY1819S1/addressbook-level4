package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.NoteDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NoteDeleteCommand object
 */
public class NoteDeleteCommandParser implements Parser<NoteDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the NoteDeleteCommand
     * and returns a NoteDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NoteDeleteCommand parse(String args) throws ParseException {
        String index = args.trim().replaceAll(" +", "");

        if (index.matches("\\d+")) {
            return new NoteDeleteCommand(Integer.parseInt(index));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteDeleteCommand.MESSAGE_USAGE));
        }
    }
}
