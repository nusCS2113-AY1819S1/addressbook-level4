//@@author SHININGGGG
package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalExpenditures.CHICKEN;
import static seedu.address.testutil.TypicalExpenditures.IPHONE;
import static seedu.address.testutil.TypicalExpenditures.SPEAKER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ExpenditureTrackerBuilder;

public class VersionedExpenditureTrackerTest {

    private final ReadOnlyExpenditureTracker expenditureTrackerWithChicken = new ExpenditureTrackerBuilder()
            .withExpenditure(CHICKEN).build();
    private final ReadOnlyExpenditureTracker expenditureTrackerWithIphone = new ExpenditureTrackerBuilder()
            .withExpenditure(IPHONE).build();
    private final ReadOnlyExpenditureTracker expenditureTrackerWithSpeaker = new ExpenditureTrackerBuilder()
            .withExpenditure(SPEAKER).build();
    private final ReadOnlyExpenditureTracker emptyExpenditureTracker = new ExpenditureTrackerBuilder().build();

    @Test
    public void commit_singleExpenditureTracker_noStatesRemovedCurrentStateSaved() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(emptyExpenditureTracker);

        versionedExpenditureTracker.commit();
        assertExpenditureTrackerStatus(versionedExpenditureTracker,
                Collections.singletonList(emptyExpenditureTracker),
                emptyExpenditureTracker,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleEtPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);
        shiftCurrentStatePointerLeftwards(versionedExpenditureTracker, 2);

        versionedExpenditureTracker.commit();
        assertExpenditureTrackerStatus(versionedExpenditureTracker,
                Collections.singletonList(emptyExpenditureTracker),
                emptyExpenditureTracker,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleExpenditureTrackerPointerAtEndOfStateList_returnsTrue() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);

