package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalEvents.AMY;
import static seedu.address.testutil.TypicalEvents.BOB;
import static seedu.address.testutil.TypicalEvents.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.EventManagerBuilder;

public class VersionedEventManagerTest {

    private final ReadOnlyEventManager eventManagerWithAmy = new EventManagerBuilder().withEvent(AMY).build();
    private final ReadOnlyEventManager eventManagerWithBob = new EventManagerBuilder().withEvent(BOB).build();
    private final ReadOnlyEventManager eventManagerWithCarl = new EventManagerBuilder().withEvent(CARL).build();
    private final ReadOnlyEventManager emptyEventManager = new EventManagerBuilder().build();

    @Test
    public void commit_singleEventManager_noStatesRemovedCurrentStateSaved() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(emptyEventManager);

        versionedEventManager.commit();
        assertEventManagerListStatus(versionedEventManager,
                Collections.singletonList(emptyEventManager),
                emptyEventManager,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleEventManagerPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);

        versionedEventManager.commit();
        assertEventManagerListStatus(versionedEventManager,
                Arrays.asList(emptyEventManager, eventManagerWithAmy, eventManagerWithBob),
                eventManagerWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleEventManagerPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEventManager, 2);

        versionedEventManager.commit();
        assertEventManagerListStatus(versionedEventManager,
                Collections.singletonList(emptyEventManager),
                emptyEventManager,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleEventManagerPointerAtEndOfStateList_returnsTrue() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);

