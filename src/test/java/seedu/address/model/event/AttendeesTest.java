package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CALVIN;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.Assert;

public class AttendeesTest {

    private Set<String> attendeesSetOne;
    private Set<String> attendeesSetTwo;
    private Set<String> attendeesSetThree;
    private Set<String> attendeesSetNull;
    private Attendees attendeesOne;
    private Attendees attendeesTwo;
    private Attendees attendeesThree;

    @Before
    public void setup() {
        attendeesSetOne = new HashSet<>();
        attendeesSetTwo = new HashSet<>();
        attendeesSetThree = new HashSet<>();
        attendeesSetNull = null;

        attendeesSetOne.add(VALID_NAME_AMY);
        attendeesSetTwo.add(VALID_NAME_BOB);
        attendeesSetThree.add(VALID_NAME_BOB);
        attendeesSetThree.add(VALID_NAME_CALVIN);

        attendeesOne = new Attendees(attendeesSetOne);
        attendeesTwo = new Attendees(attendeesSetTwo);
        attendeesThree = new Attendees(attendeesSetThree);
    }


    @Test
    public void constructor_nullSingleSet_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Attendees(attendeesSetNull));
    }

    @Test
    public void constructor_nullMultipleSet_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Attendees(attendeesSetOne,
                attendeesSetTwo, attendeesSetNull));
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
    }

    @Test
    public void hasName_presentName_success() {
        // Attendees has names it contain
        assertTrue(attendeesOne.hasName(VALID_NAME_AMY));

    }

    @Test
    public void hasName_absentName_success() {
        // Attendees does not have names absent in list
        assertFalse(attendeesOne.hasName(VALID_NAME_BOB));
    }


    @Test
    public void addName() {
        Attendees attendeesTwoNew = attendeesTwo.addName(VALID_NAME_CALVIN);
        assertEquals(attendeesTwoNew, attendeesThree);
    }

    @Test
    public void removeName() {
        Attendees attendeesThreeNew = attendeesThree.removeName(VALID_NAME_CALVIN);
        assertEquals(attendeesTwo, attendeesThreeNew);
    }

    @Test
    public void isSetEmpty_attendeeIsEmpty_success() {
        Attendees attendeesNull = new Attendees();
        assertTrue(attendeesNull.isSetEmpty());
    }

    @Test
    public void isSetEmpty_attendeeIsFilled_success() {
        assertFalse(attendeesOne.isSetEmpty());
    }



}
