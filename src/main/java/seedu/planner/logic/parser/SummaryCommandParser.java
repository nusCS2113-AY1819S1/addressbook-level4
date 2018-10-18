package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;

/**
 * Parses input arguments and creates a new SummaryCommand object
 */
public class SummaryCommandParser implements Parser<SummaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SummaryCommand
     * and returns an SummaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SummaryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        if (!arePrefixesPresent(argMultimap, PREFIX_DATE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
        }
        String dateIntervalString = argMultimap.getValue(PREFIX_DATE).get();
        String[] argList = splitByWhitespace(dateIntervalString);
        Date startDate;
        Date endDate;
        if (argList.length != 2) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE)));
        } else {
            startDate = ParserUtil.parseDate(argList[0]);
            endDate = ParserUtil.parseDate(argList[1]);
            if (!isDateOrderValid(startDate, endDate)) {
                throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE)));
            }
            return new SummaryCommand(startDate, endDate);
        }
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Splits a string using whitespace as delimiters
     * @param args
     * @return array of split strings
     */
    private static String[] splitByWhitespace(String args) {
        if (args.isEmpty()) {
            return null;
        }
        String[] argList = args.split("\\s+");
        return argList;
    }

    private static boolean isDateOrderValid(Date startDate, Date endDate) {
        return startDate.isEarlierThan(endDate) || startDate.equals(endDate);
    }
}
