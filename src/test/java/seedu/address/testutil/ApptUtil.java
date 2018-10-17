package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DRNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_VENUE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import seedu.address.logic.commands.AddApptCommand;
import seedu.address.model.timetable.Appt;

/**
 * A utility class for Appt.
 */
public class ApptUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddApptCommand(Appt appt) {
        return AddApptCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " " + getApptDetails(appt);
    }

    /**
     * Returns the part of command string for the given {@code appt}'s details.
     */
    public static String getApptDetails(Appt appt) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_APPT_START + appt.getStart().value + " ");
        sb.append(PREFIX_APPT_END + appt.getEnd().value + " ");
        sb.append(PREFIX_APPT_VENUE + appt.getVenue().value + " ");
        sb.append(PREFIX_APPT_INFO + appt.getInfo().value + " ");
        sb.append(PREFIX_APPT_DRNAME + appt.getDrName().value + " ");
        return sb.toString();
    }
}
