package seedu.address.model.expenses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.expenses.TypicalExpenses.ALICE_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.BENSON_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.CARL_CLAIM;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.expenses.ExpensesListBuilder;

public class VersionedExpensesListTest {

    private final ReadOnlyExpensesList expensesListWithAlice =
            new ExpensesListBuilder().withExpenses(ALICE_CLAIM).build();
    private final ReadOnlyExpensesList expensesListWithBenson =
            new ExpensesListBuilder().withExpenses(BENSON_CLAIM).build();
    private final ReadOnlyExpensesList expensesListWithCarl =
            new ExpensesListBuilder().withExpenses(CARL_CLAIM).build();
    private final ReadOnlyExpensesList emptyExpensesList = new ExpensesListBuilder().build();

    @Test
    public void commit_singleExpensesList_noStatesRemovedCurrentStateSaved() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(emptyExpensesList);

        versionedExpensesList.commit();
        assertExpensesListListStatus(versionedExpensesList,
                Collections.singletonList(emptyExpensesList),
                emptyExpensesList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleExpensesListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);

        versionedExpensesList.commit();
        assertExpensesListListStatus(versionedExpensesList,
                Arrays.asList(emptyExpensesList, expensesListWithAlice, expensesListWithBenson),
                expensesListWithBenson,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplExpensesListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedExpensesList, 2);

        versionedExpensesList.commit();
        assertExpensesListListStatus(versionedExpensesList,
                Collections.singletonList(emptyExpensesList),
                emptyExpensesList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleExpensesListPointerAtEndOfStateList_returnsTrue() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);

