package seedu.divelog.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.divelog.testutil.TypicalDiveSessions.DIVE_AT_BALI;
import static seedu.divelog.testutil.TypicalDiveSessions.DIVE_AT_NIGHT;
import static seedu.divelog.testutil.TypicalDiveSessions.DIVE_AT_TIOMAN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.divelog.testutil.DiveLogBuilder;

public class VersionedDiveLogTest {

    private final ReadOnlyDiveLog diveLogWithBali = new DiveLogBuilder()
            .withDive(DIVE_AT_BALI)
            .build();
    private final ReadOnlyDiveLog diveLogWithTioman = new DiveLogBuilder()
            .withDive(DIVE_AT_TIOMAN)
            .build();
    private final ReadOnlyDiveLog diveLogWithCarl = new DiveLogBuilder()
            .withDive(DIVE_AT_NIGHT)
            .build();
    private final ReadOnlyDiveLog emptyDiveLog = new DiveLogBuilder().build();

    @Test
    public void commit_singleDiveLog_noStatesRemovedCurrentStateSaved() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(emptyDiveLog);

        versionedDiveLog.commit();
        assertDiveLogListStatus(versionedDiveLog,
                Collections.singletonList(emptyDiveLog),
                emptyDiveLog,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleDiveLogPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);

        versionedDiveLog.commit();
        assertDiveLogListStatus(versionedDiveLog,
                Arrays.asList(emptyDiveLog, diveLogWithBali, diveLogWithTioman),
                diveLogWithTioman,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleDiveLogPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);
        shiftCurrentStatePointerLeftwards(versionedDiveLog, 2);

        versionedDiveLog.commit();
        assertDiveLogListStatus(versionedDiveLog,
                Collections.singletonList(emptyDiveLog),
                emptyDiveLog,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleDiveLogPointerAtEndOfStateList_returnsTrue() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);

