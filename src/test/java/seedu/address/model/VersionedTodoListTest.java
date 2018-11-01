package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalTasks.REFLECTION;
import static seedu.address.testutil.TypicalTasks.REVISION;
import static seedu.address.testutil.TypicalTasks.SURVEY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TodoListBuilder;

public class VersionedTodoListTest {

    private final ReadOnlyTodoList todoListWithReflection = new TodoListBuilder().withTask(REFLECTION).build();
    private final ReadOnlyTodoList todoListWithRevision = new TodoListBuilder().withTask(REVISION).build();
    private final ReadOnlyTodoList todoListWithSurvey = new TodoListBuilder().withTask(SURVEY).build();
    private final ReadOnlyTodoList emptyTodoList = new TodoListBuilder().build();

    @Test
    public void commit_singleTodoList_noStatesRemovedCurrentStateSaved() {
        VersionedTodoList versionedTodoList = prepareTodoList(emptyTodoList);

        versionedTodoList.commit();
        assertTodoListStatus(versionedTodoList,
                Collections.singletonList(emptyTodoList),
                emptyTodoList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTodoListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);

        versionedTodoList.commit();
        assertTodoListStatus(versionedTodoList,
                Arrays.asList(emptyTodoList, todoListWithReflection, todoListWithRevision),
                todoListWithRevision,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTodoListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 2);

        versionedTodoList.commit();
        assertTodoListStatus(versionedTodoList,
                Collections.singletonList(emptyTodoList),
                emptyTodoList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleTodoListPointerAtEndOfStateList_returnsTrue() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);

        assertTrue(versionedTodoList.canUndo());
    }

    @Test
    public void canUndo_multipleTodoListPointerAtStartOfStateList_returnsTrue() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 1);

        assertTrue(versionedTodoList.canUndo());
    }

    @Test
    public void canUndo_singleTodoList_returnsFalse() {
        VersionedTodoList versionedTodoList = prepareTodoList(emptyTodoList);

        assertFalse(versionedTodoList.canUndo());
    }

    @Test
    public void canUndo_multipleTodoListPointerAtStartOfStateList_returnsFalse() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 2);

        assertFalse(versionedTodoList.canUndo());
    }

    @Test
    public void canRedo_multipleTodoListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 1);

        assertTrue(versionedTodoList.canRedo());
    }

    @Test
    public void canRedo_multipleTodoListPointerAtStartOfStateList_returnsTrue() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 2);

        assertTrue(versionedTodoList.canRedo());
    }

    @Test
    public void canRedo_singleTodoList_returnsFalse() {
        VersionedTodoList versionedTodoList = prepareTodoList(emptyTodoList);

        assertFalse(versionedTodoList.canRedo());
    }

    @Test
    public void canRedo_multipleTodoListPointerAtEndOfStateList_returnsFalse() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);

        assertFalse(versionedTodoList.canRedo());
    }

    @Test
    public void undo_multipleTodoListPointerAtEndOfStateList_success() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);

        versionedTodoList.undo();
        assertTodoListStatus(versionedTodoList,
                Collections.singletonList(emptyTodoList),
                todoListWithReflection,
                Collections.singletonList(todoListWithRevision));
    }

    @Test
    public void undo_multipleTodoListPointerNotAtStartOfStateList_success() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 1);

        versionedTodoList.undo();
        assertTodoListStatus(versionedTodoList,
                Collections.emptyList(),
                emptyTodoList,
                Arrays.asList(todoListWithReflection, todoListWithRevision));
    }

    @Test
    public void undo_singleTodoList_throwsNoUndoableStateException() {
        VersionedTodoList versionedTodoList = prepareTodoList(emptyTodoList);

        assertThrows(VersionedTodoList.NoUndoableStateException.class, versionedTodoList::undo);
    }

    @Test
    public void undo_multipleTodoListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 2);

        assertThrows(VersionedTodoList.NoUndoableStateException.class, versionedTodoList::undo);
    }

    @Test
    public void redo_multipleTodoListPointerNotAtEndOfStateList_success() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 1);

        versionedTodoList.redo();
        assertTodoListStatus(versionedTodoList,
                Arrays.asList(emptyTodoList, todoListWithReflection),
                todoListWithRevision,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleTodoListPointerAtStartOfStateList_success() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 2);

        versionedTodoList.redo();
        assertTodoListStatus(versionedTodoList,
                Collections.singletonList(emptyTodoList),
                todoListWithReflection,
                Collections.singletonList(todoListWithRevision));
    }

    @Test
    public void redo_singleTodoList_throwsNoRedoableStateException() {
        VersionedTodoList versionedTodoList = prepareTodoList(emptyTodoList);

        assertThrows(VersionedTodoList.NoRedoableStateException.class, versionedTodoList::redo);
    }

    @Test
    public void redo_multipleTodoListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTodoList versionedTodoList = prepareTodoList(
                emptyTodoList, todoListWithReflection, todoListWithRevision);

        assertThrows(VersionedTodoList.NoRedoableStateException.class, versionedTodoList::redo);
    }

    @Test
    public void equals() {
        VersionedTodoList versionedTodoList = prepareTodoList(todoListWithReflection, todoListWithRevision);

        // same values -> returns true
        VersionedTodoList copy = prepareTodoList(todoListWithReflection, todoListWithRevision);
        assertTrue(versionedTodoList.equals(copy));

        // same object -> returns true
        assertTrue(versionedTodoList.equals(versionedTodoList));

        // null -> returns false
        assertFalse(versionedTodoList.equals(null));

        // different types -> returns false
        assertFalse(versionedTodoList.equals(1));

        // different state list -> returns false
        VersionedTodoList differentTodoList = prepareTodoList(todoListWithRevision, todoListWithSurvey);
        assertFalse(versionedTodoList.equals(differentTodoList));

        // different current pointer index -> returns false
        VersionedTodoList differentCurrentStatePointer = prepareTodoList(
                todoListWithReflection, todoListWithRevision);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 1);
        assertFalse(versionedTodoList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedTodoList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTodoList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedTodoList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertTodoListStatus(VersionedTodoList versionedTodoList,
                                             List<ReadOnlyTodoList> expectedStatesBeforePointer,
                                             ReadOnlyTodoList expectedCurrentState,
                                             List<ReadOnlyTodoList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new TodoList(versionedTodoList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTodoList.canUndo()) {
            versionedTodoList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTodoList expectedTodoList : expectedStatesBeforePointer) {
            assertEquals(expectedTodoList, new TodoList(versionedTodoList));
            versionedTodoList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTodoList expectedTodoList : expectedStatesAfterPointer) {
            versionedTodoList.redo();
            assertEquals(expectedTodoList, new TodoList(versionedTodoList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTodoList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTodoList.undo());
    }

    /**
     * Creates and returns a {@code VersionedTodoList} with the {@code todoListStates} added into it, and the
     * {@code VersionedTodoList#currentStatePointer} at the end of list.
     */
    private VersionedTodoList prepareTodoList(ReadOnlyTodoList... todoListStates) {
        assertFalse(todoListStates.length == 0);

        VersionedTodoList versionedTodoList = new VersionedTodoList(todoListStates[0]);
        for (int i = 1; i < todoListStates.length; i++) {
            versionedTodoList.resetData(todoListStates[i]);
            versionedTodoList.commit();
        }

        return versionedTodoList;
    }

    /**
     * Shifts the {@code versionedTodoList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTodoList versionedTodoList, int count) {
        for (int i = 0; i < count; i++) {
            versionedTodoList.undo();
        }
    }
}
