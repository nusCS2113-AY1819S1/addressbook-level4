package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.planner.commons.util.DateUtil;
import seedu.planner.logic.commands.SummaryByDateCommand;
import seedu.planner.logic.commands.SummaryByMonthCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.Month;
import seedu.planner.model.record.Date;

/**
 * Parses input arguments and creates a new SummaryCommand object from the string input.
 * Selects which command to create for parsing by identifying specific keywords
 */
public class SummaryCommandParser implements Parser<SummaryCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Retrieves all arguments from a string
     * @param args given string
     * @return array of strings
     * @throws ParseException if args cannot be processed
     */
    private static String[] getArguments(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SummaryCommand.MESSAGE_USAGE)));
        }
        String[] argList = args.split("\\s+");
        if (argList.length != 2) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SummaryCommand.MESSAGE_USAGE)));
        }
        return argList;
    }

    private static boolean isDateOrderValid(Date startDate, Date endDate) {
        return startDate.isEarlierThan(endDate) || startDate.equals(endDate);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SummaryCommand
     * and returns an SummaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SummaryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        if (!arePrefixesPresent(argMultimap, PREFIX_DATE) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
        }
        String mode = argMultimap.getPreamble().trim();
        String intervalString = argMultimap.getValue(PREFIX_DATE).get();
        String[] argList = getArguments(intervalString);
        return createSummaryCommand(mode, argList[0], argList[1]);
    }

    /**
     * Creates a SummaryCommand object from 2 strings arg1 and arg2.
     * @param mode - determines which SummaryCommand is created
     * @param arg1 - 1st argument
     * @param arg2 - 2nd argument
     * @throws ParseException if no SummaryCommand object can be created due to invalid arguments
     */
    private SummaryCommand createSummaryCommand(String mode, String arg1, String arg2) throws ParseException {
        if (mode.equals("date")) {
            Date startDate = ParserUtil.parseDate(arg1);
            Date endDate = ParserUtil.parseDate(arg2);
            if (!isDateOrderValid(startDate, endDate)) {
                throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SummaryByDateCommand.MESSAGE_USAGE)));
            }
            return new SummaryByDateCommand(startDate, endDate);
        } else if (mode.equals("month")) {
            Month startMonth = ParserUtil.parseMonth(arg1);
            Month endMonth = ParserUtil.parseMonth(arg2);
            if (DateUtil.compareMonth(startMonth, endMonth) > 0) {
                throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SummaryByMonthCommand.MESSAGE_USAGE)));
            }
            return new SummaryByMonthCommand(startMonth, endMonth);
        } else {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SummaryCommand.MESSAGE_USAGE)));
        }
    }
}
