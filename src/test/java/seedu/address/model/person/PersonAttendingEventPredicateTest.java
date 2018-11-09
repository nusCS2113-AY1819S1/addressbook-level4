package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class PersonAttendingEventPredicateTest {

    private Set<String> attendeesSetOne;
    private Event eventOne;
    private Event eventTwo;
    private Person personOne;
    private Person personTwo;

    @Before
    public void setup() {
        attendeesSetOne = new HashSet<>();

        personOne = ALICE;
        personTwo = BOB;

        attendeesSetOne.add(personOne.getEmail().toString());

        eventOne = new EventBuilder().withAttendee(attendeesSetOne).build();
        eventTwo = new EventBuilder().build();
    }

    @Test
    public void equals() {

        PersonAttendingEventPredicate firstPredicate =
                new PersonAttendingEventPredicate(eventOne);

        PersonAttendingEventPredicate secondPredicate =
                new PersonAttendingEventPredicate(eventTwo);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // copy of object -> returns true
        PersonAttendingEventPredicate firstPredicateCopy =
                new PersonAttendingEventPredicate(eventOne);
        assertEquals(firstPredicate, firstPredicateCopy);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different event -> returns false
        assertNotEquals(firstPredicate, secondPredicate);

    }

    @Test
    public void test_eventHasAttendee_returnTrue() {
        PersonAttendingEventPredicate predicate =
                new PersonAttendingEventPredicate(eventOne);

        assertTrue(predicate.test(personOne));
    }

    @Test
    public void test_eventDoesNotHaveAttendee_returnFalse() {
        PersonAttendingEventPredicate predicate =
                new PersonAttendingEventPredicate(eventOne);

        assertFalse(predicate.test(personTwo));
    }

    @Test
    public void test_eventHasNoAttendees_returnFalse() {
        PersonAttendingEventPredicate predicate =
                new PersonAttendingEventPredicate(eventTwo);

        assertFalse(predicate.test(personOne));
    }


}
