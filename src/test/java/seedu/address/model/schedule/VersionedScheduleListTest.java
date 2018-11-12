package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.schedule.TypicalSchedules.ALICE_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.BENSON_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.CARL_WORK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.schedule.ScheduleListBuilder;

public class VersionedScheduleListTest {

    private final ReadOnlyScheduleList scheduleListWithAlice =
            new ScheduleListBuilder().withSchedule(ALICE_WORK).build();
    private final ReadOnlyScheduleList scheduleListWithBenson =
            new ScheduleListBuilder().withSchedule(BENSON_WORK).build();
    private final ReadOnlyScheduleList scheduleListWithCarl =
            new ScheduleListBuilder().withSchedule(CARL_WORK).build();
    private final ReadOnlyScheduleList emptyScheduleList = new ScheduleListBuilder().build();

    @Test
    public void commit_singleScheduleList_noStatesRemovedCurrentStateSaved() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(emptyScheduleList);

        versionedScheduleList.commit();
        assertScheduleListListStatus(versionedScheduleList,
                Collections.singletonList(emptyScheduleList),
                emptyScheduleList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleScheduleListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);

        versionedScheduleList.commit();
        assertScheduleListListStatus(versionedScheduleList,
                Arrays.asList(emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson),
                scheduleListWithBenson,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplScheduleListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 2);

        versionedScheduleList.commit();
        assertScheduleListListStatus(versionedScheduleList,
                Collections.singletonList(emptyScheduleList),
                emptyScheduleList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleScheduleListPointerAtEndOfStateList_returnsTrue() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);

        assertTrue(versionedScheduleList.canUndo());
    }

    @Test
    public void canUndo_multipleScheduleListPointerAtStartOfStateList_returnsTrue() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 1);

        assertTrue(versionedScheduleList.canUndo());
    }

    @Test
    public void canUndo_singleScheduleList_returnsFalse() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(emptyScheduleList);

        assertFalse(versionedScheduleList.canUndo());
    }

    @Test
    public void canUndo_multipleScheduleListPointerAtStartOfStateList_returnsFalse() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 2);

        assertFalse(versionedScheduleList.canUndo());
    }

    @Test
    public void canRedo_multipleScheduleListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 1);

        assertTrue(versionedScheduleList.canRedo());
    }

    @Test
    public void canRedo_multipleScheduleListPointerAtStartOfStateList_returnsTrue() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 2);

        assertTrue(versionedScheduleList.canRedo());
    }

    @Test
    public void canRedo_singleScheduleList_returnsFalse() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(emptyScheduleList);

        assertFalse(versionedScheduleList.canRedo());
    }

    @Test
    public void canRedo_multipleScheduleListPointerAtEndOfStateList_returnsFalse() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);

        assertFalse(versionedScheduleList.canRedo());
    }

    @Test
    public void undo_multipleScheduleListPointerAtEndOfStateList_success() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);

        versionedScheduleList.undo();
        assertScheduleListListStatus(versionedScheduleList,
                Collections.singletonList(emptyScheduleList),
                scheduleListWithAlice,
                Collections.singletonList(scheduleListWithBenson));
    }

    @Test
    public void undo_multipleScheduleListPointerNotAtStartOfStateList_success() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 1);

        versionedScheduleList.undo();
        assertScheduleListListStatus(versionedScheduleList,
                Collections.emptyList(),
                emptyScheduleList,
                Arrays.asList(scheduleListWithAlice, scheduleListWithBenson));
    }

    @Test
    public void undo_singleScheduleList_throwsNoUndoableStateException() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(emptyScheduleList);

        assertThrows(VersionedScheduleList.NoUndoableStateException.class, versionedScheduleList::undo);
    }

    @Test
    public void undo_multipleScheduleListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 2);

        assertThrows(VersionedScheduleList.NoUndoableStateException.class, versionedScheduleList::undo);
    }

    @Test
    public void redo_multipleScheduleListPointerNotAtEndOfStateList_success() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 1);

        versionedScheduleList.redo();
        assertScheduleListListStatus(versionedScheduleList,
                Arrays.asList(emptyScheduleList, scheduleListWithAlice),
                scheduleListWithBenson,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleScheduleListPointerAtStartOfStateList_success() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 2);

        versionedScheduleList.redo();
        assertScheduleListListStatus(versionedScheduleList,
                Collections.singletonList(emptyScheduleList),
                scheduleListWithAlice,
                Collections.singletonList(scheduleListWithBenson));
    }

    @Test
    public void redo_singleScheduleList_throwsNoRedoableStateException() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(emptyScheduleList);
        assertThrows(VersionedScheduleList.NoRedoableStateException.class, versionedScheduleList::redo);
    }

    @Test
    public void redo_multipleScheduleListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);

        assertThrows(VersionedScheduleList.NoRedoableStateException.class, versionedScheduleList::redo);
    }

    @Test
    public void equals() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(scheduleListWithAlice,
                scheduleListWithBenson);

        // same values -> returns true
        VersionedScheduleList copy = prepareScheduleList(scheduleListWithAlice, scheduleListWithBenson);
        assertTrue(versionedScheduleList.equals(copy));

        // same object -> returns true
        assertTrue(versionedScheduleList.equals(versionedScheduleList));

        // null -> returns false
        assertFalse(versionedScheduleList.equals(null));

        // different types -> returns false
        assertFalse(versionedScheduleList.equals(1));

        // different state list -> returns false
        VersionedScheduleList differentScheduleList = prepareScheduleList(scheduleListWithBenson,
                scheduleListWithCarl);
        assertFalse(versionedScheduleList.equals(differentScheduleList));

        // different current pointer index -> returns false
        VersionedScheduleList differentCurrentStatePointer = prepareScheduleList(
                scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 1);
        assertFalse(versionedScheduleList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedScheduleList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedScheduleList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedScheduleList#currentStatePointer} is equal to
     * {@code expectedStatesAfterPointer}.
     */
    private void assertScheduleListListStatus(VersionedScheduleList versionedScheduleList,
                                             List<ReadOnlyScheduleList> expectedStatesBeforePointer,
                                             ReadOnlyScheduleList expectedCurrentState,
                                             List<ReadOnlyScheduleList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ScheduleList(versionedScheduleList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedScheduleList.canUndo()) {
            versionedScheduleList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyScheduleList expectedScheduleList : expectedStatesBeforePointer) {
            assertEquals(expectedScheduleList, new ScheduleList(versionedScheduleList));
            versionedScheduleList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyScheduleList expectedScheduleList : expectedStatesAfterPointer) {
            versionedScheduleList.redo();
            assertEquals(expectedScheduleList, new ScheduleList(versionedScheduleList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedScheduleList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedScheduleList.undo());
    }

    /**
     * Creates and returns a {@code versionedScheduleList} with the {@code scheduleListStates} added into it, and the
     * {@code versionedScheduleList#currentStatePointer} at the end of list.
     */
    private VersionedScheduleList prepareScheduleList(ReadOnlyScheduleList... scheduleListStates) {
        assertFalse(scheduleListStates.length == 0);

        VersionedScheduleList versionedScheduleList = new VersionedScheduleList(scheduleListStates[0]);
        for (int i = 1; i < scheduleListStates.length; i++) {
            versionedScheduleList.resetData(scheduleListStates[i]);
            versionedScheduleList.commit();
        }

        return versionedScheduleList;
    }

    /**
     * Shifts the {@code versionedScheduleList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedScheduleList versionedScheduleList, int count) {
        for (int i = 0; i < count; i++) {
            versionedScheduleList.undo();
        }
    }

}
