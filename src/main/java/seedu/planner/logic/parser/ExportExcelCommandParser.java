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
    private static String WHITE_SPACE = " ";

    /**
     * Parses the given code {@code String} of arguments in the context of the ExportExcelCommand
     * @return an ExportExcelCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ExportExcelCommand parse(String args) throws ParseException {
        String stringDate = " ";
        String stringPath = " ";
        String trimmedArgs = args.trim();
        logger.info("TRIMMED ARGS: " + trimmedArgs);
        if (trimmedArgs.isEmpty()) {
            return new ExportExcelCommand();
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_DATE, PREFIX_DIR);
        String stringDateRetrievedFromPrefix = retrieveDataFromPrefix(argMultimap, PREFIX_DATE);
        String stringPathRetrievedFromPrefix = retrieveDataFromPrefix(argMultimap, PREFIX_DIR);
        String stringDateRetrievedFromPrefixCopy = argMultimap.getValue(PREFIX_DATE).get();
        String stringPathRetrievedFromPrefixCopy = argMultimap.getValue(PREFIX_DIR).get();
        logger.info("retrieveDataFromPrefix(argMultimap, PREFIX_DATE)" + stringDateRetrievedFromPrefix);
        logger.info("retrieveDataFromPrefix(argMultimap, PREFIX_DIR)" + stringPathRetrievedFromPrefix);
        logger.info("retrieveDataFromPrefixCopy(argMultimap, PREFIX_DATE)" + stringDateRetrievedFromPrefixCopy);
        logger.info("retrieveDataFromPrefixCopy(argMultimap, PREFIX_DATE)" + stringPathRetrievedFromPrefixCopy);
        if (stringDateRetrievedFromPrefix.trim().isEmpty() && stringPathRetrievedFromPrefix.trim().isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ExportExcelCommand.MESSAGE_USAGE));
        }
        stringDate += retrieveDataFromPrefix(argMultimap, PREFIX_DATE);
        stringPath += retrieveDataFromPrefix(argMultimap, PREFIX_DIR);
        logger.info("stringDate: " + stringDate + " stringPath: " + stringPath);
        return parseArgumentsModeIntoCommand(stringDate.trim(), stringPath.trim());
    }

    /**
     * Parse the arguments into different argument mode, hence, we will have different command mode.
     */
    public static ExportExcelCommand parseArgumentsModeIntoCommand (String stringDate, String stringPath)
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

    /**
     * Parse the string Date into different mode, hence return different commands.
     */
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
            if (stringPath == null || stringPath.isEmpty()) {
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
     * Return the String value after retrieve data from prefix.
     */
    public static String retrieveDataFromPrefix (ArgumentMultimap argumentMultimap, Prefix prefix) {
        if (argumentMultimap.getValue(prefix).isPresent()) {
            return argumentMultimap.getValue(prefix).get();
        }
        return WHITE_SPACE;
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
