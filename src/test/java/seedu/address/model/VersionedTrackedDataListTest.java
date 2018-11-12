package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalParameters.DURATION_PARAMETER;
import static seedu.address.testutil.TypicalParameters.NAME_PARAMETER;
import static seedu.address.testutil.TypicalParameters.TYPE_PARAMETER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TrackedDataListBuilder;

public class VersionedTrackedDataListTest {

    private final ReadOnlyTrackedDataList trackedDataListWithName = new TrackedDataListBuilder()
            .withParameter(NAME_PARAMETER).build();
    private final ReadOnlyTrackedDataList trackedDataListWithType = new TrackedDataListBuilder()
            .withParameter(TYPE_PARAMETER).build();
    private final ReadOnlyTrackedDataList trackedDataListWithDuration = new TrackedDataListBuilder()
            .withParameter(DURATION_PARAMETER).build();
    private final ReadOnlyTrackedDataList emptyTrackedDataList = new TrackedDataListBuilder().build();

    @Test
    public void commit_singleTrackedDataList_noStatesRemovedCurrentStateSaved() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(emptyTrackedDataList);

        versionedTrackedDataList.commit();
        assertTrackedDataListListStatus(versionedTrackedDataList,
                Collections.singletonList(emptyTrackedDataList),
                emptyTrackedDataList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTrackedDataListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);

        versionedTrackedDataList.commit();
        assertTrackedDataListListStatus(versionedTrackedDataList,
                Arrays.asList(emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType),
                trackedDataListWithType,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTrackedDataListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);
        shiftCurrentStatePointerLeftwards(versionedTrackedDataList, 2);

        versionedTrackedDataList.commit();
        assertTrackedDataListListStatus(versionedTrackedDataList,
                Collections.singletonList(emptyTrackedDataList),
                emptyTrackedDataList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleTrackedDataListPointerAtEndOfStateList_returnsTrue() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);

