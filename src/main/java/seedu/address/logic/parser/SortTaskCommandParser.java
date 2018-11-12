package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import java.util.stream.Stream;

import seedu.address.logic.commands.SortTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@ChanChunCheong
/**
 * Parses input arguments and creates a new SortTaskCommand object
 */
public class SortTaskCommandParser implements Parser<SortTaskCommand> {
    @Override
    public SortTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE));
        }
        String method = argMultimap.getValue(PREFIX_SORT).orElse("");

        //method entered has to be one of the 4 methods
        if (method.equals("module") || method.equals("deadline") || method.equals("priority")
                || method.equals("title")) {
            return new SortTaskCommand(method);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
