package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_OVERLOAD_PREFIX_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_YEAR;

import java.util.StringTokenizer;
import java.util.stream.Stream;

import seedu.address.logic.commands.CalculateLeavesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.EmployeeId;
import seedu.address.model.schedule.Year;

/**
 * Parses input arguments and creates a new {@code CalculateLeavesCommand} object
 */
public class CalculateLeavesCommandParser implements Parser<CalculateLeavesCommand> {

    public static final int TOTAL_NUM_TOKEN_CALCULATE_SCHEDULE = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the CalculateLeavesCommand
     * and returns an CalculateLeavesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CalculateLeavesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEEID, PREFIX_SCHEDULE_YEAR);

        StringTokenizer st = new StringTokenizer(args);
        if (st.countTokens() > TOTAL_NUM_TOKEN_CALCULATE_SCHEDULE) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_OVERLOAD_PREFIX_FORMAT,
                    CalculateLeavesCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_SCHEDULE_YEAR, PREFIX_EMPLOYEEID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CalculateLeavesCommand.MESSAGE_USAGE));
        }

        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_SCHEDULE_YEAR).get());
        EmployeeId id = ParserUtil.parseEmployeeId(argMultimap.getValue(PREFIX_EMPLOYEEID).get());

        return new CalculateLeavesCommand(id, year);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

