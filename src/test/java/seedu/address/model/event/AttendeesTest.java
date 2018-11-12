package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CALVIN;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.Assert;

public class AttendeesTest {

    private Set<String> attendeesSetNull;
    private Attendees attendeesOne;
    private Attendees attendeesTwo;
    private Attendees attendeesThree;

    @Before
    public void setup() {

        attendeesOne = new Attendees(VALID_EMAIL_AMY);
        attendeesTwo = new Attendees(VALID_EMAIL_BOB);
        attendeesThree = new Attendees(VALID_EMAIL_BOB, VALID_EMAIL_CALVIN);
    }


    @Test
    public void constructor_nullSingleSet_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Attendees(attendeesSetNull));
    }


    @Test
    public void hasEmail_presentEmail_returnsTrue() {
        // Attendees has emails it contain
        assertTrue(attendeesOne.hasPerson(VALID_EMAIL_AMY));

    }

    @Test
    public void hasEmail_absentEmail_returnsTrue() {
        // Attendees does not have emails in list
        assertFalse(attendeesOne.hasPerson(VALID_EMAIL_BOB));
    }


    @Test
    public void createAttendeesWithAddedEmail_validEmail_success() {
        Attendees attendeesTwoNew = attendeesTwo.createAttendeesWithAddedEmail(VALID_EMAIL_CALVIN);
        assertEquals(attendeesTwoNew, attendeesThree);
    }

    @Test
    public void createAttendeesWithRemovedEmail_validEmail_success() {
        Attendees attendeesThreeNew = attendeesThree.createAttendeesWithRemovedEmail(VALID_EMAIL_CALVIN);
        assertEquals(attendeesTwo, attendeesThreeNew);
    }

    @Test
    public void isSetEmpty_attendeeIsEmpty_returnsTrue() {
        Attendees attendeesNull = new Attendees();
        assertTrue(attendeesNull.isSetEmpty());
    }

    @Test
    public void isSetEmpty_attendeeIsFilled_returnsFalse() {
        assertFalse(attendeesOne.isSetEmpty());
    }

    @Test
    public void equals() {

        // Attendees equal to itself
        assertTrue(attendeesOne.equals(attendeesOne));

        Attendees attendeesOneCopy = attendeesOne;
        // Attendees equal to one with same content
        assertTrue(attendeesOne.equals(attendeesOneCopy));

        // Attendees not equal to one with different content
        assertFalse(attendeesOne.equals(attendeesTwo));
        assertFalse(attendeesOne.equals(attendeesThree));
        assertFalse(attendeesTwo.equals(attendeesThree));

        // Attendees equal after adding new person
        Attendees attendeesTest = attendeesTwo.createAttendeesWithAddedEmail(VALID_EMAIL_CALVIN);
        assertTrue(attendeesTest.equals(attendeesThree));
    }

}
