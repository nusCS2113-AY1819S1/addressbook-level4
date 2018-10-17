package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DRNAME_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRNAME_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_APPT2;

import seedu.address.model.timetable.Appt;

/**
 * A utility class containing a list of {@code Appt} objects to be used in tests.
 */
public class TypicalAppts {

    public static final Appt APPT_EXAMPLE1 = new ApptBuilder().withApptStart(VALID_START_APPT1)
            .withApptEnd(VALID_END_APPT1).withApptVenue(VALID_VENUE_APPT1).withApptInfo(VALID_INFO_APPT1)
            .withApptDrName(VALID_DRNAME_APPT1).build();
    public static final Appt APPT_EXAMPLE2 = new ApptBuilder().withApptStart(VALID_START_APPT2)
            .withApptEnd(VALID_END_APPT2).withApptVenue(VALID_VENUE_APPT2).withApptInfo(VALID_INFO_APPT2)
            .withApptDrName(VALID_DRNAME_APPT2).build();
    public static final Appt EMPTY_APPT = new ApptBuilder().withApptStart("").withApptEnd("").withApptVenue("")
            .withApptInfo("").withApptDrName("").build();
}
