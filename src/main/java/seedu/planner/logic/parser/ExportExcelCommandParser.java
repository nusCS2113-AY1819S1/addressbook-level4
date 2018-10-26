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
        } else {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_DIR);
            if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
                stringDate += argMultimap.getValue(PREFIX_DATE).get();
            }
            if (argMultimap.getValue(PREFIX_DIR).isPresent()) {
                stringPath += argMultimap.getValue(PREFIX_DIR).get();
            }
            logger.info("stringDate: " + stringDate + " stringPath: " + stringPath);
        }
        return parseArgumentsModeIntoCommand(args, stringDate, stringPath);
    }

    /**
     * Parse the arguments into different argument mode, hence, we will have different command mode.
     */
    private static ExportExcelCommand parseArgumentsModeIntoCommand (String args, String stringDate, String stringPath)
            throws ParseException {
        Date startDate;
        Date endDate;
        String directoryPath;
        if (stringDate == null && stringPath == null) {
                return new ExportExcelCommand();
        } else if (stringDate == null) {
            directoryPath = ParserUtil.parseDirectoryString(stringPath.trim());
            return new ExportExcelCommand(directoryPath);
        } else {
            String[] dates = splitByWhitespace(stringDate.trim());
            int dateNum = Arrays.asList(dates).size();
            if (dateNum > 2) {
                throw new ParseException(
                        String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT
                                + Messages.MESSAGE_INVALID_DATE_REQUIRED, ExportExcelCommand.MESSAGE_USAGE));
            } else if (dateNum == 1) {
                logger.info("HELLO :) BEFORE PARSE");
                startDate = ParserUtil.parseDate(Arrays.asList(dates).get(0).trim());
                endDate = ParserUtil.parseDate(Arrays.asList(dates).get(0).trim());
            } else {
                logger.info("HELLO :) BEFORE PARSE");
                startDate = ParserUtil.parseDate(Arrays.asList(dates).get(0).trim());
                endDate = ParserUtil.parseDate(Arrays.asList(dates).get(1).trim());
            }
            if (isDateOrderValid(startDate, endDate)) {
                if (stringPath == null) {
                    return new ExportExcelCommand(startDate, endDate);
                } else {
                    directoryPath = ParserUtil.parseDirectoryString(stringPath.trim());
                    return new ExportExcelCommand(startDate, endDate, directoryPath);
                }
            } else {
                throw new ParseException(Messages.MESSAGE_INVALID_STARTDATE_ENDDATE);
            }
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
    /**
     * Check whether the Dates are valid period or not.
     */
    private static boolean isDateOrderValid(Date startDate, Date endDate) {
        return startDate.isEarlierThan(endDate) || startDate.equals(endDate);
    }
}
