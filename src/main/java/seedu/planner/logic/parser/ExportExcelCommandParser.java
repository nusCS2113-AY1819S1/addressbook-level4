package seedu.planner.logic.parser;

import java.util.Arrays;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;

/**
 * Parses input arguments and create ExportExcelCommand object.
 */
public class ExportExcelCommandParser implements Parser<ExportExcelCommand> {
    private Logger logger = LogsCenter.getLogger(ExportExcelCommandParser.class);

    /**
     * Parses the given code {@code String} of arguments ini the context of the SummaryCommand
     * @param args
     * @return an SummaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ExportExcelCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ExportExcelCommand.MESSAGE_USAGE));
        }
        String[] dates = splitByWhitespace(trimmedArgs);
        if (Arrays.asList(dates).size() != 2) {
            throw new ParseException(
                    String.format(
                            Messages.MESSAGE_INVALID_COMMAND_FORMAT
                                    + Messages.MESSAGE_INVALID_DATE_REQUIRED, ExportExcelCommand.MESSAGE_USAGE));
        }
        logger.info(String.format("The Start Date: %1$s, the End Date: %2$s\n",
                Arrays.asList(dates).get(0), Arrays.asList(dates).get(1)));

        Date startDate = ParserUtil.parseDate(Arrays.asList(dates).get(0));
        Date endDate = ParserUtil.parseDate(Arrays.asList(dates).get(1));

        if (isDateOrderValid(startDate, endDate)) {
            DateIsWithinIntervalPredicate predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
            return new ExportExcelCommand(predicate);
        } else {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_STARTDATE_ENDDATE));
        }
    }
    /**
     * Splits a string using whitespace as delimiters
     * @param args String arguments that have 2 dates.
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
