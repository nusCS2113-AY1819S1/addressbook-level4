package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_2;
import static seedu.address.testutil.TypicalEvents.EVENT_3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.exceptions.NoRedoableStateException;
import seedu.address.model.exceptions.NoUndoableStateException;
import seedu.address.testutil.EventListBuilder;

public class VersionedEventListTest {

    private final ReadOnlyEventList eventListWithEventOne = new EventListBuilder().withEvent(EVENT_1).build();
    private final ReadOnlyEventList eventListWithEventTwo = new EventListBuilder().withEvent(EVENT_2).build();
    private final ReadOnlyEventList eventListWithEventThree = new EventListBuilder().withEvent(EVENT_3).build();
    private final ReadOnlyEventList emptyEventList = new EventListBuilder().build();

    @Test
    public void commit_singleEventList_noStatesRemovedCurrentStateSaved() {
        VersionedEventList versionedEventList = prepareEventListList(emptyEventList);

        versionedEventList.commit();
        assertEventListListStatus(versionedEventList,
                Collections.singletonList(emptyEventList),
                emptyEventList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleEventListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);

        versionedEventList.commit();
        assertEventListListStatus(versionedEventList,
                Arrays.asList(emptyEventList, eventListWithEventOne, eventListWithEventTwo),
                eventListWithEventTwo,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleEventListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);
        shiftCurrentStatePointerLeftwards(versionedEventList, 2);

        versionedEventList.commit();
        assertEventListListStatus(versionedEventList,
                Collections.singletonList(emptyEventList),
                emptyEventList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleEventListPointerAtEndOfStateList_returnsTrue() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);

        assertTrue(versionedEventList.canUndo());
    }

