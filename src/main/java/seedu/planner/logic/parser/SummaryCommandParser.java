package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SummaryCommand object from the string input.
 * Selects which parser to use for parsing by identifying specific keywords
 */
public class SummaryCommandParser implements Parser<SummaryCommand> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_SUMMARY_COMMAND_FORMAT = Pattern.compile("(?<keyword>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into a new SummaryCommand for execution.
     *
     * @param args input string after the "summary" keyword
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public SummaryCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_SUMMARY_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
        }
        String keyword = matcher.group("keyword").trim().toLowerCase();
        String arguments = matcher.group("arguments");
        switch (keyword) {
        case "date":
            return new SummaryByDateCommandParser().parse(arguments);
        case "month" :
            return new SummaryByMonthCommandParser().parse(arguments);
        default :
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
        }
    }
}
