package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.commons.util.DateUtil.isEarlierThan;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.planner.logic.commands.ListCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;

//@@author tenvinc
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand();
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        if (!arePrefixesPresent(argMultimap, PREFIX_DATE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        String[] argList = splitByWhitespace(argMultimap.getValue(PREFIX_DATE).get());
        return createListCommand(argList);
    }

    /**
     * Creates a SummaryCommand object from 2 strings arg1 and arg2. Chooses which command to create based
     * on the number of arguments in argList
     * @param argList
     * @return
     * @throws ParseException if no ListCommand object can be created due to invalid arguments
     */
    private ListCommand createListCommand(String[] argList) throws ParseException {
        switch (argList.length) {
        case ListCommand.DUO_ARG_MODE_COUNT:
            Date startDate = ParserUtil.parseDate(argList[0]);
            Date endDate = ParserUtil.parseDate(argList[1]);
            if (!isDateOrderValid(startDate, endDate)) {
                throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE)));
            }
            return new ListCommand(startDate, endDate);
        case ListCommand.SINGLE_ARG_MODE_COUNT:
            Date date = ParserUtil.parseDate(argList[0]);
            return new ListCommand(date);
        default:
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE)));
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
        return isEarlierThan(startDate, endDate) || startDate.equals(endDate);
    }
}
