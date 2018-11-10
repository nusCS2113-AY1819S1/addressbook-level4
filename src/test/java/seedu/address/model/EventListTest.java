package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PUNCTUAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_LT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_2;
import static seedu.address.testutil.TypicalEvents.EVENT_3;
import static seedu.address.testutil.TypicalEvents.EVENT_4;
import static seedu.address.testutil.TypicalEvents.EVENT_5;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

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
    public void resetData_withValidReadOnlyEventList_replacesData() {
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
        Event editedEvent = new EventBuilder(EVENT_1)
                .withDescription(VALID_DESCRIPTION_PUNCTUAL)
                .build();
        assertTrue(eventList.hasEvent(editedEvent));
    }

    @Test
    public void hasEvent_eventWithDifferentLocationInEventList_returnsFalse() {
        eventList.addEvent(EVENT_1);
        Event editedEvent = new EventBuilder(EVENT_1).withLocation(VALID_LOCATION_LT)
                .withDescription(VALID_DESCRIPTION_PUNCTUAL)
                .build();
        assertFalse(eventList.hasEvent(editedEvent));
    }

    @Test
    public void hasEventAfterEdit_eventNotInList_returnsFalse() {
        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);
        assertFalse(eventList.hasEventAfterEdit(EVENT_1, EVENT_4));
    }

    @Test
    public void hasEventAfterEdit_eventOverlapInList_returnsTrue() {
        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);
        assertTrue(eventList.hasEventAfterEdit(EVENT_1, EVENT_2));
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
        eventList.addEvent(EVENT_3);
        assertTrue(eventList.hasClash(EVENT_1, VALID_EMAIL_ALICE));
    }

    @Test
    public void hasClash_eventDoesNotClashWithList_returnsFalse() {
        eventList.addEvent(EVENT_3);
        assertFalse(eventList.hasClash(EVENT_3, VALID_NAME_BOB));
    }

    @Test
    public void hasClashAfterEdit_eventClashWithList_returnsTrue() {
        eventList.addEvent(EVENT_3);
        eventList.addEvent(EVENT_5);
        Event editedEvent = new EventBuilder(EVENT_5).withLocation("Test Location 3").withDate("2018-09-18").build();
        assertTrue(eventList.hasClashAfterEdit(EVENT_5, editedEvent, VALID_EMAIL_ALICE));
    }

    @Test
    public void hasClashAfterEdit_eventDoesNotClashWithList_returnsFalse() {
        eventList.addEvent(EVENT_3);
        eventList.addEvent(EVENT_5);
        Event editedEvent = new EventBuilder(EVENT_5).withLocation("Test Location 3").build();
        assertFalse(eventList.hasClashAfterEdit(EVENT_5, editedEvent, VALID_EMAIL_ALICE));
    }


}
