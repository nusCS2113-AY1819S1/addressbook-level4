package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_2;
import static seedu.address.testutil.TypicalEvents.EVENT_3;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
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
    public void test_eventHasDifferentName_returnsFalse() {
        EventBuilder eventBuilder = new EventBuilder(eventWithAlice);
        Event eventOther = eventBuilder
                .withEventName("Different name")
                .build();

        EventSingleDisplayPredicate predicate = new EventSingleDisplayPredicate(eventWithAlice);

        assertFalse(predicate.test(eventOther));
    }

    @Test
    public void test_eventHasDifferentLocation_returnsFalse() {
        EventBuilder eventBuilder = new EventBuilder(eventWithAlice);
        Event eventOther = eventBuilder
                .withLocation("Different location")
                .build();

        EventSingleDisplayPredicate predicate = new EventSingleDisplayPredicate(eventWithAlice);

        assertFalse(predicate.test(eventOther));
    }

    @Test
    public void test_eventHasDifferentDate_returnsFalse() {
        EventBuilder eventBuilder = new EventBuilder(eventWithAlice);
        Event eventOther = eventBuilder
                .withDate("2099-09-09")
                .build();

        EventSingleDisplayPredicate predicate = new EventSingleDisplayPredicate(eventWithAlice);

        assertFalse(predicate.test(eventOther));
    }

    @Test
    public void test_eventHasDifferentStartTime_returnsFalse() {
        EventBuilder eventBuilder = new EventBuilder(eventWithAlice);
        Event eventOther = eventBuilder
                .withStartTime("00:00")
                .build();

        EventSingleDisplayPredicate predicate = new EventSingleDisplayPredicate(eventWithAlice);

        assertFalse(predicate.test(eventOther));
    }

    @Test
    public void test_eventHasDifferentEndTime_returnsFalse() {
        EventBuilder eventBuilder = new EventBuilder(eventWithAlice);
        Event eventOther = eventBuilder
                .withEndTime("00:00")
                .build();

        EventSingleDisplayPredicate predicate = new EventSingleDisplayPredicate(eventWithAlice);

        assertFalse(predicate.test(eventOther));
    }

    @Test
    public void test_eventHasDifferentAttendees_returnsTrue() {
        // An invite command will cause the event to be rebuilt with different attendees.
        // It is, however, the same event and should return True
        PersonBuilder personBuilder = new PersonBuilder(BOB);
        Person person = personBuilder.withEmail("test_new_person@test.com")
                .withName("Test Nonconflicting Name")
                .build();
        String personEmail = person.getEmail().toString();

        Attendees attendees = eventWithAlice.getAttendees();
        Attendees attendeesUpdated = attendees.createAttendeesWithAddedEmail(personEmail);
        Set<String> setUpdated = attendeesUpdated.getAttendeesSet();

        Event eventDifferentAttendees = new EventBuilder(eventWithAlice).withAttendee(setUpdated).build();

        EventSingleDisplayPredicate predicate = new EventSingleDisplayPredicate(eventWithAlice);

        assertTrue(predicate.test(eventDifferentAttendees));
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