        assertTrue(versionedExpenditureTracker.canUndo());
    }

    @Test
    public void canUndo_multipleExpenditureTrackerPointerAtStartOfStateList_returnsTrue() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);
        shiftCurrentStatePointerLeftwards(versionedExpenditureTracker, 1);

        assertTrue(versionedExpenditureTracker.canUndo());
    }

    @Test
    public void canUndo_singleExpenditureTracker_returnsFalse() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(emptyExpenditureTracker);

        assertFalse(versionedExpenditureTracker.canUndo());
    }

    @Test
    public void canUndo_multipleExpenditureTrackerPointerAtStartOfStateList_returnsFalse() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);
        shiftCurrentStatePointerLeftwards(versionedExpenditureTracker, 2);

        assertFalse(versionedExpenditureTracker.canUndo());
    }

    @Test
    public void canRedo_multipleExpenditureTrackerPointerNotAtEndOfStateList_returnsTrue() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);
        shiftCurrentStatePointerLeftwards(versionedExpenditureTracker, 1);

        assertTrue(versionedExpenditureTracker.canRedo());
    }

    @Test
    public void canRedo_multipleExpenditureTrackerPointerAtStartOfStateList_returnsTrue() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);
        shiftCurrentStatePointerLeftwards(versionedExpenditureTracker, 2);

        assertTrue(versionedExpenditureTracker.canRedo());
    }

    @Test
    public void canRedo_singleExpenditureTracker_returnsFalse() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(emptyExpenditureTracker);

        assertFalse(versionedExpenditureTracker.canRedo());
    }

    @Test
    public void canRedo_multipleExpenditureTrackerPointerAtEndOfStateList_returnsFalse() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);

        assertFalse(versionedExpenditureTracker.canRedo());
    }

    @Test
    public void undo_multipleExpenditureTrackerPointerAtEndOfStateList_success() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);

        versionedExpenditureTracker.undo();
        assertExpenditureTrackerStatus(versionedExpenditureTracker,
                Collections.singletonList(emptyExpenditureTracker),
                expenditureTrackerWithChicken,
                Collections.singletonList(expenditureTrackerWithIphone));
    }

    @Test
    public void undo_multipleExpenditureTrackerPointerNotAtStartOfStateList_success() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);
        shiftCurrentStatePointerLeftwards(versionedExpenditureTracker, 1);

        versionedExpenditureTracker.undo();
        assertExpenditureTrackerStatus(versionedExpenditureTracker,
                Collections.emptyList(),
                emptyExpenditureTracker,
                Arrays.asList(expenditureTrackerWithChicken, expenditureTrackerWithIphone));
    }

    @Test
    public void undo_singleExpenditureTracker_throwsNoUndoableStateException() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(emptyExpenditureTracker);

        assertThrows(VersionedExpenditureTracker.NoUndoableStateException.class, versionedExpenditureTracker::undo);
    }

    @Test
    public void undo_multipleExpenditureTrackerPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);
        shiftCurrentStatePointerLeftwards(versionedExpenditureTracker, 2);

        assertThrows(VersionedExpenditureTracker.NoUndoableStateException.class, versionedExpenditureTracker::undo);
    }

    @Test
    public void redo_multipleExpenditureTrackerPointerNotAtEndOfStateList_success() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);
        shiftCurrentStatePointerLeftwards(versionedExpenditureTracker, 1);

        versionedExpenditureTracker.redo();
        assertExpenditureTrackerStatus(versionedExpenditureTracker,
                Arrays.asList(emptyExpenditureTracker, expenditureTrackerWithChicken),
                expenditureTrackerWithIphone,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleExpenditureTrackerPointerAtStartOfStateList_success() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);
        shiftCurrentStatePointerLeftwards(versionedExpenditureTracker, 2);

        versionedExpenditureTracker.redo();
        assertExpenditureTrackerStatus(versionedExpenditureTracker,
                Collections.singletonList(emptyExpenditureTracker),
                expenditureTrackerWithChicken,
                Collections.singletonList(expenditureTrackerWithIphone));
    }

    @Test
    public void redo_singleExpenditureTracker_throwsNoRedoableStateException() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(emptyExpenditureTracker);

        assertThrows(VersionedExpenditureTracker.NoRedoableStateException.class, versionedExpenditureTracker::redo);
    }

    @Test
    public void redo_multipleExpenditureTrackerPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedExpenditureTracker versionedExpenditureTracker = prepareExpenditureTracker(
                emptyExpenditureTracker, expenditureTrackerWithChicken, expenditureTrackerWithIphone);

        assertThrows(VersionedExpenditureTracker.NoRedoableStateException.class, versionedExpenditureTracker::redo);
    }

    @Test
    public void equals() {
        VersionedExpenditureTracker versionedExpenditureTracker =
                prepareExpenditureTracker(expenditureTrackerWithChicken,
                expenditureTrackerWithIphone);

        // same values -> returns true
        VersionedExpenditureTracker copy = prepareExpenditureTracker(expenditureTrackerWithChicken,
                expenditureTrackerWithIphone);
        assertTrue(versionedExpenditureTracker.equals(copy));

        // same object -> returns true
        assertTrue(versionedExpenditureTracker.equals(versionedExpenditureTracker));

        // null -> returns false
        assertFalse(versionedExpenditureTracker.equals(null));

        // different types -> returns false
        assertFalse(versionedExpenditureTracker.equals(1));

        // different current pointer index -> returns false
        VersionedExpenditureTracker differentCurrentStatePointer = prepareExpenditureTracker(
                expenditureTrackerWithChicken, expenditureTrackerWithIphone);
        shiftCurrentStatePointerLeftwards(versionedExpenditureTracker, 1);
        assertFalse(versionedExpenditureTracker.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedExpenditureTracker} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedExpenditureTracker#currentStatePointer}
     * is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedExpenditureTracker#currentStatePointer}
     * is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertExpenditureTrackerStatus(VersionedExpenditureTracker versionedExpenditureTracker,
                                      List<ReadOnlyExpenditureTracker> expectedStatesBeforePointer,
                                      ReadOnlyExpenditureTracker expectedCurrentState,
                                      List<ReadOnlyExpenditureTracker> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ExpenditureTracker(versionedExpenditureTracker), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedExpenditureTracker.canUndo()) {
            versionedExpenditureTracker.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyExpenditureTracker expectedExpenditureTracker : expectedStatesBeforePointer) {
            assertEquals(expectedExpenditureTracker, new ExpenditureTracker(versionedExpenditureTracker));
            versionedExpenditureTracker.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyExpenditureTracker expectedExpenditureTracker : expectedStatesAfterPointer) {
            versionedExpenditureTracker.redo();
            assertEquals(expectedExpenditureTracker, new ExpenditureTracker(versionedExpenditureTracker));
        }

        // check that there are no more states after pointer
        assertFalse(versionedExpenditureTracker.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedExpenditureTracker.undo());
    }

    /**
     * Creates and returns a {@code versionedExpenditureTracker}
     * with the {@code expenditureTrackerStates} added into it, and the
     * {@code versionedExpenditureTracker#currentStatePointer} at the end of list.
     */
    private VersionedExpenditureTracker prepareExpenditureTracker(ReadOnlyExpenditureTracker...
                                                                          expenditureTrackerStates) {
        assertFalse(expenditureTrackerStates.length == 0);

        VersionedExpenditureTracker versionedExpenditureTracker =
                new VersionedExpenditureTracker(expenditureTrackerStates[0]);
        for (int i = 1; i < expenditureTrackerStates.length; i++) {
            versionedExpenditureTracker.resetData(expenditureTrackerStates[i]);
            versionedExpenditureTracker.commit();
        }

        return versionedExpenditureTracker;
    }

    /**
     * Shifts the {@code versionedExpenditureTracker#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedExpenditureTracker versionedExpenditureTracker,
                                                   int count) {
        for (int i = 0; i < count; i++) {
            versionedExpenditureTracker.undo();
        }
    }
}
