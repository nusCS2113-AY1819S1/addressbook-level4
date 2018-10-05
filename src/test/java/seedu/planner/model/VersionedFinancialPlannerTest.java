package seedu.planner.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.planner.testutil.TypicalRecords.AMY;
import static seedu.planner.testutil.TypicalRecords.BOB;
import static seedu.planner.testutil.TypicalRecords.INDO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.planner.testutil.FinancialPlannerBuilder;

public class VersionedFinancialPlannerTest {

    private final ReadOnlyFinancialPlanner financialPlannerWithAmy =
            new FinancialPlannerBuilder().withRecord(AMY).build();
    private final ReadOnlyFinancialPlanner financialPlannerWithBob =
            new FinancialPlannerBuilder().withRecord(BOB).build();
    private final ReadOnlyFinancialPlanner financialPlannerWithIndo =
            new FinancialPlannerBuilder().withRecord(INDO).build();
    private final ReadOnlyFinancialPlanner emptyFinancialPlanner =
            new FinancialPlannerBuilder().build();

    @Test
    public void commit_singleFinancialPlanner_noStatesRemovedCurrentStateSaved() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(emptyFinancialPlanner);

        versionedFinancialPlanner.commit();
        assertFinancialPlannerListStatus(versionedFinancialPlanner,
                Collections.singletonList(emptyFinancialPlanner),
                emptyFinancialPlanner,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleFinancialPlannerPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);

        versionedFinancialPlanner.commit();
        assertFinancialPlannerListStatus(versionedFinancialPlanner,
                Arrays.asList(emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob),
                financialPlannerWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleFinancialPlannerPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);
        shiftCurrentStatePointerLeftwards(versionedFinancialPlanner, 2);

        versionedFinancialPlanner.commit();
        assertFinancialPlannerListStatus(versionedFinancialPlanner,
                Collections.singletonList(emptyFinancialPlanner),
                emptyFinancialPlanner,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleFinancialPlannerPointerAtEndOfStateList_returnsTrue() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);

