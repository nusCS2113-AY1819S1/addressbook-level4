package seedu.planner.logic.parser;

import java.util.Arrays;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;

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
        Date startDate;
        Date endDate;
        String trimmedArgs = args.trim();
        logger.info(trimmedArgs);

        if (trimmedArgs.isEmpty()) {
            return new ExportExcelCommand();
        } else {
            String[] dates = splitByWhitespace(trimmedArgs);
            int dateNum = Arrays.asList(dates).size();
            if (dateNum > 2) {
                throw new ParseException(
                        String.format(
                                Messages.MESSAGE_INVALID_COMMAND_FORMAT
                                        + Messages.MESSAGE_INVALID_DATE_REQUIRED, ExportExcelCommand.MESSAGE_USAGE));
            } else if (dateNum == 1) {
                startDate = ParserUtil.parseDate(Arrays.asList(dates).get(0));
                endDate = ParserUtil.parseDate(Arrays.asList(dates).get(0));
            } else {
                startDate = ParserUtil.parseDate(Arrays.asList(dates).get(0));
                endDate = ParserUtil.parseDate(Arrays.asList(dates).get(1));
            }
            logger.info(String.format("The Start Date: %1$s, the End Date: %2$s\n",
                    startDate.getValue(), endDate.getValue()));
        }
        if (isDateOrderValid(startDate, endDate)) {
            return new ExportExcelCommand(startDate, endDate);
        } else {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_STARTDATE_ENDDATE));
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
