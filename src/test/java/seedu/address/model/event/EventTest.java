package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PUNCTUAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_LT;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.EventBuilder;


public class EventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(EVENT_1.isSameEvent(EVENT_1));

        // null -> returns false
        assertFalse(EVENT_1.isSameEvent(null));

        // different location and -> returns false
        Event editedEvent = new EventBuilder(EVENT_1).withLocation(VALID_LOCATION_LT)
                .withDescription(VALID_DESCRIPTION_PUNCTUAL).build();
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

        // TODO: update this after time is added instead of date
        // different start time -> returns false
        //editedEvent = new EventBuilder(EVENT_1).withStartTime(VALID_TIME_MORNING).build();
        //assertFalse(EVENT_1.equals(editedEvent));

        // different end time -> returns false
        //editedEvent = new EventBuilder(EVENT_1).withEndTime(VALID_TIME_NOON).build();
        //assertFalse(EVENT_1.equals(editedEvent));
    }
}
