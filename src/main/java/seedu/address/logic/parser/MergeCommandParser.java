package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.MergeCommand.MESSAGE_NOT_MERGED;
import static seedu.address.logic.commands.MergeCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MERGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.commands.MergeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class MergeCommandParser implements Parser<MergeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MergeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MERGE, PREFIX_NAME);

        List<String> indices = argMultimap.getAllValues(PREFIX_MERGE);
        List<String> name = argMultimap.getAllValues(PREFIX_NAME);
        if (indices.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MergeCommand.MESSAGE_USAGE));
        }
        if (name.isEmpty()) {
            throw new ParseException("No group name entered. " + MESSAGE_USAGE);
        }


        if (indices.size() < 2) {
            throw new ParseException(MESSAGE_NOT_MERGED);
        }

        return new MergeCommand(indices, name.get(0));
    }
}
