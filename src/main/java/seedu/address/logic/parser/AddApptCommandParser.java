package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DRNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_VENUE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timetable.Appt;
import seedu.address.model.timetable.ApptDateTime;
import seedu.address.model.timetable.ApptDrName;
import seedu.address.model.timetable.ApptInfo;
import seedu.address.model.timetable.ApptVenue;

/**
 * Parses input arguments and creates a new {@code AddApptCommand} object
 */
public class AddApptCommandParser implements Parser<AddApptCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddApptCommand}
     * and returns a {@code AddApptCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddApptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPT_START, PREFIX_APPT_END,
                PREFIX_APPT_VENUE, PREFIX_APPT_INFO, PREFIX_APPT_DRNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_APPT_START, PREFIX_APPT_END, PREFIX_APPT_VENUE, PREFIX_APPT_INFO,
                PREFIX_APPT_DRNAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE), ive);
        }

        ApptDateTime start = ParserUtil.parseApptTime(argMultimap.getValue(PREFIX_APPT_START).get());
        ApptDateTime end = ParserUtil.parseApptTime(argMultimap.getValue(PREFIX_APPT_END).get());
        ApptVenue venue = ParserUtil.parseApptVenue(argMultimap.getValue(PREFIX_APPT_VENUE).get());
        ApptInfo info = ParserUtil.parseApptInfo(argMultimap.getValue(PREFIX_APPT_INFO).get());
        ApptDrName drName = ParserUtil.parseApptDrName(argMultimap.getValue(PREFIX_APPT_DRNAME).get());
        return new AddApptCommand(index, new Appt(start, end, venue, info, drName));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
