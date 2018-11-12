package seedu.divelog.testutil;

import static seedu.divelog.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.divelog.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.divelog.logic.parser.CliSyntax.PREFIX_DEPTH;
import static seedu.divelog.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.divelog.logic.parser.CliSyntax.PREFIX_SAFETY_STOP;
import static seedu.divelog.logic.parser.CliSyntax.PREFIX_TIMEZONE;
import static seedu.divelog.logic.parser.CliSyntax.PREFIX_TIME_END;
import static seedu.divelog.logic.parser.CliSyntax.PREFIX_TIME_START;

import seedu.divelog.logic.commands.AddCommand;
import seedu.divelog.logic.commands.EditCommand;
import seedu.divelog.model.dive.DiveSession;

/**
 * A utility class for DiveSession.
 */
public class DiveUtil {

    /**
     * Returns an add command string for adding the {@code dive}.
     */
    public static String getAddCommand(DiveSession dive) {
        return AddCommand.COMMAND_WORD + " " + getDiveDetails(dive);
    }

    /**
     * Returns the part of command string for the given {@code dive}'s details.
     * @param dive
     */
    public static String getDiveDetails(DiveSession dive) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DATE_START + dive.getDateStart().getOurDateString() + " ");
        sb.append(PREFIX_TIME_START + dive.getStart().getTimeString() + " ");
        sb.append(PREFIX_DATE_END + dive.getDateStart().getOurDateString() + " ");
        sb.append(PREFIX_TIME_END + dive.getEnd().getTimeString() + " ");
        sb.append(PREFIX_SAFETY_STOP + dive.getSafetyStop().getTimeString() + " ");
        sb.append(PREFIX_DEPTH + String.valueOf(dive.getDepthProfile().getDepth()) + " ");
        sb.append(PREFIX_LOCATION + dive.getLocation().getLocationName() + " ");
        sb.append(PREFIX_TIMEZONE + dive.getTimeZone().getTimeZoneString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditDiveDescriptor}'s details.
     */
    public static String getEditDiveDescriptorDetails(EditCommand.EditDiveDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getStart().ifPresent(timeStart ->
                sb.append(PREFIX_TIME_START).append(timeStart.getTimeString()).append(" "));
        descriptor.getEnd().ifPresent(timeEnd ->
                sb.append(PREFIX_TIME_END).append(timeEnd.getTimeString()).append(" "));
        descriptor.getSafetyStop().ifPresent(safetyStop ->
                sb.append(PREFIX_SAFETY_STOP).append(safetyStop.getTimeString()).append(" "));
        descriptor.getDepthProfile().ifPresent(depth ->
                sb.append(PREFIX_DEPTH).append(depth.getDepth()).append(" "));
        descriptor.getLocation().ifPresent(location ->
                sb.append(PREFIX_LOCATION).append(location.getLocationName()).append(" "));
        return sb.toString();
    }
}
