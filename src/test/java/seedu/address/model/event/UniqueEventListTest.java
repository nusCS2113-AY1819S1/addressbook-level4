package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_BOB;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.testutil.TypicalEvents.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

import seedu.address.testutil.EventBuilder;

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
        assertFalse(uniqueEventList.contains(ALICE));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(ALICE);
        assertTrue(uniqueEventList.contains(ALICE));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEventList.add(ALICE);
        Event editedAlice = new EventBuilder(ALICE).withVenue(VALID_VENUE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueEventList.contains(editedAlice));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.add(null);
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(ALICE);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.add(ALICE);
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvent(null, ALICE);
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvent(ALICE, null);
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.setEvent(ALICE, ALICE);
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        uniqueEventList.add(ALICE);
        uniqueEventList.setEvent(ALICE, ALICE);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(ALICE);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasSameIdentity_success() {
        uniqueEventList.add(ALICE);
        Event editedAlice = new EventBuilder(ALICE).withVenue(VALID_VENUE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueEventList.setEvent(ALICE, editedAlice);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedAlice);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(ALICE);
        uniqueEventList.setEvent(ALICE, BOB);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BOB);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicateEventException() {
        uniqueEventList.add(ALICE);
        uniqueEventList.add(BOB);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.setEvent(ALICE, BOB);
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.remove(null);
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.remove(ALICE);
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(ALICE);
        uniqueEventList.remove(ALICE);
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
        uniqueEventList.add(ALICE);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BOB);
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
        uniqueEventList.add(ALICE);
        List<Event> eventList = Collections.singletonList(BOB);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BOB);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.setEvents(listWithDuplicateEvents);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asUnmodifiableObservableList().remove(0);
    }
}
