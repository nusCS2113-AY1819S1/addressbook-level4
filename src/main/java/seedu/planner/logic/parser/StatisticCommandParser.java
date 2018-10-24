package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.planner.logic.commands.StatisticCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;

/**
 * Parses input arguments and creates a new StatisticCommand object
 */
public class StatisticCommandParser implements Parser<StatisticCommand> {

    /**
     * Retrieves all arguments from a string
     * @param args given string
     * @return array of strings
     * @throws ParseException if args cannot be processed
     */
    private static String[] getArguments(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    StatisticCommand.MESSAGE_USAGE)));
        }
        String[] argList = args.split("\\s+");
        if (argList.length != 2) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    StatisticCommand.MESSAGE_USAGE)));
        }
        return argList;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean isDateOrderValid(Date startDate, Date endDate) {
        return startDate.isEarlierThan(endDate) || startDate.equals(endDate);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the StatisticCommand
     * and returns an StatisticCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatisticCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        if (!arePrefixesPresent(argMultimap, PREFIX_DATE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticCommand.MESSAGE_USAGE));
        }
        String dateIntervalString = argMultimap.getValue(PREFIX_DATE).get();
        String[] argList = getArguments(dateIntervalString);
        Date startDate = ParserUtil.parseDate(argList[0]);
        Date endDate = ParserUtil.parseDate(argList[1]);
        if (!isDateOrderValid(startDate, endDate)) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticCommand.MESSAGE_USAGE)));
        }
        return new StatisticCommand(startDate, endDate);
    }
}