        assertTrue(versionedDiveLog.canUndo());
    }

    @Test
    public void canUndo_multipleDiveLogPointerAtStartOfStateList_returnsTrue() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);
        shiftCurrentStatePointerLeftwards(versionedDiveLog, 1);

        assertTrue(versionedDiveLog.canUndo());
    }

    @Test
    public void canUndo_singleDiveLog_returnsFalse() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(emptyDiveLog);

        assertFalse(versionedDiveLog.canUndo());
    }

    @Test
    public void canUndo_multipleDiveLogPointerAtStartOfStateList_returnsFalse() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);
        shiftCurrentStatePointerLeftwards(versionedDiveLog, 2);

        assertFalse(versionedDiveLog.canUndo());
    }

    @Test
    public void canRedo_multipleDiveLogPointerNotAtEndOfStateList_returnsTrue() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);
        shiftCurrentStatePointerLeftwards(versionedDiveLog, 1);

        assertTrue(versionedDiveLog.canRedo());
    }

    @Test
    public void canRedo_multipleDiveLogPointerAtStartOfStateList_returnsTrue() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);
        shiftCurrentStatePointerLeftwards(versionedDiveLog, 2);

        assertTrue(versionedDiveLog.canRedo());
    }

    @Test
    public void canRedo_singleDiveLog_returnsFalse() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(emptyDiveLog);

        assertFalse(versionedDiveLog.canRedo());
    }

    @Test
    public void canRedo_multipleDiveLogPointerAtEndOfStateList_returnsFalse() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);

        assertFalse(versionedDiveLog.canRedo());
    }

    @Test
    public void undo_multipleDiveLogPointerAtEndOfStateList_success() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);

        versionedDiveLog.undo();
        assertDiveLogListStatus(versionedDiveLog,
                Collections.singletonList(emptyDiveLog),
                diveLogWithBali,
                Collections.singletonList(diveLogWithTioman));
    }

    @Test
    public void undo_multipleDiveLogPointerNotAtStartOfStateList_success() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);
        shiftCurrentStatePointerLeftwards(versionedDiveLog, 1);

        versionedDiveLog.undo();
        assertDiveLogListStatus(versionedDiveLog,
                Collections.emptyList(),
                emptyDiveLog,
                Arrays.asList(diveLogWithBali, diveLogWithTioman));
    }

    @Test
    public void undo_singleDiveLog_throwsNoUndoableStateException() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(emptyDiveLog);

        assertThrows(VersionedDiveLog.NoUndoableStateException.class, versionedDiveLog::undo);
    }

    @Test
    public void undo_multipleDiveLogPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);
        shiftCurrentStatePointerLeftwards(versionedDiveLog, 2);

        assertThrows(VersionedDiveLog.NoUndoableStateException.class, versionedDiveLog::undo);
    }

    @Test
    public void redo_multipleDiveLogPointerNotAtEndOfStateList_success() {
        VersionedDiveLog versionversionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);
        shiftCurrentStatePointerLeftwards(versionversionedDiveLog, 1);

        versionversionedDiveLog.redo();
        assertDiveLogListStatus(versionversionedDiveLog,
                Arrays.asList(emptyDiveLog, diveLogWithBali),
                diveLogWithTioman,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleDiveLogPointerAtStartOfStateList_success() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);
        shiftCurrentStatePointerLeftwards(versionedDiveLog, 2);

        versionedDiveLog.redo();
        assertDiveLogListStatus(versionedDiveLog,
                Collections.singletonList(emptyDiveLog),
                diveLogWithBali,
                Collections.singletonList(diveLogWithTioman));
    }

    @Test
    public void redo_singleDiveLog_throwsNoRedoableStateException() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(emptyDiveLog);

        assertThrows(VersionedDiveLog.NoRedoableStateException.class, versionedDiveLog::redo);
    }

    @Test
    public void redo_multipleDiveLogPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(
                emptyDiveLog, diveLogWithBali, diveLogWithTioman);

        assertThrows(VersionedDiveLog.NoRedoableStateException.class, versionedDiveLog::redo);
    }

    @Test
    public void equals() {
        VersionedDiveLog versionedDiveLog = prepareDiveLogList(diveLogWithBali, diveLogWithTioman);

        // same values -> returns true
        VersionedDiveLog copy = prepareDiveLogList(diveLogWithBali, diveLogWithTioman);
        assertTrue(versionedDiveLog.equals(copy));

        // same object -> returns true
        assertTrue(versionedDiveLog.equals(versionedDiveLog));

        // null -> returns false
        assertFalse(versionedDiveLog.equals(null));

        // different types -> returns false
        assertFalse(versionedDiveLog.equals(1));

        // different state list -> returns false
        VersionedDiveLog differentDiveLogList = prepareDiveLogList(diveLogWithTioman, diveLogWithCarl);
        assertFalse(versionedDiveLog.equals(differentDiveLogList));

        // different current pointer index -> returns false
        VersionedDiveLog differentCurrentStatePointer = prepareDiveLogList(
                diveLogWithBali, diveLogWithTioman);
        shiftCurrentStatePointerLeftwards(versionedDiveLog, 1);
        assertFalse(versionedDiveLog.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedDiveLog} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedDiveLog#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedDiveLog#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertDiveLogListStatus(VersionedDiveLog versionedDiveLog,
                                         List<ReadOnlyDiveLog> expectedStatesBeforePointer,
                                         ReadOnlyDiveLog expectedCurrentState,
                                         List<ReadOnlyDiveLog> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new DiveLog(versionedDiveLog), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedDiveLog.canUndo()) {
            versionedDiveLog.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyDiveLog expectedDiveLog : expectedStatesBeforePointer) {
            assertEquals(expectedDiveLog, new DiveLog(versionedDiveLog));
            versionedDiveLog.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyDiveLog expectedDiveLog : expectedStatesAfterPointer) {
            versionedDiveLog.redo();
            assertEquals(expectedDiveLog, new DiveLog(versionedDiveLog));
        }

        // check that there are no more states after pointer
        assertFalse(versionedDiveLog.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedDiveLog.undo());
    }

    /**
     * Creates and returns a {@code VersionedDiveLog} with the {@code diveLogStates} added into it, and the
     * {@code VersionedDiveLog#currentStatePointer} at the end of list.
     * @param diveLogStates
     */
    private VersionedDiveLog prepareDiveLogList(ReadOnlyDiveLog... diveLogStates) {
        assertFalse(diveLogStates.length == 0);

        VersionedDiveLog versionedDiveLog = new VersionedDiveLog(diveLogStates[0]);
        for (int i = 1; i < diveLogStates.length; i++) {
            versionedDiveLog.resetData(diveLogStates[i]);
            versionedDiveLog.commit();
        }

        return versionedDiveLog;
    }

    /**
     * Shifts the {@code versionedDiveLog#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedDiveLog versionedDiveLog, int count) {
        for (int i = 0; i < count; i++) {
            versionedDiveLog.undo();
        }
    }
}
