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

import seedu.address.testutil.WorkoutBookBuilder;

public class VersionedWorkoutBookTest {

    private final ReadOnlyWorkoutBook workoutBookWithAmy = new WorkoutBookBuilder().withWorkout(AMY_WORKOUT).build();
    private final ReadOnlyWorkoutBook workoutBookWithBob = new WorkoutBookBuilder().withWorkout(BOB_WORKOUT).build();
    private final ReadOnlyWorkoutBook workoutBookWithCarl = new WorkoutBookBuilder().withWorkout(CARL_WORKOUT).build();
    private final ReadOnlyWorkoutBook emptyWorkoutBook = new WorkoutBookBuilder().build();

    @Test
    public void commit_singleWorkoutBook_noStatesRemovedCurrentStateSaved() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(emptyWorkoutBook);

        versionedWorkoutBook.commit();
        assertWorkoutBookListStatus(versionedWorkoutBook,
                Collections.singletonList(emptyWorkoutBook),
                emptyWorkoutBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleWorkoutBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);

        versionedWorkoutBook.commit();
        assertWorkoutBookListStatus(versionedWorkoutBook,
                Arrays.asList(emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob),
                workoutBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleWorkoutBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedWorkoutBook, 2);

        versionedWorkoutBook.commit();
        assertWorkoutBookListStatus(versionedWorkoutBook,
                Collections.singletonList(emptyWorkoutBook),
                emptyWorkoutBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleWorkoutBookPointerAtEndOfStateList_returnsTrue() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);

