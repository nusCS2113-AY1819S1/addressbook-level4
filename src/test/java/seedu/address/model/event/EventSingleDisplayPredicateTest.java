package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_2;
import static seedu.address.testutil.TypicalEvents.EVENT_3;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.TypicalEvents;


public class EventSingleDisplayPredicateTest {

    private Event eventWithAlice;
    private Event eventOther;

    @Before
    public void setup() {
        eventWithAlice = EVENT_3;
        eventOther = TypicalEvents.EVENT_1;
    }

    @Test
    public void test_eventIsSelected_returnsTrue() {
        EventSingleDisplayPredicate predicate = new EventSingleDisplayPredicate(eventWithAlice);
        assertTrue(predicate.test(eventWithAlice));
    }

    @Test
    public void test_eventIsNotSelected_returnsFalse() {
        EventSingleDisplayPredicate predicate = new EventSingleDisplayPredicate(eventWithAlice);
        assertFalse(predicate.test(eventOther));
    }

    @Test
    public void equals() {

        EventSingleDisplayPredicate firstPredicate =
                new EventSingleDisplayPredicate(EVENT_1);
        EventSingleDisplayPredicate secondPredicate =
                new EventSingleDisplayPredicate(EVENT_2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // copy of object -> returns true
        EventSingleDisplayPredicate firstPredicateCopy =
                new EventSingleDisplayPredicate(EVENT_1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person and eventdate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

    }

}