        assertTrue(versionedExpensesList.canUndo());
    }

    @Test
    public void canUndo_multipleExpensesListPointerAtStartOfStateList_returnsTrue() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedExpensesList, 1);

        assertTrue(versionedExpensesList.canUndo());
    }

    @Test
    public void canUndo_singleExpensesList_returnsFalse() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(emptyExpensesList);

        assertFalse(versionedExpensesList.canUndo());
    }

    @Test
    public void canUndo_multipleExpensesListPointerAtStartOfStateList_returnsFalse() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedExpensesList, 2);

        assertFalse(versionedExpensesList.canUndo());
    }

    @Test
    public void canRedo_multipleExpensesListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedExpensesList, 1);

        assertTrue(versionedExpensesList.canRedo());
    }

    @Test
    public void canRedo_multipleExpensesListPointerAtStartOfStateList_returnsTrue() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedExpensesList, 2);

        assertTrue(versionedExpensesList.canRedo());
    }

    @Test
    public void canRedo_singleExpensesList_returnsFalse() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(emptyExpensesList);

        assertFalse(versionedExpensesList.canRedo());
    }

    @Test
    public void canRedo_multipleExpensesListPointerAtEndOfStateList_returnsFalse() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);

        assertFalse(versionedExpensesList.canRedo());
    }

    @Test
    public void undo_multipleExpensesListPointerAtEndOfStateList_success() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);

        versionedExpensesList.undo();
        assertExpensesListListStatus(versionedExpensesList,
                Collections.singletonList(emptyExpensesList),
                expensesListWithAlice,
                Collections.singletonList(expensesListWithBenson));
    }

    @Test
    public void undo_multipleExpensesListPointerNotAtStartOfStateList_success() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedExpensesList, 1);

        versionedExpensesList.undo();
        assertExpensesListListStatus(versionedExpensesList,
                Collections.emptyList(),
                emptyExpensesList,
                Arrays.asList(expensesListWithAlice, expensesListWithBenson));
    }

    @Test
    public void undo_singleExpensesList_throwsNoUndoableStateException() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(emptyExpensesList);

        assertThrows(VersionedExpensesList.NoUndoableStateException.class, versionedExpensesList::undo);
    }

    @Test
    public void undo_multipleExpensesListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedExpensesList, 2);

        assertThrows(VersionedExpensesList.NoUndoableStateException.class, versionedExpensesList::undo);
    }

    @Test
    public void redo_multipleExpensesListPointerNotAtEndOfStateList_success() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedExpensesList, 1);

        versionedExpensesList.redo();
        assertExpensesListListStatus(versionedExpensesList,
                Arrays.asList(emptyExpensesList, expensesListWithAlice),
                expensesListWithBenson,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleExpensesListPointerAtStartOfStateList_success() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedExpensesList, 2);

        versionedExpensesList.redo();
        assertExpensesListListStatus(versionedExpensesList,
                Collections.singletonList(emptyExpensesList),
                expensesListWithAlice,
                Collections.singletonList(expensesListWithBenson));
    }

    @Test
    public void redo_singleExpensesList_throwsNoRedoableStateException() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(emptyExpensesList);
        assertThrows(VersionedExpensesList.NoRedoableStateException.class, versionedExpensesList::redo);
    }

    @Test
    public void redo_multipleExpensesListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(
                emptyExpensesList, expensesListWithAlice, expensesListWithBenson);

        assertThrows(VersionedExpensesList.NoRedoableStateException.class, versionedExpensesList::redo);
    }

    @Test
    public void equals() {
        VersionedExpensesList versionedExpensesList = prepareExpensesList(expensesListWithAlice,
                expensesListWithBenson);

        // same values -> returns true
        VersionedExpensesList copy = prepareExpensesList(expensesListWithAlice, expensesListWithBenson);
        assertTrue(versionedExpensesList.equals(copy));

        // same object -> returns true
        assertTrue(versionedExpensesList.equals(versionedExpensesList));

        // null -> returns false
        assertFalse(versionedExpensesList.equals(null));

        // different types -> returns false
        assertFalse(versionedExpensesList.equals(1));

        // different state list -> returns false
        VersionedExpensesList differentExpensesList = prepareExpensesList(expensesListWithBenson,
                expensesListWithCarl);
        assertFalse(versionedExpensesList.equals(differentExpensesList));

        // different current pointer index -> returns false
        VersionedExpensesList differentCurrentStatePointer = prepareExpensesList(
                expensesListWithAlice, expensesListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedExpensesList, 1);
        assertFalse(versionedExpensesList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedExpensesList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedExpensesList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedExpensesList#currentStatePointer} is equal to
     * {@code expectedStatesAfterPointer}.
     */
    private void assertExpensesListListStatus(VersionedExpensesList versionedExpensesList,
                                              List<ReadOnlyExpensesList> expectedStatesBeforePointer,
                                              ReadOnlyExpensesList expectedCurrentState,
                                              List<ReadOnlyExpensesList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ExpensesList(versionedExpensesList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedExpensesList.canUndo()) {
            versionedExpensesList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyExpensesList expectedExpensesList : expectedStatesBeforePointer) {
            assertEquals(expectedExpensesList, new ExpensesList(versionedExpensesList));
            versionedExpensesList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyExpensesList expectedExpensesList : expectedStatesAfterPointer) {
            versionedExpensesList.redo();
            assertEquals(expectedExpensesList, new ExpensesList(versionedExpensesList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedExpensesList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedExpensesList.undo());
    }

    /**
     * Creates and returns a {@code versionedExpensesList} with the {@code expensesListStates} added into it, and the
     * {@code versionedExpensesList#currentStatePointer} at the end of list.
     */
    private VersionedExpensesList prepareExpensesList(ReadOnlyExpensesList... expensesListStates) {
        assertFalse(expensesListStates.length == 0);

        VersionedExpensesList versionedExpensesList = new VersionedExpensesList(expensesListStates[0]);
        for (int i = 1; i < expensesListStates.length; i++) {
            versionedExpensesList.resetData(expensesListStates[i]);
            versionedExpensesList.commit();
        }

        return versionedExpensesList;
    }

    /**
     * Shifts the {@code versionedExpensesList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedExpensesList versionedExpensesList, int count) {
        for (int i = 0; i < count; i++) {
            versionedExpensesList.undo();
        }
    }

}
