package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.MOTOR;
import static seedu.address.testutil.TypicalItems.RPLIDAR;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.StockListBuilder;

public class VersionedStockListTest {

    private final ReadOnlyStockList stockListWithAmy = new StockListBuilder().withItem(ARDUINO).build();
    private final ReadOnlyStockList stockListWithBob = new StockListBuilder().withItem(MOTOR).build();
    private final ReadOnlyStockList stockListWithCarl = new StockListBuilder().withItem(RPLIDAR).build();
    private final ReadOnlyStockList emptyStockList = new StockListBuilder().build();

    @Test
    public void commit_singleStockList_noStatesRemovedCurrentStateSaved() {
        VersionedStockList versionedStockList = prepareStockListList(emptyStockList);

        versionedStockList.commit();
        assertStockListListStatus(versionedStockList,
                Collections.singletonList(emptyStockList),
                emptyStockList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleStockListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);

        versionedStockList.commit();
        assertStockListListStatus(versionedStockList,
                Arrays.asList(emptyStockList, stockListWithAmy, stockListWithBob),
                stockListWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleStockListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 2);

        versionedStockList.commit();
        assertStockListListStatus(versionedStockList,
                Collections.singletonList(emptyStockList),
                emptyStockList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleStockListPointerAtEndOfStateList_returnsTrue() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);

        assertTrue(versionedStockList.canUndo());
    }

    @Test
    public void canUndo_multipleStockListPointerAtStartOfStateList_returnsTrue() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 1);

        assertTrue(versionedStockList.canUndo());
    }

    @Test
    public void canUndo_singleStockList_returnsFalse() {
        VersionedStockList versionedStockList = prepareStockListList(emptyStockList);

        assertFalse(versionedStockList.canUndo());
    }

    @Test
    public void canUndo_multipleStockListPointerAtStartOfStateList_returnsFalse() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 2);

        assertFalse(versionedStockList.canUndo());
    }

    @Test
    public void canRedo_multipleStockListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 1);

        assertTrue(versionedStockList.canRedo());
    }

    @Test
    public void canRedo_multipleStockListPointerAtStartOfStateList_returnsTrue() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 2);

        assertTrue(versionedStockList.canRedo());
    }

    @Test
    public void canRedo_singleStockList_returnsFalse() {
        VersionedStockList versionedStockList = prepareStockListList(emptyStockList);

        assertFalse(versionedStockList.canRedo());
    }

    @Test
    public void canRedo_multipleStockListPointerAtEndOfStateList_returnsFalse() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);

        assertFalse(versionedStockList.canRedo());
    }

    @Test
    public void undo_multipleStockListPointerAtEndOfStateList_success() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);

        versionedStockList.undo();
        assertStockListListStatus(versionedStockList,
                Collections.singletonList(emptyStockList),
                stockListWithAmy,
                Collections.singletonList(stockListWithBob));
    }

    @Test
    public void undo_multipleStockListPointerNotAtStartOfStateList_success() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 1);

        versionedStockList.undo();
        assertStockListListStatus(versionedStockList,
                Collections.emptyList(),
                emptyStockList,
                Arrays.asList(stockListWithAmy, stockListWithBob));
    }

    @Test
    public void undo_singleStockList_throwsNoUndoableStateException() {
        VersionedStockList versionedStockList = prepareStockListList(emptyStockList);

        assertThrows(VersionedStockList.NoUndoableStateException.class, versionedStockList::undo);
    }

    @Test
    public void undo_multipleStockListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 2);

        assertThrows(VersionedStockList.NoUndoableStateException.class, versionedStockList::undo);
    }

    @Test
    public void redo_multipleStockListPointerNotAtEndOfStateList_success() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 1);

        versionedStockList.redo();
        assertStockListListStatus(versionedStockList,
                Arrays.asList(emptyStockList, stockListWithAmy),
                stockListWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleStockListPointerAtStartOfStateList_success() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 2);

        versionedStockList.redo();
        assertStockListListStatus(versionedStockList,
                Collections.singletonList(emptyStockList),
                stockListWithAmy,
                Collections.singletonList(stockListWithBob));
    }

    @Test
    public void redo_singleStockList_throwsNoRedoableStateException() {
        VersionedStockList versionedStockList = prepareStockListList(emptyStockList);

        assertThrows(VersionedStockList.NoRedoableStateException.class, versionedStockList::redo);
    }

    @Test
    public void redo_multipleStockListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, stockListWithAmy, stockListWithBob);

        assertThrows(VersionedStockList.NoRedoableStateException.class, versionedStockList::redo);
    }

    @Test
    public void equals() {
        VersionedStockList versionedStockList = prepareStockListList(stockListWithAmy, stockListWithBob);

        // same values -> returns true
        VersionedStockList copy = prepareStockListList(stockListWithAmy, stockListWithBob);
        assertTrue(versionedStockList.equals(copy));

        // same object -> returns true
        assertTrue(versionedStockList.equals(versionedStockList));

        // null -> returns false
        assertFalse(versionedStockList.equals(null));

        // different types -> returns false
        assertFalse(versionedStockList.equals(1));

        // different state list -> returns false
        VersionedStockList differentStockListList = prepareStockListList(stockListWithBob, stockListWithCarl);
        assertFalse(versionedStockList.equals(differentStockListList));

        // different current pointer index -> returns false
        VersionedStockList differentCurrentStatePointer = prepareStockListList(
                stockListWithAmy, stockListWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 1);
        assertFalse(versionedStockList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedStockList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedStockList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedStockList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertStockListListStatus(VersionedStockList versionedStockList,
                                             List<ReadOnlyStockList> expectedStatesBeforePointer,
                                             ReadOnlyStockList expectedCurrentState,
                                             List<ReadOnlyStockList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new StockList(versionedStockList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedStockList.canUndo()) {
            versionedStockList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyStockList expectedStockList : expectedStatesBeforePointer) {
            assertEquals(expectedStockList, new StockList(versionedStockList));
            versionedStockList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyStockList expectedStockList : expectedStatesAfterPointer) {
            versionedStockList.redo();
            assertEquals(expectedStockList, new StockList(versionedStockList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedStockList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedStockList.undo());
    }

    /**
     * Creates and returns a {@code VersionedStockList} with the {@code stockListStates} added into it, and the
     * {@code VersionedStockList#currentStatePointer} at the end of list.
     */
    private VersionedStockList prepareStockListList(ReadOnlyStockList... stockListStates) {
        assertFalse(stockListStates.length == 0);

        VersionedStockList versionedStockList = new VersionedStockList(stockListStates[0]);
        for (int i = 1; i < stockListStates.length; i++) {
            versionedStockList.resetData(stockListStates[i]);
            versionedStockList.commit();
        }

        return versionedStockList;
    }

    /**
     * Shifts the {@code versionedStockList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedStockList versionedStockList, int count) {
        for (int i = 0; i < count; i++) {
            versionedStockList.undo();
        }
    }
}
