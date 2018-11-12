package seedu.divelog.logic.parser;

import java.util.stream.Stream;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.util.CompareUtil;
import seedu.divelog.logic.commands.AddCommand;
import seedu.divelog.logic.parser.exceptions.ParseException;
import seedu.divelog.logic.pressuregroup.PressureGroupLogic;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.Location;
import seedu.divelog.model.dive.OurDate;
import seedu.divelog.model.dive.PressureGroup;
import seedu.divelog.model.dive.Time;
import seedu.divelog.model.dive.TimeZone;
import seedu.divelog.model.divetables.PadiDiveTable;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        CliSyntax.PREFIX_DATE_START,
                        CliSyntax.PREFIX_TIME_START,
                        CliSyntax.PREFIX_DATE_END,
                        CliSyntax.PREFIX_TIME_END,
                        CliSyntax.PREFIX_SAFETY_STOP,
                        CliSyntax.PREFIX_DEPTH,
                        CliSyntax.PREFIX_LOCATION,
                        CliSyntax.PREFIX_TIMEZONE);

        if (!arePrefixesPresent(argMultimap,
                CliSyntax.PREFIX_DATE_START,
                CliSyntax.PREFIX_TIME_START,
                CliSyntax.PREFIX_DATE_END,
                CliSyntax.PREFIX_TIME_END,
                CliSyntax.PREFIX_SAFETY_STOP,
                CliSyntax.PREFIX_DEPTH,
                CliSyntax.PREFIX_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }



        ParserUtil.checkTimeDateLimit(argMultimap);
        ParserUtil.checkDateFormat(argMultimap);
        ParserUtil.checkTimeZoneformat(argMultimap.getValue(CliSyntax.PREFIX_TIMEZONE).get());
        ParserUtil.checkTimeformat(argMultimap.getValue(CliSyntax.PREFIX_TIME_START).get(),
                argMultimap.getValue(CliSyntax.PREFIX_TIME_END).get(),
                argMultimap.getValue(CliSyntax.PREFIX_SAFETY_STOP).get());

        OurDate dateStart = new OurDate(argMultimap.getValue(CliSyntax.PREFIX_DATE_START).get());
        Time startTime = new Time(argMultimap.getValue(CliSyntax.PREFIX_TIME_START).get());
        OurDate dateEnd = new OurDate(argMultimap.getValue(CliSyntax.PREFIX_DATE_END).get());
        Time endTime = new Time(argMultimap.getValue(CliSyntax.PREFIX_TIME_END).get());
        Time safetyStop = new Time(argMultimap.getValue(CliSyntax.PREFIX_SAFETY_STOP).get());
        PressureGroup pressureGroupAtBeginning = new PressureGroup("A");
        Location location =
                new Location(argMultimap.getValue(CliSyntax.PREFIX_LOCATION).get());
        DepthProfile depthProfile = ParserUtil.parseDepth(argMultimap.getValue(CliSyntax.PREFIX_DEPTH).get());
        TimeZone timezone = new TimeZone(argMultimap.getValue(CliSyntax.PREFIX_TIMEZONE).get());
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();

        try {
            long duration = CompareUtil.checkTimeDifference(startTime.getTimeString(), endTime.getTimeString(),
                    dateStart.getOurDateString(), dateEnd.getOurDateString());

            PressureGroup pressureGroupAtEnd = PressureGroupLogic.computePressureGroupFirstDive(depthProfile,
                    (float) duration);

            DiveSession dive =
                    new DiveSession(dateStart, startTime, safetyStop, dateEnd, endTime, pressureGroupAtBeginning,
                            pressureGroupAtEnd, location, depthProfile, timezone);
            return new AddCommand(dive);
        } catch (LimitExceededException e) {
            throw new ParseException(Messages.MESSAGE_ERROR_LIMIT_EXCEED + " Max time you can spend at "
                    + depthProfile.getDepth() + "m is " + padiDiveTable.getMaxBottomTime(depthProfile) + " minutes");
        } catch (java.text.ParseException pe) {
            throw new ParseException(Messages.MESSAGE_INVALID_TIME_FORMAT);
        }

    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
