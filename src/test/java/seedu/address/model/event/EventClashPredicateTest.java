package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_2;
import static seedu.address.testutil.TypicalEvents.EVENT_4;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.TypicalEvents;


public class EventClashPredicateTest {

    private Event eventWithAlice;

    @Before
    public void setup() {
        eventWithAlice = TypicalEvents.eventwithAttendee();
    }

    @Test
    public void test_eventDoNotContainAttendee_returnsFalse() {
        EventClashPredicate predicate = new EventClashPredicate(EVENT_1, VALID_NAME_BOB);
        assertFalse(predicate.test(eventWithAlice));
    }

    @Test
    public void test_eventClashWithList_returnsTrue() {
        EventClashPredicate predicate = new EventClashPredicate(EVENT_1, VALID_NAME_ALICE);
        assertTrue(predicate.test(eventWithAlice));
    }

    @Test
    public void test_eventDoesNotClashWithList_returnsFalse() {
        EventClashPredicate predicate = new EventClashPredicate(EVENT_4, VALID_NAME_BOB);
        assertFalse(predicate.test(eventWithAlice));
    }

    @Test
    public void equals() {

        EventClashPredicate firstPredicate =
                new EventClashPredicate(EVENT_1, VALID_NAME_ALICE);
        EventClashPredicate secondPredicate =
                new EventClashPredicate(EVENT_2, VALID_NAME_BOB);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // copy of object -> returns true
        EventClashPredicate firstPredicateCopy =
                new EventClashPredicate(EVENT_1, VALID_NAME_ALICE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person and eventdate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

    }

}
