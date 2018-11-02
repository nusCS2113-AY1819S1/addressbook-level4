package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PUNCTUAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_LT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
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
        setOne.add(VALID_NAME_ALICE);
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(EVENT_1.isSameEvent(EVENT_1));

        // null -> returns false
        assertFalse(EVENT_1.isSameEvent(null));


        // different location and -> returns false
        Event editedEvent = new EventBuilder(EVENT_1).withLocation(VALID_LOCATION_LT)
                .withDescription(VALID_DESCRIPTION_PUNCTUAL).withStartTime(VALID_TIME_MORNING)
                .withEndTime(VALID_TIME_NOON).build();
        assertFalse(EVENT_1.isSameEvent(editedEvent));

        // different name -> returns false
        editedEvent = new EventBuilder(EVENT_1).withEventName(VALID_EVENT_NAME_BIRTHDAY).build();
        assertFalse(EVENT_1.isSameEvent(editedEvent));

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

        // different person -> returns false
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
    public void addPersonToAttendee_validNameAdded_success() {
        Event eventToUpdate = new EventBuilder(EVENT_1).build();
        Event eventUpdated = new EventBuilder(EVENT_1).withAttendee(setOne).build();

        //add name
        Event event = eventToUpdate.createEventWithUpdatedAttendee(VALID_NAME_ALICE);

        assertEquals(event, eventUpdated);
    }

    @Test
    public void removePersonFromAttendee_validNameRemoved_success() {
        Event eventToUpdate = new EventBuilder(EVENT_1).withAttendee(setOne).build();
        Event eventUpdated = new EventBuilder(EVENT_1).build();

        //remove name
        Event event = eventToUpdate.removePersonFromAttendee(VALID_NAME_ALICE);

        assertEquals(event, eventUpdated);
    }

    @Test
    public void hasAttendee_validName_success() {
        Event event = new EventBuilder(EVENT_1).withAttendee(setOne).build();
        assertTrue(event.hasAttendee("Alice Pauline"));

    }

    @Test
    public void hasAttendee_invalidName_fail() {
        Event event = new EventBuilder(EVENT_1).withAttendee(setOne).build();
        assertFalse(event.hasAttendee("Bob Choo"));
    }

    @Test
    public void isAttendeeEmpty_attendeeContainsNoName_success() {
        Event event = new EventBuilder(EVENT_1).build();
        assertTrue(event.isAttendeeEmpty());
    }

    @Test
    public void isAttendeeEmpty_attendeeContainsNames_success() {
        Event event = new EventBuilder(EVENT_1).withAttendee(setOne).build();
        assertFalse(event.isAttendeeEmpty());
    }
}
