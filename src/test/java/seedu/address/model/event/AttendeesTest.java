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
    }


    @Test
    public void constructor_nullSingleSet_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Attendees(attendeesSetNull));
    }

    @Test
    public void constructor_nullMultipleSet_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Attendees(attendeesSetOne, attendeesSetNull));
    }

    @Test
    public void equals() {
        Attendees listOne = new Attendees(attendeesSetOne);
        Attendees listOneCopy = new Attendees(attendeesSetOne);
        Attendees listTwo = new Attendees(attendeesSetTwo);
        Attendees listThree = new Attendees(attendeesSetThree);

        // Attendees equal to itself
        assertTrue(listOne.equals(listOne));

        // Attendees equal to one with same content
        assertTrue(listOne.equals(listOneCopy));

        // Attendees not equal to one with different content
        assertFalse(listOne.equals(listTwo));
        assertFalse(listOne.equals(listThree));
        assertFalse(listTwo.equals(listThree));
    }

    @Test
    public void hasName() {
        Attendees attendees = new Attendees(attendeesSetOne);

        // Attendees has names it contain
        assertTrue(attendees.hasName(VALID_NAME_AMY));

        // Attendees does not have names absent in list
        assertFalse(attendees.hasName(VALID_NAME_BOB));
    }

    @Test
    public void addName() {
        Attendees attendees1 = new Attendees(attendeesSetTwo);
        Attendees attendees2 = new Attendees(attendeesSetTwo);

        // TODO: FIX use of attribute's method
        attendees1.addName(VALID_NAME_CALVIN);
        attendees2.attendeesSet.add(VALID_NAME_CALVIN);

        assertEquals(attendees1, attendees2);
    }

    @Test
    public void isSetEmpty() {
        Attendees attendeesFilled = new Attendees(attendeesSetOne);
        Attendees attendeesEmpty = new Attendees();

        assertTrue(attendeesEmpty.isSetEmpty());

        assertFalse(attendeesFilled.isSetEmpty());
    }


}