        assertTrue(versionedEventManager.canUndo());
    }

    @Test
    public void canUndo_multipleEventManagerPointerAtStartOfStateList_returnsTrue() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEventManager, 1);

        assertTrue(versionedEventManager.canUndo());
    }

    @Test
    public void canUndo_singleEventManager_returnsFalse() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(emptyEventManager);

        assertFalse(versionedEventManager.canUndo());
    }

    @Test
    public void canUndo_multipleEventManagerPointerAtStartOfStateList_returnsFalse() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEventManager, 2);

        assertFalse(versionedEventManager.canUndo());
    }

    @Test
    public void canRedo_multipleEventManagerPointerNotAtEndOfStateList_returnsTrue() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEventManager, 1);

        assertTrue(versionedEventManager.canRedo());
    }

    @Test
    public void canRedo_multipleEventManagerPointerAtStartOfStateList_returnsTrue() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEventManager, 2);

        assertTrue(versionedEventManager.canRedo());
    }

    @Test
    public void canRedo_singleEventManager_returnsFalse() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(emptyEventManager);

        assertFalse(versionedEventManager.canRedo());
    }

    @Test
    public void canRedo_multipleEventManagerPointerAtEndOfStateList_returnsFalse() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);

        assertFalse(versionedEventManager.canRedo());
    }

    @Test
    public void undo_multipleEventManagerPointerAtEndOfStateList_success() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);

        versionedEventManager.undo();
        assertEventManagerListStatus(versionedEventManager,
                Collections.singletonList(emptyEventManager),
                eventManagerWithAmy,
                Collections.singletonList(eventManagerWithBob));
    }

    @Test
    public void undo_multipleEventManagerPointerNotAtStartOfStateList_success() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEventManager, 1);

        versionedEventManager.undo();
        assertEventManagerListStatus(versionedEventManager,
                Collections.emptyList(),
                emptyEventManager,
                Arrays.asList(eventManagerWithAmy, eventManagerWithBob));
    }

    @Test
    public void undo_singleEventManager_throwsNoUndoableStateException() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(emptyEventManager);

        assertThrows(VersionedEventManager.NoUndoableStateException.class, versionedEventManager::undo);
    }

    @Test
    public void undo_multipleEventManagerPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEventManager, 2);

        assertThrows(VersionedEventManager.NoUndoableStateException.class, versionedEventManager::undo);
    }

    @Test
    public void redo_multipleEventManagerPointerNotAtEndOfStateList_success() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEventManager, 1);

        versionedEventManager.redo();
        assertEventManagerListStatus(versionedEventManager,
                Arrays.asList(emptyEventManager, eventManagerWithAmy),
                eventManagerWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleEventManagerPointerAtStartOfStateList_success() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEventManager, 2);

        versionedEventManager.redo();
        assertEventManagerListStatus(versionedEventManager,
                Collections.singletonList(emptyEventManager),
                eventManagerWithAmy,
                Collections.singletonList(eventManagerWithBob));
    }

    @Test
    public void redo_singleEventManager_throwsNoRedoableStateException() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(emptyEventManager);

        assertThrows(VersionedEventManager.NoRedoableStateException.class, versionedEventManager::redo);
    }

    @Test
    public void redo_multipleEventManagerPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(
                emptyEventManager, eventManagerWithAmy, eventManagerWithBob);

        assertThrows(VersionedEventManager.NoRedoableStateException.class, versionedEventManager::redo);
    }

    @Test
    public void equals() {
        VersionedEventManager versionedEventManager = prepareEventManagerList(eventManagerWithAmy, eventManagerWithBob);

        // same values -> returns true
        VersionedEventManager copy = prepareEventManagerList(eventManagerWithAmy, eventManagerWithBob);
        assertTrue(versionedEventManager.equals(copy));

        // same object -> returns true
        assertTrue(versionedEventManager.equals(versionedEventManager));

        // null -> returns false
        assertFalse(versionedEventManager.equals(null));

        // different types -> returns false
        assertFalse(versionedEventManager.equals(1));

        // different state list -> returns false
        VersionedEventManager differentEManagerList = prepareEventManagerList(eventManagerWithBob,
                eventManagerWithCarl);
        assertFalse(versionedEventManager.equals(differentEManagerList));

        // different current pointer index -> returns false
        VersionedEventManager differentCurrentStatePointer = prepareEventManagerList(
                eventManagerWithAmy, eventManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEventManager, 1);
        assertFalse(versionedEventManager.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedEventManager} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedEventManager#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedEventManager#currentStatePointer} is equal to {@code expectedStatesAfterPointer}
     * .
     */
    private void assertEventManagerListStatus(VersionedEventManager versionedEventManager,
                                             List<ReadOnlyEventManager> expectedStatesBeforePointer,
                                             ReadOnlyEventManager expectedCurrentState,
                                             List<ReadOnlyEventManager> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new EventManager(versionedEventManager), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedEventManager.canUndo()) {
            versionedEventManager.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyEventManager expectedEventManager : expectedStatesBeforePointer) {
            assertEquals(expectedEventManager, new EventManager(versionedEventManager));
            versionedEventManager.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyEventManager expectedEventManager : expectedStatesAfterPointer) {
            versionedEventManager.redo();
            assertEquals(expectedEventManager, new EventManager(versionedEventManager));
        }

        // check that there are no more states after pointer
        assertFalse(versionedEventManager.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedEventManager.undo());
    }

    /**
     * Creates and returns a {@code VersionedEventManager} with the {@code eventManagerStates} added into it, and the
     * {@code VersionedEventManager#currentStatePointer} at the end of list.
     */
    private VersionedEventManager prepareEventManagerList(ReadOnlyEventManager... eventManagerStates) {
        assertFalse(eventManagerStates.length == 0);

        VersionedEventManager versionedEventManager = new VersionedEventManager(eventManagerStates[0]);
        for (int i = 1; i < eventManagerStates.length; i++) {
            versionedEventManager.resetData(eventManagerStates[i]);
            versionedEventManager.commit();
        }

        return versionedEventManager;
    }

    /**
     * Shifts the {@code versionedEventManager#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedEventManager versionedEventManager, int count) {
        for (int i = 0; i < count; i++) {
            versionedEventManager.undo();
        }
    }
}
