package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalWorkouts.AMY_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.BOB_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.CARL_WORKOUT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TrackedDataBuilder;

public class VersionedTrackedDataTest {

    private final ReadOnlyTrackedData trackedDataWithAmy = new TrackedDataBuilder().withWorkout(AMY_WORKOUT).build();
    private final ReadOnlyTrackedData trackedDataWithBob = new TrackedDataBuilder().withWorkout(BOB_WORKOUT).build();
    private final ReadOnlyTrackedData trackedDataWithCarl = new TrackedDataBuilder().withWorkout(CARL_WORKOUT).build();
    private final ReadOnlyTrackedData emptyTrackedData = new TrackedDataBuilder().build();

    @Test
    public void commit_singleTrackedData_noStatesRemovedCurrentStateSaved() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(emptyTrackedData);

        versionedTrackedData.commit();
        assertTrackedDataListStatus(versionedTrackedData,
                Collections.singletonList(emptyTrackedData),
                emptyTrackedData,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTrackedDataPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);

        versionedTrackedData.commit();
        assertTrackedDataListStatus(versionedTrackedData,
                Arrays.asList(emptyTrackedData, trackedDataWithAmy, trackedDataWithBob),
                trackedDataWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTrackedDataPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);
        shiftCurrentStatePointerLeftwards(versionedTrackedData, 2);

        versionedTrackedData.commit();
        assertTrackedDataListStatus(versionedTrackedData,
                Collections.singletonList(emptyTrackedData),
                emptyTrackedData,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleTrackedDataPointerAtEndOfStateList_returnsTrue() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);

