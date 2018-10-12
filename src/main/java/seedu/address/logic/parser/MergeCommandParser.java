package seedu.address.logic.parser;

import static seedu.address.logic.commands.MergeCommand.MESSAGE_NOT_MERGED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MERGE;

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
     * @throws ParseException if the user input does not conform the expected format
     */
    public MergeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MERGE);

        List<String> indices = argMultimap.getAllValues(PREFIX_MERGE);


        if (indices.size() < 2) {
            throw new ParseException(MESSAGE_NOT_MERGED);
        }

        return new MergeCommand(indices);
    }
}
