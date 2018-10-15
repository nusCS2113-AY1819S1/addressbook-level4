package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANGE;

import java.util.ArrayList;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_RANGE);

        // if (argMultimap.getValue(PREFIX_RANGE).isPresent()) {
        //     try {
        //         ArrayList<Index> indexArrayList = ParserUtil.parseRangeIndex(startIndex, endIndex);
        //         return new SelectCommand(indexArrayList);
        //     } catch (ParseException pe) {
        //         throw new ParseException(
        //                 String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), pe);
        //     }    
        // }
        
        try {
            ArrayList<Index> indexArrayList = ParserUtil.parseMultipleIndex(args);
            return new SelectCommand(indexArrayList);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), pe);
        }
    }
}