    @Test
    public void canUndo_multipleEventListPointerAtStartOfStateList_returnsTrue() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);
        shiftCurrentStatePointerLeftwards(versionedEventList, 1);

        assertTrue(versionedEventList.canUndo());
    }

    @Test
    public void canUndo_singleEventList_returnsFalse() {
        VersionedEventList versionedEventList = prepareEventListList(emptyEventList);

        assertFalse(versionedEventList.canUndo());
    }

    @Test
    public void canUndo_multipleEventListPointerAtStartOfStateList_returnsFalse() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);
        shiftCurrentStatePointerLeftwards(versionedEventList, 2);

        assertFalse(versionedEventList.canUndo());
    }

    @Test
    public void canRedo_multipleEventListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);
        shiftCurrentStatePointerLeftwards(versionedEventList, 1);

        assertTrue(versionedEventList.canRedo());
    }

    @Test
    public void canRedo_multipleEventListPointerAtStartOfStateList_returnsTrue() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);
        shiftCurrentStatePointerLeftwards(versionedEventList, 2);

        assertTrue(versionedEventList.canRedo());
    }

    @Test
    public void canRedo_singleEventList_returnsFalse() {
        VersionedEventList versionedEventList = prepareEventListList(emptyEventList);

        assertFalse(versionedEventList.canRedo());
    }

    @Test
    public void canRedo_multipleEventListPointerAtEndOfStateList_returnsFalse() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);

        assertFalse(versionedEventList.canRedo());
    }

    @Test
    public void undo_multipleEventListPointerAtEndOfStateList_success() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);

        versionedEventList.undo();
        assertEventListListStatus(versionedEventList,
                Collections.singletonList(emptyEventList),
                eventListWithEventOne,
                Collections.singletonList(eventListWithEventTwo));
    }

    @Test
    public void undo_multipleEventListPointerNotAtStartOfStateList_success() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);
        shiftCurrentStatePointerLeftwards(versionedEventList, 1);

        versionedEventList.undo();
        assertEventListListStatus(versionedEventList,
                Collections.emptyList(),
                emptyEventList,
                Arrays.asList(eventListWithEventOne, eventListWithEventTwo));
    }

    @Test
    public void undo_singleEventList_throwsNoUndoableStateException() {
        VersionedEventList versionedEventList = prepareEventListList(emptyEventList);

        assertThrows(NoUndoableStateException.class, versionedEventList::undo);
    }

    @Test
    public void undo_multipleEventListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);
        shiftCurrentStatePointerLeftwards(versionedEventList, 2);

        assertThrows(NoUndoableStateException.class, versionedEventList::undo);
    }

    @Test
    public void redo_multipleEventListPointerNotAtEndOfStateList_success() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);
        shiftCurrentStatePointerLeftwards(versionedEventList, 1);

        versionedEventList.redo();
        assertEventListListStatus(versionedEventList,
                Arrays.asList(emptyEventList, eventListWithEventOne),
                eventListWithEventTwo,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleEventListPointerAtStartOfStateList_success() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);
        shiftCurrentStatePointerLeftwards(versionedEventList, 2);

        versionedEventList.redo();
        assertEventListListStatus(versionedEventList,
                Collections.singletonList(emptyEventList),
                eventListWithEventOne,
                Collections.singletonList(eventListWithEventTwo));
    }

    @Test
    public void redo_singleEventList_throwsNoRedoableStateException() {
        VersionedEventList versionedEventList = prepareEventListList(emptyEventList);

        assertThrows(NoRedoableStateException.class, versionedEventList::redo);
    }

    @Test
    public void redo_multipleEventListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedEventList versionedEventList = prepareEventListList(
                emptyEventList, eventListWithEventOne, eventListWithEventTwo);

        assertThrows(NoRedoableStateException.class, versionedEventList::redo);
    }

    @Test
    public void equals() {
        VersionedEventList versionedEventList = prepareEventListList(eventListWithEventOne, eventListWithEventTwo);

        // same values -> returns true
        VersionedEventList copy = prepareEventListList(eventListWithEventOne, eventListWithEventTwo);
        assertTrue(versionedEventList.equals(copy));

        // same object -> returns true
        assertTrue(versionedEventList.equals(versionedEventList));

        // null -> returns false
        assertFalse(versionedEventList.equals(null));

        // different types -> returns false
        assertFalse(versionedEventList.equals(1));

        // different state list -> returns false
        VersionedEventList differentEventListList = prepareEventListList(eventListWithEventTwo, eventListWithEventThree);
        assertFalse(versionedEventList.equals(differentEventListList));

        // different current pointer index -> returns false
        VersionedEventList differentCurrentStatePointer = prepareEventListList(
                eventListWithEventOne, eventListWithEventTwo);
        shiftCurrentStatePointerLeftwards(versionedEventList, 1);
        assertFalse(versionedEventList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedEventList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedEventList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedEventList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertEventListListStatus(VersionedEventList versionedEventList,
                                             List<ReadOnlyEventList> expectedStatesBeforePointer,
                                             ReadOnlyEventList expectedCurrentState,
                                             List<ReadOnlyEventList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new EventList(versionedEventList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedEventList.canUndo()) {
            versionedEventList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyEventList expectedEventList : expectedStatesBeforePointer) {
            assertEquals(expectedEventList, new EventList(versionedEventList));
            versionedEventList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyEventList expectedEventList : expectedStatesAfterPointer) {
            versionedEventList.redo();
            assertEquals(expectedEventList, new EventList(versionedEventList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedEventList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedEventList.undo());
    }

    /**
     * Creates and returns a {@code VersionedEventList} with the {@code eventListStates} added into it, and the
     * {@code VersionedEventList#currentStatePointer} at the end of list.
     */
    private VersionedEventList prepareEventListList(ReadOnlyEventList... eventListStates) {
        assertFalse(eventListStates.length == 0);

        VersionedEventList versionedEventList = new VersionedEventList(eventListStates[0]);
        for (int i = 1; i < eventListStates.length; i++) {
            versionedEventList.resetData(eventListStates[i]);
            versionedEventList.commit();
        }

        return versionedEventList;
    }

    /**
     * Shifts the {@code versionedEventList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedEventList versionedEventList, int count) {
        for (int i = 0; i < count; i++) {
            versionedEventList.undo();
        }
    }
}
