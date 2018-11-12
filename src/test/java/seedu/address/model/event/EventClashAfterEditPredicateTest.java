package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_CHRISTMAS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_LT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_ROOM;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_2;
import static seedu.address.testutil.TypicalEvents.EVENT_3;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.EventBuilder;

public class EventClashAfterEditPredicateTest {

    private Event eventBeforeEdit;
    private Event eventAfterEdit;
    private EventClashAfterEditPredicate predicate;

    @Before
    public void setup() {
        eventBeforeEdit = EVENT_3;
        eventAfterEdit = new EventBuilder(EVENT_3).withLocation(VALID_LOCATION_ROOM).build();
        predicate = new EventClashAfterEditPredicate(eventBeforeEdit, eventAfterEdit, VALID_EMAIL_ALICE);
    }

    @Test
    public void test_eventIsEventBeforeEdit_returnsFalse() {
        assertFalse(predicate.test(eventBeforeEdit));
    }

    @Test
    public void test_eventDoNotContainAttendee_returnsFalse() {
        assertFalse(predicate.test(new EventBuilder(EVENT_3).withAttendee().build()));
    }

    @Test
    public void test_eventDoesNotClash_returnsFalse() {
        assertFalse(predicate.test(new EventBuilder(EVENT_3).withDate(VALID_DATE_CHRISTMAS).build()));
    }

    @Test
    public void test_eventClash_returnsTrue() {
        assertTrue(predicate.test(new EventBuilder(EVENT_3).withEventName(VALID_EVENT_NAME_MEETING)
                .withLocation(VALID_LOCATION_LT).build()));
    }

    @Test
    public void equals() {

        EventClashAfterEditPredicate firstPredicate =
                new EventClashAfterEditPredicate(EVENT_1, EVENT_2, VALID_EMAIL_ALICE);
        EventClashAfterEditPredicate secondPredicate =
                new EventClashAfterEditPredicate(EVENT_2, EVENT_3, VALID_EMAIL_BOB);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // copy of object -> returns true
        EventClashAfterEditPredicate firstPredicateCopy =
                new EventClashAfterEditPredicate(EVENT_1, EVENT_2, VALID_EMAIL_ALICE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person and eventdate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

    }

}
