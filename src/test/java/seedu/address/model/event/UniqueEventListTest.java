package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_CHRISTMAS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PUNCTUAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_LT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_2;
import static seedu.address.testutil.TypicalEvents.EVENT_3;
import static seedu.address.testutil.TypicalEvents.EVENT_4;
import static seedu.address.testutil.TypicalEvents.EVENT_5;
import static seedu.address.testutil.TypicalEvents.EVENT_6;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.testutil.EventBuilder;

/**
 * Based on UniquePersonListTest with minor modification and refactoring
 */
public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.contains(null);
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(EVENT_1));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(EVENT_1);
        assertTrue(uniqueEventList.contains(EVENT_1));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEventList.add(EVENT_1);
        Event editedEvent = new EventBuilder(EVENT_1).withDescription(VALID_DESCRIPTION_PUNCTUAL).build();
        assertTrue(uniqueEventList.contains(editedEvent));
    }

    @Test
    public void containsAfterEdit_nullEventToEdit_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.containsAfterEdit(null, EVENT_2);
    }

    @Test
    public void containsAfterEdit_nullEditedEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.containsAfterEdit(EVENT_1, null);
    }

    @Test
    public void containsAfterEdit_eventNotInList_returnsFalse() {
        uniqueEventList.add(EVENT_1);
        uniqueEventList.add(EVENT_6);
        assertFalse(uniqueEventList.containsAfterEdit(EVENT_1, EVENT_2));
    }

    @Test
    public void containsAfterEdit_eventInList_returnsTrue() {
        uniqueEventList.add(EVENT_1);
        assertTrue(uniqueEventList.containsAfterEdit(EVENT_2, EVENT_1));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.add(null);
    }

    @Test
    public void add_duplicateEvent_throwsDuplicatePersonException() {
        uniqueEventList.add(EVENT_1);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.add(EVENT_1);
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvent(null, EVENT_1);
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvent(EVENT_1, null);
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.setEvent(EVENT_1, EVENT_1);
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        uniqueEventList.add(EVENT_1);
        uniqueEventList.setEvent(EVENT_1, EVENT_1);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(EVENT_1);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentLocation_success() {
        uniqueEventList.add(EVENT_1);
        Event editedEvent = new EventBuilder(EVENT_1).withLocation(VALID_LOCATION_LT).build();
        uniqueEventList.setEvent(EVENT_1, editedEvent);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedEvent);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentDate_success() {
        uniqueEventList.add(EVENT_1);
        Event editedEvent = new EventBuilder(EVENT_1).withDate(VALID_DATE_CHRISTMAS).build();
        uniqueEventList.setEvent(EVENT_1, editedEvent);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedEvent);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(EVENT_1);
        uniqueEventList.setEvent(EVENT_1, EVENT_2);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(EVENT_2);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueEventList.add(EVENT_1);
        uniqueEventList.add(EVENT_2);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.setEvent(EVENT_1, EVENT_2);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.remove(null);
    }

    @Test
    public void remove_eventDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.remove(EVENT_1);
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(EVENT_1);
        uniqueEventList.remove(EVENT_1);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvents((UniqueEventList) null);
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(EVENT_1);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(EVENT_2);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvents((List<Event>) null);
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(EVENT_1);
        List<Event> eventList = Collections.singletonList(EVENT_2);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(EVENT_2);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(EVENT_1, EVENT_1);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.setEvents(listWithDuplicateEvents);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void removeAttendee_personEmailExistInList_success () {
        uniqueEventList.add(EVENT_3);
        uniqueEventList.removeAttendee(VALID_EMAIL_ALICE);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        Event updatedEvent = new EventBuilder(EVENT_3).withAttendee().build();
        expectedUniqueEventList.add(updatedEvent);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void removeAttendee_nullEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.removeAttendee(null);
    }

    @Test
    public void hasClash_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.hasClash(null, VALID_EMAIL_ALICE);
    }

    @Test
    public void hasClash_nullEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.hasClash(EVENT_1, null);
    }

    @Test
    public void hasClash_clashWithEventInList_returnsTrue() {
        uniqueEventList.add(EVENT_3);
        assertTrue(uniqueEventList.hasClash(EVENT_1, VALID_EMAIL_ALICE));
    }

    @Test
    public void hasClash_noEventInList_returnsFalse() {
        assertFalse(uniqueEventList.hasClash(EVENT_1, VALID_NAME_ALICE));
    }

    @Test
    public void hasClash_doesNotClashWithEventInList_returnsFalse() {
        uniqueEventList.add(EVENT_1);
        uniqueEventList.add(EVENT_3);
        assertFalse(uniqueEventList.hasClash(EVENT_4, VALID_EMAIL_ALICE));
    }

    @Test
    public void hasClashAfterEdit_clashWithEventInList_returnsTrue() {
        uniqueEventList.add(EVENT_3);
        uniqueEventList.add(EVENT_5);
        Event editedEvent = new EventBuilder(EVENT_5).withLocation("Test Location 3").withDate("2018-09-18").build();
        assertTrue(uniqueEventList.hasClashAfterEdit(EVENT_5, editedEvent, VALID_EMAIL_ALICE));
    }

    @Test
    public void hasClashAfterEdit_doesNotClashWithEventInList_returnsFalse() {
        uniqueEventList.add(EVENT_3);
        uniqueEventList.add(EVENT_5);
        Event editedEvent = new EventBuilder(EVENT_5).withLocation("Test Location 3").build();
        assertFalse(uniqueEventList.hasClashAfterEdit(EVENT_5, editedEvent, VALID_EMAIL_ALICE));
    }
}