        assertTrue(versionedFinancialPlanner.canUndo());
    }

    @Test
    public void canUndo_multipleFinancialPlannerPointerAtStartOfStateList_returnsTrue() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);
        shiftCurrentStatePointerLeftwards(versionedFinancialPlanner, 1);

        assertTrue(versionedFinancialPlanner.canUndo());
    }

    @Test
    public void canUndo_singleFinancialPlanner_returnsFalse() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(emptyFinancialPlanner);

        assertFalse(versionedFinancialPlanner.canUndo());
    }

    @Test
    public void canUndo_multipleFinancialPlannerPointerAtStartOfStateList_returnsFalse() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);
        shiftCurrentStatePointerLeftwards(versionedFinancialPlanner, 2);

        assertFalse(versionedFinancialPlanner.canUndo());
    }

    @Test
    public void canRedo_multipleFinancialPlannerPointerNotAtEndOfStateList_returnsTrue() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);
        shiftCurrentStatePointerLeftwards(versionedFinancialPlanner, 1);

        assertTrue(versionedFinancialPlanner.canRedo());
    }

    @Test
    public void canRedo_multipleFinancialPlannerPointerAtStartOfStateList_returnsTrue() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);
        shiftCurrentStatePointerLeftwards(versionedFinancialPlanner, 2);

        assertTrue(versionedFinancialPlanner.canRedo());
    }

    @Test
    public void canRedo_singleFinancialPlanner_returnsFalse() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(emptyFinancialPlanner);

        assertFalse(versionedFinancialPlanner.canRedo());
    }

    @Test
    public void canRedo_multipleFinancialPlannerPointerAtEndOfStateList_returnsFalse() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);

        assertFalse(versionedFinancialPlanner.canRedo());
    }

    @Test
    public void undo_multipleFinancialPlannerPointerAtEndOfStateList_success() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);

        versionedFinancialPlanner.undo();
        assertFinancialPlannerListStatus(versionedFinancialPlanner,
                Collections.singletonList(emptyFinancialPlanner),
                financialPlannerWithAmy,
                Collections.singletonList(financialPlannerWithBob));
    }

    @Test
    public void undo_multipleFinancialPlannerPointerNotAtStartOfStateList_success() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);
        shiftCurrentStatePointerLeftwards(versionedFinancialPlanner, 1);

        versionedFinancialPlanner.undo();
        assertFinancialPlannerListStatus(versionedFinancialPlanner,
                Collections.emptyList(),
                emptyFinancialPlanner,
                Arrays.asList(financialPlannerWithAmy, financialPlannerWithBob));
    }

    @Test
    public void undo_singleFinancialPlanner_throwsNoUndoableStateException() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(emptyFinancialPlanner);

        assertThrows(VersionedFinancialPlanner.NoUndoableStateException.class, versionedFinancialPlanner::undo);
    }

    @Test
    public void undo_multipleFinancialPlannerPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);
        shiftCurrentStatePointerLeftwards(versionedFinancialPlanner, 2);

        assertThrows(VersionedFinancialPlanner.NoUndoableStateException.class, versionedFinancialPlanner::undo);
    }

    @Test
    public void redo_multipleFinancialPlannerPointerNotAtEndOfStateList_success() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);
        shiftCurrentStatePointerLeftwards(versionedFinancialPlanner, 1);

        versionedFinancialPlanner.redo();
        assertFinancialPlannerListStatus(versionedFinancialPlanner,
                Arrays.asList(emptyFinancialPlanner, financialPlannerWithAmy),
                financialPlannerWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleFinancialPlannerPointerAtStartOfStateList_success() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);
        shiftCurrentStatePointerLeftwards(versionedFinancialPlanner, 2);

        versionedFinancialPlanner.redo();
        assertFinancialPlannerListStatus(versionedFinancialPlanner,
                Collections.singletonList(emptyFinancialPlanner),
                financialPlannerWithAmy,
                Collections.singletonList(financialPlannerWithBob));
    }

    @Test
    public void redo_singleFinancialPlanner_throwsNoRedoableStateException() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(emptyFinancialPlanner);

        assertThrows(VersionedFinancialPlanner.NoRedoableStateException.class, versionedFinancialPlanner::redo);
    }

    @Test
    public void redo_multipleFinancialPlannerPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedFinancialPlanner versionedFinancialPlanner = prepareFinancialPlannerList(
                emptyFinancialPlanner, financialPlannerWithAmy, financialPlannerWithBob);

        assertThrows(VersionedFinancialPlanner.NoRedoableStateException.class, versionedFinancialPlanner::redo);
    }

    @Test
    public void equals() {
        VersionedFinancialPlanner versionedFinancialPlanner =
                prepareFinancialPlannerList(financialPlannerWithAmy, financialPlannerWithBob);

        // same values -> returns true
        VersionedFinancialPlanner copy = prepareFinancialPlannerList(financialPlannerWithAmy, financialPlannerWithBob);
        assertTrue(versionedFinancialPlanner.equals(copy));

        // same object -> returns true
        assertTrue(versionedFinancialPlanner.equals(versionedFinancialPlanner));

        // null -> returns false
        assertFalse(versionedFinancialPlanner.equals(null));

        // different types -> returns false
        assertFalse(versionedFinancialPlanner.equals(1));

        // different state list -> returns false
        VersionedFinancialPlanner differentFinancialPlannerList =
                prepareFinancialPlannerList(financialPlannerWithBob, financialPlannerWithIndo);
        assertFalse(versionedFinancialPlanner.equals(differentFinancialPlannerList));

        // different current pointer index -> returns false
        VersionedFinancialPlanner differentCurrentStatePointer = prepareFinancialPlannerList(
                financialPlannerWithAmy, financialPlannerWithBob);
        shiftCurrentStatePointerLeftwards(versionedFinancialPlanner, 1);
        assertFalse(versionedFinancialPlanner.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedFinancialPlanner} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedFinancialPlanner#currentStatePointer} is equal
     * to {@code expectedStatesBeforePointer} and states after {@code versionedFinancialPlanner#currentStatePointer}
     * is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertFinancialPlannerListStatus(VersionedFinancialPlanner versionedFinancialPlanner,
                                             List<ReadOnlyFinancialPlanner> expectedStatesBeforePointer,
                                             ReadOnlyFinancialPlanner expectedCurrentState,
                                             List<ReadOnlyFinancialPlanner> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new FinancialPlanner(versionedFinancialPlanner), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedFinancialPlanner.canUndo()) {
            versionedFinancialPlanner.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyFinancialPlanner expectedFinancialPlanner : expectedStatesBeforePointer) {
            assertEquals(expectedFinancialPlanner, new FinancialPlanner(versionedFinancialPlanner));
            versionedFinancialPlanner.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyFinancialPlanner expectedFinancialPlanner : expectedStatesAfterPointer) {
            versionedFinancialPlanner.redo();
            assertEquals(expectedFinancialPlanner, new FinancialPlanner(versionedFinancialPlanner));
        }

        // check that there are no more states after pointer
        assertFalse(versionedFinancialPlanner.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedFinancialPlanner.undo());
    }

    /**
     * Creates and returns a {@code VersionedFinancialPlanner} with the {@code financialPlannerStates} added into it,
     * and the {@code VersionedFinancialPlanner#currentStatePointer} at the end of list.
     */
    private VersionedFinancialPlanner prepareFinancialPlannerList(ReadOnlyFinancialPlanner... financialPlannerStates) {
        assertFalse(financialPlannerStates.length == 0);

        VersionedFinancialPlanner versionedFinancialPlanner = new VersionedFinancialPlanner(financialPlannerStates[0]);
        for (int i = 1; i < financialPlannerStates.length; i++) {
            versionedFinancialPlanner.resetData(financialPlannerStates[i]);
            versionedFinancialPlanner.commit();
        }

        return versionedFinancialPlanner;
    }

    /**
     * Shifts the {@code versionedFinancialPlanner#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedFinancialPlanner versionedFinancialPlanner, int count) {
        for (int i = 0; i < count; i++) {
            versionedFinancialPlanner.undo();
        }
    }
}