        assertTrue(versionedTrackedDataList.canUndo());
    }

    @Test
    public void canUndo_multipleTrackedDataListPointerAtStartOfStateList_returnsTrue() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);
        shiftCurrentStatePointerLeftwards(versionedTrackedDataList, 1);

        assertTrue(versionedTrackedDataList.canUndo());
    }

    @Test
    public void canUndo_singleTrackedDataList_returnsFalse() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList);

        assertFalse(versionedTrackedDataList.canUndo());
    }

    @Test
    public void canUndo_multipleTrackedDataListPointerAtStartOfStateList_returnsFalse() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);
        shiftCurrentStatePointerLeftwards(versionedTrackedDataList, 2);

        assertFalse(versionedTrackedDataList.canUndo());
    }

    @Test
    public void canRedo_multipleTrackedDataListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);
        shiftCurrentStatePointerLeftwards(versionedTrackedDataList, 1);

        assertTrue(versionedTrackedDataList.canRedo());
    }

    @Test
    public void canRedo_multipleTrackedDataListPointerAtStartOfStateList_returnsTrue() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);
        shiftCurrentStatePointerLeftwards(versionedTrackedDataList, 2);

        assertTrue(versionedTrackedDataList.canRedo());
    }

    @Test
    public void canRedo_singleTrackedDataList_returnsFalse() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList);

        assertFalse(versionedTrackedDataList.canRedo());
    }

    @Test
    public void canRedo_multipleTrackedDataListPointerAtEndOfStateList_returnsFalse() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);

        assertFalse(versionedTrackedDataList.canRedo());
    }

    @Test
    public void undo_multipleTrackedDataListPointerAtEndOfStateList_success() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);

        versionedTrackedDataList.undo();
        assertTrackedDataListListStatus(versionedTrackedDataList,
                Collections.singletonList(emptyTrackedDataList),
                trackedDataListWithName,
                Collections.singletonList(trackedDataListWithType));
    }

    @Test
    public void undo_multipleTrackedDataListPointerNotAtStartOfStateList_success() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);
        shiftCurrentStatePointerLeftwards(versionedTrackedDataList, 1);

        versionedTrackedDataList.undo();
        assertTrackedDataListListStatus(versionedTrackedDataList,
                Collections.emptyList(),
                emptyTrackedDataList,
                Arrays.asList(trackedDataListWithName, trackedDataListWithType));
    }

    @Test
    public void undo_singleTrackedDataList_throwsNoUndoableStateException() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList);

        assertThrows(VersionedTrackedDataList.NoUndoableStateException.class, versionedTrackedDataList::undo);
    }

    @Test
    public void undo_multipleTrackedDataListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);
        shiftCurrentStatePointerLeftwards(versionedTrackedDataList, 2);

        assertThrows(VersionedTrackedDataList.NoUndoableStateException.class, versionedTrackedDataList::undo);
    }

    @Test
    public void redo_multipleTrackedDataListPointerNotAtEndOfStateList_success() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);
        shiftCurrentStatePointerLeftwards(versionedTrackedDataList, 1);

        versionedTrackedDataList.redo();
        assertTrackedDataListListStatus(versionedTrackedDataList,
                Arrays.asList(emptyTrackedDataList, trackedDataListWithName),
                trackedDataListWithType,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleTrackedDataListPointerAtStartOfStateList_success() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);
        shiftCurrentStatePointerLeftwards(versionedTrackedDataList, 2);

        versionedTrackedDataList.redo();
        assertTrackedDataListListStatus(versionedTrackedDataList,
                Collections.singletonList(emptyTrackedDataList),
                trackedDataListWithName,
                Collections.singletonList(trackedDataListWithType));
    }

    @Test
    public void redo_singleTrackedDataList_throwsNoRedoableStateException() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList);

        assertThrows(VersionedTrackedDataList.NoRedoableStateException.class, versionedTrackedDataList::redo);
    }

    @Test
    public void redo_multipleTrackedDataListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(
                emptyTrackedDataList, trackedDataListWithName, trackedDataListWithType);

        assertThrows(VersionedTrackedDataList.NoRedoableStateException.class, versionedTrackedDataList::redo);
    }

    @Test
    public void equals() {
        VersionedTrackedDataList versionedTrackedDataList = prepareTrackedDataListList(trackedDataListWithName,
                trackedDataListWithType);

        // same values -> returns true
        VersionedTrackedDataList copy = prepareTrackedDataListList(trackedDataListWithName, trackedDataListWithType);
        assertTrue(versionedTrackedDataList.equals(copy));

        // same object -> returns true
        assertTrue(versionedTrackedDataList.equals(versionedTrackedDataList));

        // null -> returns false
        assertFalse(versionedTrackedDataList.equals(null));

        // different types -> returns false
        assertFalse(versionedTrackedDataList.equals(1));

        // different state list -> returns false
        VersionedTrackedDataList differentWorkoutBookList = prepareTrackedDataListList(trackedDataListWithType,
                trackedDataListWithDuration);
        assertFalse(versionedTrackedDataList.equals(differentWorkoutBookList));

        // different current pointer index -> returns false
        VersionedTrackedDataList differentCurrentStatePointer = prepareTrackedDataListList(
                trackedDataListWithName, trackedDataListWithType);
        shiftCurrentStatePointerLeftwards(versionedTrackedDataList, 1);
        assertFalse(versionedTrackedDataList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedTrackedDataList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTrackedDataList#currentStatePointer} is equal to
     * {@code expectedStatesBeforePointer},
     * and states after {@code versionedTrackedDataList#currentStatePointer} is equal to
     * {@code expectedStatesAfterPointer}.
     */
    private void assertTrackedDataListListStatus(VersionedTrackedDataList versionedTrackedDataList,
                                             List<ReadOnlyTrackedDataList> expectedStatesBeforePointer,
                                             ReadOnlyTrackedDataList expectedCurrentState,
                                             List<ReadOnlyTrackedDataList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new TrackedDataList(versionedTrackedDataList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTrackedDataList.canUndo()) {
            versionedTrackedDataList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTrackedDataList expectedTrackedDataList : expectedStatesBeforePointer) {
            assertEquals(expectedTrackedDataList, new TrackedDataList(versionedTrackedDataList));
            versionedTrackedDataList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTrackedDataList expectedTrackedDataList : expectedStatesAfterPointer) {
            versionedTrackedDataList.redo();
            assertEquals(expectedTrackedDataList, new TrackedDataList(versionedTrackedDataList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTrackedDataList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTrackedDataList.undo());
    }

    /**
     * Creates and returns a {@code VersionedTrackedDataList} with the {@code trackedDataListStates} added into it,
     * and the {@code VersionedTrackedDataList#currentStatePointer} at the end of list.
     */
    private VersionedTrackedDataList prepareTrackedDataListList(ReadOnlyTrackedDataList... trackedDataListStates) {
        assertFalse(trackedDataListStates.length == 0);

        VersionedTrackedDataList versionedTrackedDataList = new VersionedTrackedDataList(trackedDataListStates[0]);
        for (int i = 1; i < trackedDataListStates.length; i++) {
            versionedTrackedDataList.resetData(trackedDataListStates[i]);
            versionedTrackedDataList.commit();
        }

        return versionedTrackedDataList;
    }

    /**
     * Shifts the {@code versionedTrackedDataList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTrackedDataList versionedTrackedDataList, int count) {
        for (int i = 0; i < count; i++) {
            versionedTrackedDataList.undo();
        }
    }
}