        assertTrue(versionedTrackedData.canUndo());
    }

    @Test
    public void canUndo_multipleTrackedDataPointerAtStartOfStateList_returnsTrue() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);
        shiftCurrentStatePointerLeftwards(versionedTrackedData, 1);

        assertTrue(versionedTrackedData.canUndo());
    }

    @Test
    public void canUndo_singleTrackedData_returnsFalse() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(emptyTrackedData);

        assertFalse(versionedTrackedData.canUndo());
    }

    @Test
    public void canUndo_multipleTrackedDataPointerAtStartOfStateList_returnsFalse() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);
        shiftCurrentStatePointerLeftwards(versionedTrackedData, 2);

        assertFalse(versionedTrackedData.canUndo());
    }

    @Test
    public void canRedo_multipleTrackedDataPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);
        shiftCurrentStatePointerLeftwards(versionedTrackedData, 1);

        assertTrue(versionedTrackedData.canRedo());
    }

    @Test
    public void canRedo_multipleTrackedDataPointerAtStartOfStateList_returnsTrue() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);
        shiftCurrentStatePointerLeftwards(versionedTrackedData, 2);

        assertTrue(versionedTrackedData.canRedo());
    }

    @Test
    public void canRedo_singleTrackedData_returnsFalse() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(emptyTrackedData);

        assertFalse(versionedTrackedData.canRedo());
    }

    @Test
    public void canRedo_multipleTrackedDataPointerAtEndOfStateList_returnsFalse() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);

        assertFalse(versionedTrackedData.canRedo());
    }

    @Test
    public void undo_multipleTrackedDataPointerAtEndOfStateList_success() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);

        versionedTrackedData.undo();
        assertTrackedDataListStatus(versionedTrackedData,
                Collections.singletonList(emptyTrackedData),
                trackedDataWithAmy,
                Collections.singletonList(trackedDataWithBob));
    }

    @Test
    public void undo_multipleTrackedDataPointerNotAtStartOfStateList_success() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);
        shiftCurrentStatePointerLeftwards(versionedTrackedData, 1);

        versionedTrackedData.undo();
        assertTrackedDataListStatus(versionedTrackedData,
                Collections.emptyList(),
                emptyTrackedData,
                Arrays.asList(trackedDataWithAmy, trackedDataWithBob));
    }

    @Test
    public void undo_singleTrackedData_throwsNoUndoableStateException() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(emptyTrackedData);

        assertThrows(VersionedTrackedData.NoUndoableStateException.class, versionedTrackedData::undo);
    }

    @Test
    public void undo_multipleTrackedDataPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);
        shiftCurrentStatePointerLeftwards(versionedTrackedData, 2);

        assertThrows(VersionedTrackedData.NoUndoableStateException.class, versionedTrackedData::undo);
    }

    @Test
    public void redo_multipleTrackedDataPointerNotAtEndOfStateList_success() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);
        shiftCurrentStatePointerLeftwards(versionedTrackedData, 1);

        versionedTrackedData.redo();
        assertTrackedDataListStatus(versionedTrackedData,
                Arrays.asList(emptyTrackedData, trackedDataWithAmy),
                trackedDataWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleTrackedDataPointerAtStartOfStateList_success() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);
        shiftCurrentStatePointerLeftwards(versionedTrackedData, 2);

        versionedTrackedData.redo();
        assertTrackedDataListStatus(versionedTrackedData,
                Collections.singletonList(emptyTrackedData),
                trackedDataWithAmy,
                Collections.singletonList(trackedDataWithBob));
    }

    @Test
    public void redo_singleTrackedData_throwsNoRedoableStateException() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(emptyTrackedData);

        assertThrows(VersionedTrackedData.NoRedoableStateException.class, versionedTrackedData::redo);
    }

    @Test
    public void redo_multipleTrackedDataPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(
                emptyTrackedData, trackedDataWithAmy, trackedDataWithBob);

        assertThrows(VersionedTrackedData.NoRedoableStateException.class, versionedTrackedData::redo);
    }

    @Test
    public void equals() {
        VersionedTrackedData versionedTrackedData = prepareTrackedDataList(trackedDataWithAmy, trackedDataWithBob);

        // same values -> returns true
        VersionedTrackedData copy = prepareTrackedDataList(trackedDataWithAmy, trackedDataWithBob);
        assertTrue(versionedTrackedData.equals(copy));

        // same object -> returns true
        assertTrue(versionedTrackedData.equals(versionedTrackedData));

        // null -> returns false
        assertFalse(versionedTrackedData.equals(null));

        // different types -> returns false
        assertFalse(versionedTrackedData.equals(1));

        // different state list -> returns false
        VersionedTrackedData differentTrackedDataList = prepareTrackedDataList(trackedDataWithBob, trackedDataWithCarl);
        assertFalse(versionedTrackedData.equals(differentTrackedDataList));

        // different current pointer index -> returns false
        VersionedTrackedData differentCurrentStatePointer = prepareTrackedDataList(
                trackedDataWithAmy, trackedDataWithBob);
        shiftCurrentStatePointerLeftwards(versionedTrackedData, 1);
        assertFalse(versionedTrackedData.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedTrackedData} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTrackedData#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedTrackedData#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertTrackedDataListStatus(VersionedTrackedData versionedTrackedData,
                                             List<ReadOnlyTrackedData> expectedStatesBeforePointer,
                                             ReadOnlyTrackedData expectedCurrentState,
                                             List<ReadOnlyTrackedData> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new TrackedData(versionedTrackedData), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTrackedData.canUndo()) {
            versionedTrackedData.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTrackedData expectedTrackedData : expectedStatesBeforePointer) {
            assertEquals(expectedTrackedData, new TrackedData(versionedTrackedData));
            versionedTrackedData.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTrackedData expectedTrackedData : expectedStatesAfterPointer) {
            versionedTrackedData.redo();
            assertEquals(expectedTrackedData, new TrackedData(versionedTrackedData));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTrackedData.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTrackedData.undo());
    }

    /**
     * Creates and returns a {@code VersionedTrackedData} with the {@code trackedDataStates} added into it, and the
     * {@code VersionedTrackedData#currentStatePointer} at the end of list.
     */
    private VersionedTrackedData prepareTrackedDataList(ReadOnlyTrackedData... trackedDataStates) {
        assertFalse(trackedDataStates.length == 0);

        VersionedTrackedData versionedTrackedData = new VersionedTrackedData(trackedDataStates[0]);
        for (int i = 1; i < trackedDataStates.length; i++) {
            versionedTrackedData.resetData(trackedDataStates[i]);
            versionedTrackedData.commit();
        }

        return versionedTrackedData;
    }

    /**
     * Shifts the {@code versionedTrackedData#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTrackedData versionedTrackedData, int count) {
        for (int i = 0; i < count; i++) {
            versionedTrackedData.undo();
        }
    }
}
