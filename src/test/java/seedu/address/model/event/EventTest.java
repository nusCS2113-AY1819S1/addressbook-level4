package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PUNCTUAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_LT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_NOON;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_2;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.EventBuilder;


public class EventTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Set<String> setOne;

    @Before
    public void setup() {
        setOne = new HashSet<>();
        setOne.add(VALID_EMAIL_ALICE);
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(EVENT_1.isSameEvent(EVENT_1));

        // null -> returns false
        assertFalse(EVENT_1.isSameEvent(null));

        // different location and time clash returns false
        Event editedEvent = new EventBuilder(EVENT_1).withLocation(VALID_LOCATION_LT).build();
        assertFalse(EVENT_1.isSameEvent(editedEvent));

        // same location and time does not clash -> returns false
        editedEvent = new EventBuilder(EVENT_1).withStartTime(VALID_TIME_MORNING).withEndTime(VALID_TIME_NOON).build();
        assertFalse(EVENT_1.isSameEvent(editedEvent));

        // same location and time clash, different name and description -> returns true
        editedEvent = new EventBuilder(EVENT_1).withEventName(VALID_EVENT_NAME_BIRTHDAY)
                .withDescription(VALID_DESCRIPTION_PUNCTUAL).build();
        assertTrue(EVENT_1.isSameEvent(editedEvent));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event eventCopy = new EventBuilder(EVENT_1).build();
        assertTrue(EVENT_1.equals(eventCopy));

        // same object -> returns true
        assertTrue(EVENT_1.equals(EVENT_1));

        // null -> returns false
        assertFalse(EVENT_1.equals(null));

        // different type -> returns false
        assertFalse(EVENT_1.equals(5));

        // different event -> returns false
        assertFalse(EVENT_1.equals(EVENT_2));

        // different name -> returns false
        Event editedEvent = new EventBuilder(EVENT_1).withEventName(VALID_EVENT_NAME_BIRTHDAY).build();
        assertFalse(EVENT_1.equals(editedEvent));

        // different location -> returns false
        editedEvent = new EventBuilder(EVENT_1).withLocation(VALID_LOCATION_LT).build();
        assertFalse(EVENT_1.equals(editedEvent));

        // different description -> returns false
        editedEvent = new EventBuilder(EVENT_1).withDescription(VALID_DESCRIPTION_PUNCTUAL).build();
        assertFalse(EVENT_1.equals(editedEvent));

        // different start time -> returns false
        editedEvent = new EventBuilder(EVENT_1).withStartTime(VALID_TIME_MORNING).build();
        assertFalse(EVENT_1.equals(editedEvent));

        // different end time -> returns false
        editedEvent = new EventBuilder(EVENT_1).withEndTime(VALID_TIME_NOON).build();
        assertFalse(EVENT_1.equals(editedEvent));

        // different date -> returns false
        editedEvent = new EventBuilder(EVENT_1).withDate(VALID_DATE).build();
        assertFalse(EVENT_1.equals(editedEvent));
    }

    @Test
    public void addPersonToAttendee_validEmailAdded_success() {
        Event eventToUpdate = new EventBuilder(EVENT_1).build();
        Event eventUpdated = new EventBuilder(EVENT_1).withAttendee(setOne).build();

        //add email
        Event event = eventToUpdate.createEventWithUpdatedAttendee(VALID_EMAIL_ALICE);

        assertEquals(event, eventUpdated);
    }

    @Test
    public void removePersonFromAttendee_validEmailRemoved_success() {
        Event eventToUpdate = new EventBuilder(EVENT_1).withAttendee(setOne).build();
        Event eventUpdated = new EventBuilder(EVENT_1).build();

        //remove email
        Event event = eventToUpdate.removePersonFromAttendee(VALID_EMAIL_ALICE);

        assertEquals(event, eventUpdated);
    }

    @Test
    public void hasAttendee_invitedPerson_returnsTrue() {
        Event event = new EventBuilder(EVENT_1).withAttendee(setOne).build();
        assertTrue(event.hasAttendee(VALID_EMAIL_ALICE));

    }

    @Test
    public void hasAttendee_uninvitedPerson_returnsFalse() {
        Event event = new EventBuilder(EVENT_1).withAttendee(setOne).build();
        assertFalse(event.hasAttendee(VALID_EMAIL_BOB));
    }

    @Test
    public void isAttendeeEmpty_attendeeContainsNoEmail_returnsTrue() {
        Event event = new EventBuilder(EVENT_1).build();
        assertTrue(event.isAttendeeEmpty());
    }

    @Test
    public void isAttendeeEmpty_attendeeContainsEmails_returnsFalse() {
        Event event = new EventBuilder(EVENT_1).withAttendee(setOne).build();
        assertFalse(event.isAttendeeEmpty());
    }

    @Test
    public void hasClash_sameEvent_returnTrue() {
        assertTrue(EVENT_1.hasClash(EVENT_1));
    }

    @Test
    public void hasClash_eventHasDifferentDateAndTimeOverlap_returnsFalse() {
        Event event = new EventBuilder(EVENT_1).withDate(VALID_DATE).build();
        assertFalse(EVENT_1.hasClash(event));
        assertFalse(event.hasClash(EVENT_1));
    }

    @Test
    public void hasClash_differentEventSameStartAndEndTime_returnsTrue() {
        Event event = new EventBuilder(EVENT_2).withStartTime("12:00").withEndTime("16:00").build();
        assertTrue(EVENT_1.hasClash(event));
        assertTrue(event.hasClash(EVENT_1));
    }

    @Test
    public void hasClash_eventHasSameDateSameStartTimeDifferentEndTime_returnsTrue() {
        //earlier end time
        Event event = new EventBuilder(EVENT_1).withEndTime("15:59").build();
        assertTrue(EVENT_1.hasClash(event));
        assertTrue(event.hasClash(EVENT_1));
        //later end time
        event = new EventBuilder(EVENT_1).withEndTime("16:01").build();
        assertTrue(EVENT_1.hasClash(event));
        assertTrue(event.hasClash(EVENT_1));
    }

    @Test
    public void hasClash_eventHasSameDateSameEndTimeDifferentStartTime_returnsTrue() {
        //earlier start time
        Event event = new EventBuilder(EVENT_1).withStartTime("11:59").build();
        assertTrue(EVENT_1.hasClash(event));
        assertTrue(event.hasClash(EVENT_1));
        //later start time
        event = new EventBuilder(EVENT_1).withStartTime("12:01").build();
        assertTrue(EVENT_1.hasClash(event));
        assertTrue(event.hasClash(EVENT_1));
    }

    @Test
    public void hasClash_eventHasSameDateDifferentTimeWhichOverlap_returnsTrue() {
        Event event = new EventBuilder(EVENT_1).withStartTime("12:01").withEndTime("16:01").build();
        assertTrue(EVENT_1.hasClash(event));
        assertTrue(event.hasClash(EVENT_1));
    }

    @Test
    public void hasClash_eventHasSameDateDifferentTimeWhichDoesNotOverlap_returnsTrue() {
        // after event
        Event event = new EventBuilder(EVENT_1).withStartTime("16:00").withEndTime("23:59").build();
        assertFalse(EVENT_1.hasClash(event));
        assertFalse(event.hasClash(EVENT_1));
        // before event
        event = new EventBuilder(EVENT_1).withStartTime("00:00").withEndTime("12:00").build();
        assertFalse(EVENT_1.hasClash(event));
        assertFalse(event.hasClash(EVENT_1));
    }

}
