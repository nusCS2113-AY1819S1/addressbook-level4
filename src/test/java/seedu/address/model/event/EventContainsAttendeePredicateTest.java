package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CALVIN;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.EventBuilder;

public class EventContainsAttendeePredicateTest {

    private Set<String> attendeesSetOne;
    private Set<String> attendeesSetTwo;
    private Set<String> attendeesSetThree;

    @Before
    public void setup() {
        attendeesSetOne = new HashSet<>();
        attendeesSetTwo = new HashSet<>();

        attendeesSetOne.add(VALID_NAME_AMY);
        attendeesSetTwo.add(VALID_NAME_BOB);
        attendeesSetTwo.add(VALID_NAME_CALVIN);
    }


    @Test
    public void equals() {
        String personNameAlice = "ALICE";
        String personNameBob = "BOB";

        EventContainsAttendeePredicate firstPredicate = new EventContainsAttendeePredicate(personNameAlice);
        EventContainsAttendeePredicate secondPredicate = new EventContainsAttendeePredicate(personNameBob);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        EventContainsAttendeePredicate firstPredicateCopy = new EventContainsAttendeePredicate(personNameAlice);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

    }

    @Test
    public void test_attendeesContainName_returnsTrue() {
        //event with attendee size one
        EventContainsAttendeePredicate predicate = new EventContainsAttendeePredicate(VALID_NAME_AMY);
        assertTrue(predicate.test(new EventBuilder().withAttendee(attendeesSetOne).build()));

        //event with attendee size more than one
        predicate = new EventContainsAttendeePredicate(VALID_NAME_BOB);
        assertTrue(predicate.test(new EventBuilder().withAttendee(attendeesSetTwo).build()));

    }

    @Test
    public void test_attendeesContainName_returnsFalse() {
        //empty name
        EventContainsAttendeePredicate predicate = new EventContainsAttendeePredicate("");
        assertFalse(predicate.test(new EventBuilder().withAttendee(attendeesSetOne).build()));

        //person not in attendee
        predicate = new EventContainsAttendeePredicate(VALID_NAME_AMY);
        assertFalse(predicate.test(new EventBuilder().withAttendee(attendeesSetTwo).build()));

        predicate = new EventContainsAttendeePredicate(VALID_NAME_CALVIN);
        assertFalse(predicate.test(new EventBuilder().withAttendee(attendeesSetOne).build()));

        //no attendee in event
        predicate = new EventContainsAttendeePredicate(VALID_NAME_CALVIN);
        assertFalse(predicate.test(new EventBuilder().build()));

    }
}
