package seedu.address.model.timetable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRNAME_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_APPT2;
import static seedu.address.testutil.TypicalAppts.APPT_EXAMPLE1;
import static seedu.address.testutil.TypicalAppts.APPT_EXAMPLE2;

import org.junit.Test;

import seedu.address.testutil.ApptBuilder;

public class ApptTest {

    @Test
    public void equals() {
        Appt apptExample1Copy = new ApptBuilder(APPT_EXAMPLE1).build();

        // same object -> returns true
        assertTrue(APPT_EXAMPLE1.equals(APPT_EXAMPLE1));

        // same values -> returns true
        assertTrue(APPT_EXAMPLE1.equals(apptExample1Copy));

        // different types -> returns false
        assertFalse(APPT_EXAMPLE1.equals(5));

        // null -> returns false
        assertFalse(APPT_EXAMPLE1 == null);

        // different appt -> returns false
        assertFalse(APPT_EXAMPLE1.equals(APPT_EXAMPLE2));

        // different start time -> returns false
        Appt editedApptExample1 = new ApptBuilder(APPT_EXAMPLE1).withApptStart(VALID_START_APPT2).build();
        assertFalse(APPT_EXAMPLE1.equals(editedApptExample1));

        // different end time -> returns false
        editedApptExample1 = new ApptBuilder(APPT_EXAMPLE1).withApptEnd(VALID_END_APPT2).build();
        assertFalse(APPT_EXAMPLE1.equals(editedApptExample1));

        // different venue -> returns false
        editedApptExample1 = new ApptBuilder(APPT_EXAMPLE1).withApptVenue(VALID_VENUE_APPT2).build();
        assertFalse(APPT_EXAMPLE1.equals(editedApptExample1));

        // different info -> returns false
        editedApptExample1 = new ApptBuilder(APPT_EXAMPLE1).withApptVenue(VALID_INFO_APPT2).build();
        assertFalse(APPT_EXAMPLE1.equals(editedApptExample1));

        // different dr name -> returns false
        editedApptExample1 = new ApptBuilder(APPT_EXAMPLE1).withApptVenue(VALID_DRNAME_APPT2).build();
        assertFalse(APPT_EXAMPLE1.equals(editedApptExample1));
    }
}
