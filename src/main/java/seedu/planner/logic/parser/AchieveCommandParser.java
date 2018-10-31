package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DIR;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.ExportExcelCommandParser.isDateOrderValid;
import static seedu.planner.logic.parser.ExportExcelCommandParser.retrieveDataFromPrefix;
import static seedu.planner.logic.parser.ExportExcelCommandParser.splitByWhitespace;

import java.util.Arrays;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.logic.commands.AchieveCommand;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;

/**
 * Achieve the records, export into  Excel file then delete all records exported.
 */
public class AchieveCommandParser implements Parser<AchieveCommand> {
    private Logger logger = LogsCenter.getLogger(AchieveCommand.class);

    /**
     * Parses input arguments and create AchieveCommand object.
     */
    public AchieveCommand parse(String args) throws ParseException {
        String stringDate = " ";
        String stringPath = " ";
        String trimmedArgs = args.trim();
        logger.info(trimmedArgs);
        if (trimmedArgs.isEmpty()) {
            return new AchieveCommand();
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_DIR);
        stringDate += retrieveDataFromPrefix(argMultimap, PREFIX_NAME);
        stringPath += retrieveDataFromPrefix(argMultimap, PREFIX_DIR);
        logger.info("stringDate: " + stringDate + " stringPath: " + stringPath);
        return parseArgumentsModeIntoCommand(args, stringDate, stringPath);
    }

    /**
     * Parse the arguments into different argument mode, hence, we will have different command mode.
     */
    public static AchieveCommand parseArgumentsModeIntoCommand (String args, String stringDate, String stringPath)
            throws ParseException {
        String directoryPath;
        if (stringDate.isEmpty() && stringPath.isEmpty()) {
            return new AchieveCommand();
        } else if (stringDate.isEmpty()) {
            directoryPath = ParserUtil.parseDirectoryString(stringPath);
            return new AchieveCommand(directoryPath);
        } else {
            String[] dates = splitByWhitespace(stringDate);
            return parseDateIntoDifferentMode(dates, stringPath);
        }
    }

    /**
     * Parse the string Date into different mode and return different mode of command.
     */
    private static AchieveCommand parseDateIntoDifferentMode (String[] dates, String stringPath)
            throws ParseException {
        Date startDate;
        Date endDate;
        String directoryPath;
        int dateNum = Arrays.asList(dates).size();
        if (dateNum > AchieveCommand.DUO_MODE) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT
                            + Messages.MESSAGE_INVALID_DATE_REQUIRED, ExportExcelCommand.MESSAGE_USAGE));
        } else if (dateNum == AchieveCommand.SINGLE_MODE) {
            startDate = ParserUtil.parseDate(Arrays.asList(dates).get(AchieveCommand.FIRST_ELEMENT).trim());
            endDate = ParserUtil.parseDate(Arrays.asList(dates).get(AchieveCommand.FIRST_ELEMENT).trim());
        } else {
            startDate = ParserUtil.parseDate(Arrays.asList(dates).get(AchieveCommand.FIRST_ELEMENT).trim());
            endDate = ParserUtil.parseDate(Arrays.asList(dates).get(AchieveCommand.SECOND_ELEMENT).trim());
        }
        if (isDateOrderValid(startDate, endDate)) {
            if (stringPath.isEmpty()) {
                return new AchieveCommand(startDate, endDate);
            } else {
                directoryPath = ParserUtil.parseDirectoryString(stringPath);
                return new AchieveCommand(startDate, endDate, directoryPath);
            }
        } else {
            throw new ParseException(Messages.MESSAGE_INVALID_STARTDATE_ENDDATE);
        }
    }
}
