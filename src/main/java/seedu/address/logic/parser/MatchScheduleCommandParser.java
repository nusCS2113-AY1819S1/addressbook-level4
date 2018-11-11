package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MatchScheduleCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TheDate;
import seedu.address.model.person.Time;
import seedu.address.model.matchSchedule.MatchSchedule;


/**
 * Parses input arguments and creates a new MatchSchedule object
 */

public class MatchScheduleCommandParser implements Parser<MatchScheduleCommand>{
    /**
     * Parses the given {@code String} of arguments in the context of the MatchScheduleCommand
     * and returns an MatchScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchScheduleCommand parse(String args) throws ParseException {

        requireNonNull(args);

        List<Index> index;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchScheduleCommand.MESSAGE_USAGE));
        }

        TheDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Time startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        Time endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());
        List<Index> matchScheduleList = ParserUtil.parseMatchScheduleIndex(argMultimap.getAllValues(PREFIX_INDEX));
        MatchSchedule matchSchedule = new MatchSchedule(date, startTime, endTime, matchScheduleList);

        return new MatchScheduleCommand(matchSchedule, date, startTime, endTime, matchScheduleList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
