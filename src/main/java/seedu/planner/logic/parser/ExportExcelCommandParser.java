package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DIR;
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
    private static Logger logger = LogsCenter.getLogger(ExportExcelCommandParser.class);

    /**
     * Parses the given code {@code String} of arguments in the context of the ExportExcelCommand
     * @return an ExportExcelCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ExportExcelCommand parse(String args) throws ParseException {
        String stringDate = " ";
        String stringPath = " ";
        String trimmedArgs = args.trim();
        logger.info(trimmedArgs);
        if (args.isEmpty()) {
            return new ExportExcelCommand();
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_DIR);
        stringDate += retrieveDataFromPrefix(argMultimap, PREFIX_DATE);
        stringPath += retrieveDataFromPrefix(argMultimap, PREFIX_DIR);
        logger.info("stringDate: " + stringDate + " stringPath: " + stringPath);
        return parseArgumentsModeIntoCommand(args, stringDate.trim(), stringPath.trim());
    }

    /**
     * Return the String value after retrieve data from prefix.
     */
    public static String retrieveDataFromPrefix (ArgumentMultimap argumentMultimap, Prefix prefix) {
        if (argumentMultimap.getValue(prefix).isPresent()) {
            return argumentMultimap.getValue(prefix).get();
        }
        return null;
    }
    /**
     * Parse the arguments into different argument mode, hence, we will have different command mode.
     */
    public static ExportExcelCommand parseArgumentsModeIntoCommand (String args, String stringDate, String stringPath)
            throws ParseException {
        String directoryPath;
        if (stringDate.isEmpty() && stringPath.isEmpty()) {
            return new ExportExcelCommand();
        } else if (stringDate.isEmpty()) {
            directoryPath = ParserUtil.parseDirectoryString(stringPath);
            return new ExportExcelCommand(directoryPath);
        } else {
            String[] dates = splitByWhitespace(stringDate);
            return parseDateIntoDifferentMode(dates, stringPath);
        }
    }
    private static ExportExcelCommand parseDateIntoDifferentMode (String[] dates, String stringPath)
            throws ParseException {
        Date startDate;
        Date endDate;
        String directoryPath;
        int dateNum = Arrays.asList(dates).size();
        if (dateNum > ExportExcelCommand.DUO_MODE) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT
                            + Messages.MESSAGE_INVALID_DATE_REQUIRED, ExportExcelCommand.MESSAGE_USAGE));
        } else if (dateNum == ExportExcelCommand.SINGLE_MODE) {
            startDate = ParserUtil.parseDate(Arrays.asList(dates).get(ExportExcelCommand.FIRST_ELEMENT).trim());
            endDate = ParserUtil.parseDate(Arrays.asList(dates).get(ExportExcelCommand.FIRST_ELEMENT).trim());
        } else {
            startDate = ParserUtil.parseDate(Arrays.asList(dates).get(ExportExcelCommand.FIRST_ELEMENT).trim());
            endDate = ParserUtil.parseDate(Arrays.asList(dates).get(ExportExcelCommand.SECOND_ELEMENT).trim());
        }
        if (isDateOrderValid(startDate, endDate)) {
            if (stringPath.isEmpty()) {
                return new ExportExcelCommand(startDate, endDate);
            } else {
                directoryPath = ParserUtil.parseDirectoryString(stringPath);
                return new ExportExcelCommand(startDate, endDate, directoryPath);
            }
        } else {
            throw new ParseException(Messages.MESSAGE_INVALID_STARTDATE_ENDDATE);
        }
    }
    /**
     * Splits a string using whitespace as delimiters
     * @param args String arguments that have 2 dates.
     * @return array of split strings
     */
    public static String[] splitByWhitespace(String args) {
        if (args.isEmpty()) {
            return null;
        }
        return args.split("\\s+");
    }
    /**
     * Check whether the Dates are valid period or not.
     */
    public static boolean isDateOrderValid(Date startDate, Date endDate) {
        return startDate.isEarlierThan(endDate) || startDate.equals(endDate);
    }
}
