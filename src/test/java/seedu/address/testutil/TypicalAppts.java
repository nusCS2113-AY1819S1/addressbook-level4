package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DRNAME_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRNAME_APPT3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_APPT3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_APPT4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_APPT3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_APPT3;

import seedu.address.model.appt.Appt;

//@@author brandonccm1996
/**
 * A utility class containing a list of {@code Appt} objects to be used in tests.
 */
public class TypicalAppts {

    public static final Appt APPT_EXAMPLE1 = new ApptBuilder().build();
    public static final Appt APPT_EXAMPLE2 = new ApptBuilder().withApptStart(VALID_START_APPT2)
            .withApptEnd(VALID_END_APPT2).withApptVenue(VALID_VENUE_APPT2).withApptInfo(VALID_INFO_APPT2)
            .withApptDrName(VALID_DRNAME_APPT2).build();
    public static final Appt APPT_EXAMPLE3 = new ApptBuilder().withApptStart(VALID_START_APPT3)
            .withApptEnd(VALID_END_APPT3).withApptVenue(VALID_VENUE_APPT3).withApptInfo(VALID_INFO_APPT3)
            .withApptDrName(VALID_DRNAME_APPT3).build();
    public static final Appt APPT_EXAMPLE4 = new ApptBuilder().withApptStart(VALID_START_APPT4)
            .withApptEnd(VALID_END_APPT4).withApptVenue(VALID_VENUE_APPT3).withApptInfo(VALID_INFO_APPT3)
            .withApptDrName(VALID_DRNAME_APPT3).build();

    public static final Appt INVALID_APPT_EXAMPLE1 = new ApptBuilder().withApptStart(VALID_START_APPT3)
            .withApptEnd(VALID_END_APPT2).withApptVenue(VALID_VENUE_APPT3).withApptInfo(VALID_INFO_APPT3)
            .withApptDrName(VALID_DRNAME_APPT3).build();
}