        assertTrue(versionedWorkoutBook.canUndo());
    }

    @Test
    public void canUndo_multipleWorkoutBookPointerAtStartOfStateList_returnsTrue() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedWorkoutBook, 1);

        assertTrue(versionedWorkoutBook.canUndo());
    }

    @Test
    public void canUndo_singleWorkoutBook_returnsFalse() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(emptyWorkoutBook);

        assertFalse(versionedWorkoutBook.canUndo());
    }

    @Test
    public void canUndo_multipleWorkoutBookPointerAtStartOfStateList_returnsFalse() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedWorkoutBook, 2);

        assertFalse(versionedWorkoutBook.canUndo());
    }

    @Test
    public void canRedo_multipleWorkoutBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedWorkoutBook, 1);

        assertTrue(versionedWorkoutBook.canRedo());
    }

    @Test
    public void canRedo_multipleWorkoutBookPointerAtStartOfStateList_returnsTrue() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedWorkoutBook, 2);

        assertTrue(versionedWorkoutBook.canRedo());
    }

    @Test
    public void canRedo_singleWorkoutBook_returnsFalse() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(emptyWorkoutBook);

        assertFalse(versionedWorkoutBook.canRedo());
    }

    @Test
    public void canRedo_multipleWorkoutBookPointerAtEndOfStateList_returnsFalse() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);

        assertFalse(versionedWorkoutBook.canRedo());
    }

    @Test
    public void undo_multipleWorkoutBookPointerAtEndOfStateList_success() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);

        versionedWorkoutBook.undo();
        assertWorkoutBookListStatus(versionedWorkoutBook,
                Collections.singletonList(emptyWorkoutBook),
                workoutBookWithAmy,
                Collections.singletonList(workoutBookWithBob));
    }

    @Test
    public void undo_multipleWorkoutBookPointerNotAtStartOfStateList_success() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedWorkoutBook, 1);

        versionedWorkoutBook.undo();
        assertWorkoutBookListStatus(versionedWorkoutBook,
                Collections.emptyList(),
                emptyWorkoutBook,
                Arrays.asList(workoutBookWithAmy, workoutBookWithBob));
    }

    @Test
    public void undo_singleWorkoutBook_throwsNoUndoableStateException() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(emptyWorkoutBook);

        assertThrows(VersionedWorkoutBook.NoUndoableStateException.class, versionedWorkoutBook::undo);
    }

    @Test
    public void undo_multipleWorkoutBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedWorkoutBook, 2);

        assertThrows(VersionedWorkoutBook.NoUndoableStateException.class, versionedWorkoutBook::undo);
    }

    @Test
    public void redo_multipleWorkoutBookPointerNotAtEndOfStateList_success() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedWorkoutBook, 1);

        versionedWorkoutBook.redo();
        assertWorkoutBookListStatus(versionedWorkoutBook,
                Arrays.asList(emptyWorkoutBook, workoutBookWithAmy),
                workoutBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleWorkoutBookPointerAtStartOfStateList_success() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedWorkoutBook, 2);

        versionedWorkoutBook.redo();
        assertWorkoutBookListStatus(versionedWorkoutBook,
                Collections.singletonList(emptyWorkoutBook),
                workoutBookWithAmy,
                Collections.singletonList(workoutBookWithBob));
    }

    @Test
    public void redo_singleWorkoutBook_throwsNoRedoableStateException() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(emptyWorkoutBook);

        assertThrows(VersionedWorkoutBook.NoRedoableStateException.class, versionedWorkoutBook::redo);
    }

    @Test
    public void redo_multipleWorkoutBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(
                emptyWorkoutBook, workoutBookWithAmy, workoutBookWithBob);

        assertThrows(VersionedWorkoutBook.NoRedoableStateException.class, versionedWorkoutBook::redo);
    }

    @Test
    public void equals() {
        VersionedWorkoutBook versionedWorkoutBook = prepareWorkoutBookList(workoutBookWithAmy, workoutBookWithBob);

        // same values -> returns true
        VersionedWorkoutBook copy = prepareWorkoutBookList(workoutBookWithAmy, workoutBookWithBob);
        assertTrue(versionedWorkoutBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedWorkoutBook.equals(versionedWorkoutBook));

        // null -> returns false
        assertFalse(versionedWorkoutBook.equals(null));

        // different types -> returns false
        assertFalse(versionedWorkoutBook.equals(1));

        // different state list -> returns false
        VersionedWorkoutBook differentWorkoutBookList = prepareWorkoutBookList(workoutBookWithBob, workoutBookWithCarl);
        assertFalse(versionedWorkoutBook.equals(differentWorkoutBookList));

        // different current pointer index -> returns false
        VersionedWorkoutBook differentCurrentStatePointer = prepareWorkoutBookList(
                workoutBookWithAmy, workoutBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedWorkoutBook, 1);
        assertFalse(versionedWorkoutBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedWorkoutBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedWorkoutBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedWorkoutBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertWorkoutBookListStatus(VersionedWorkoutBook versionedWorkoutBook,
                                             List<ReadOnlyWorkoutBook> expectedStatesBeforePointer,
                                             ReadOnlyWorkoutBook expectedCurrentState,
                                             List<ReadOnlyWorkoutBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new WorkoutBook(versionedWorkoutBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedWorkoutBook.canUndo()) {
            versionedWorkoutBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyWorkoutBook expectedWorkoutBook : expectedStatesBeforePointer) {
            assertEquals(expectedWorkoutBook, new WorkoutBook(versionedWorkoutBook));
            versionedWorkoutBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyWorkoutBook expectedWorkoutBook : expectedStatesAfterPointer) {
            versionedWorkoutBook.redo();
            assertEquals(expectedWorkoutBook, new WorkoutBook(versionedWorkoutBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedWorkoutBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedWorkoutBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedWorkoutBook} with the {@code workoutBookStates} added into it, and the
     * {@code VersionedWorkoutBook#currentStatePointer} at the end of list.
     */
    private VersionedWorkoutBook prepareWorkoutBookList(ReadOnlyWorkoutBook... workoutBookStates) {
        assertFalse(workoutBookStates.length == 0);

        VersionedWorkoutBook versionedWorkoutBook = new VersionedWorkoutBook(workoutBookStates[0]);
        for (int i = 1; i < workoutBookStates.length; i++) {
            versionedWorkoutBook.resetData(workoutBookStates[i]);
            versionedWorkoutBook.commit();
        }

        return versionedWorkoutBook;
    }

    /**
     * Shifts the {@code versionedWorkoutBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedWorkoutBook versionedWorkoutBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedWorkoutBook.undo();
        }
    }
}
