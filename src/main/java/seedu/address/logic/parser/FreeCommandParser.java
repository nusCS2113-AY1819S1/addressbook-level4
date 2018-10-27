package seedu.address.logic.parser;

import seedu.address.logic.commands.FreeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.List;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FreeCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREE;

/**
 * Parses input arguments and creates a new FreeCommand object
 */
public class FreeCommandParser implements Parser<FreeCommand> {
    private ArgumentMultimap argMultimap;

    /**
     * Parses the given {@code String} of arguments in the context of the FreeCommand
     * and returns an FreeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FreeCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FREE);

        List<String> indices = argMultimap.getAllValues(PREFIX_FREE);
        if (indices.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }



        return new FreeCommand(indices);
    }
}
