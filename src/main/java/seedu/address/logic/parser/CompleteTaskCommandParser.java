package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CompleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CompleteTaskCommand object
 */
public class CompleteTaskCommandParser implements Parser<CompleteTaskCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteTaskCommand
     * and returns an CompleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CompleteTaskCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX, PREFIX_HOURS);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_HOURS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteTaskCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        int expectedNumOfHours = ParserUtil.parseHours(argMultimap.getValue(PREFIX_HOURS).get());

        return new CompleteTaskCommand(index, expectedNumOfHours);
    }
}


