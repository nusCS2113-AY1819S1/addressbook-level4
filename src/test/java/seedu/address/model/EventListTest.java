package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PUNCTUAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_LT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_3;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TypicalEvents;

public class EventListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventList eventList = new EventList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventList.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        EventList newData = getTypicalEventList();
        eventList.resetData(newData);
        assertEquals(newData, eventList);
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.hasEvent(null);
    }

    @Test
    public void hasEvent_eventNotInEventList_returnsFalse() {
        assertFalse(eventList.hasEvent(EVENT_1));
    }

    @Test
    public void hasEvent_eventInEventList_returnsTrue() {
        eventList.addEvent(EVENT_1);
        assertTrue(eventList.hasEvent(EVENT_1));
    }

    @Test
    public void hasEvent_eventWithSameIdentityFieldsInEventList_returnsTrue() {
        eventList.addEvent(EVENT_1);
        Event editedEvent = new EventBuilder(EVENT_1).withLocation(VALID_LOCATION_LT)
                .withDescription(VALID_DESCRIPTION_PUNCTUAL)
                .build();
        assertTrue(eventList.hasEvent(editedEvent));
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        eventList.getEventList().remove(0);
    }

    @Test
    public void removePersonFromAllEvents_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.removePersonFromAllEvents(null);
    }

    @Test
    public void hasClash_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.hasClash(null, VALID_NAME_ALICE);
    }

    @Test
    public void hasClash_nullPersonName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.hasClash(EVENT_1, null);
    }

    @Test
    public void hasClash_eventClashWithList_returnsTrue() {
        eventList.addEvent(TypicalEvents.eventwithAttendee());
        assertTrue(eventList.hasClash(EVENT_3, VALID_NAME_ALICE));
    }

    @Test
    public void hasClash_eventDoesNotClashWithList_returnsFalse() {
        eventList.addEvent(TypicalEvents.eventwithAttendee());
        assertFalse(eventList.hasClash(EVENT_3, VALID_NAME_BOB));
    }


}
