package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.RASPBERRY_PI;
import static seedu.address.testutil.TypicalItems.RPLIDAR;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.StockListBuilder;

public class VersionedStockListTest {

    private final ReadOnlyStockList addressBookWithAmy = new StockListBuilder().withItem(ARDUINO).build();
    private final ReadOnlyStockList addressBookWithBob = new StockListBuilder().withItem(RASPBERRY_PI).build();
    private final ReadOnlyStockList addressBookWithCarl = new StockListBuilder().withItem(RPLIDAR).build();
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
                emptyStockList, addressBookWithAmy, addressBookWithBob);

        versionedStockList.commit();
        assertStockListListStatus(versionedStockList,
                Arrays.asList(emptyStockList, addressBookWithAmy, addressBookWithBob),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleStockListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, addressBookWithAmy, addressBookWithBob);
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
                emptyStockList, addressBookWithAmy, addressBookWithBob);

        assertTrue(versionedStockList.canUndo());
    }

    @Test
    public void canUndo_multipleStockListPointerAtStartOfStateList_returnsTrue() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, addressBookWithAmy, addressBookWithBob);
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
                emptyStockList, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 2);

        assertFalse(versionedStockList.canUndo());
    }

    @Test
    public void canRedo_multipleStockListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 1);

        assertTrue(versionedStockList.canRedo());
    }

    @Test
    public void canRedo_multipleStockListPointerAtStartOfStateList_returnsTrue() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, addressBookWithAmy, addressBookWithBob);
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
                emptyStockList, addressBookWithAmy, addressBookWithBob);

        assertFalse(versionedStockList.canRedo());
    }

    @Test
    public void undo_multipleStockListPointerAtEndOfStateList_success() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, addressBookWithAmy, addressBookWithBob);

        versionedStockList.undo();
        assertStockListListStatus(versionedStockList,
                Collections.singletonList(emptyStockList),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void undo_multipleStockListPointerNotAtStartOfStateList_success() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 1);

        versionedStockList.undo();
        assertStockListListStatus(versionedStockList,
                Collections.emptyList(),
                emptyStockList,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void undo_singleStockList_throwsNoUndoableStateException() {
        VersionedStockList versionedStockList = prepareStockListList(emptyStockList);

        assertThrows(VersionedStockList.NoUndoableStateException.class, versionedStockList::undo);
    }

    @Test
    public void undo_multipleStockListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 2);

        assertThrows(VersionedStockList.NoUndoableStateException.class, versionedStockList::undo);
    }

    @Test
    public void redo_multipleStockListPointerNotAtEndOfStateList_success() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 1);

        versionedStockList.redo();
        assertStockListListStatus(versionedStockList,
                Arrays.asList(emptyStockList, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleStockListPointerAtStartOfStateList_success() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedStockList, 2);

        versionedStockList.redo();
        assertStockListListStatus(versionedStockList,
                Collections.singletonList(emptyStockList),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void redo_singleStockList_throwsNoRedoableStateException() {
        VersionedStockList versionedStockList = prepareStockListList(emptyStockList);

        assertThrows(VersionedStockList.NoRedoableStateException.class, versionedStockList::redo);
    }

    @Test
    public void redo_multipleStockListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedStockList versionedStockList = prepareStockListList(
                emptyStockList, addressBookWithAmy, addressBookWithBob);

        assertThrows(VersionedStockList.NoRedoableStateException.class, versionedStockList::redo);
    }

    @Test
    public void equals() {
        VersionedStockList versionedStockList = prepareStockListList(addressBookWithAmy, addressBookWithBob);

        // same values -> returns true
        VersionedStockList copy = prepareStockListList(addressBookWithAmy, addressBookWithBob);
        assertTrue(versionedStockList.equals(copy));

        // same object -> returns true
        assertTrue(versionedStockList.equals(versionedStockList));

        // null -> returns false
        assertFalse(versionedStockList.equals(null));

        // different types -> returns false
        assertFalse(versionedStockList.equals(1));

        // different state list -> returns false
        VersionedStockList differentStockListList = prepareStockListList(addressBookWithBob, addressBookWithCarl);
        assertFalse(versionedStockList.equals(differentStockListList));

        // different current pointer index -> returns false
        VersionedStockList differentCurrentStatePointer = prepareStockListList(
                addressBookWithAmy, addressBookWithBob);
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
     * Creates and returns a {@code VersionedStockList} with the {@code addressBookStates} added into it, and the
     * {@code VersionedStockList#currentStatePointer} at the end of list.
     */
    private VersionedStockList prepareStockListList(ReadOnlyStockList... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedStockList versionedStockList = new VersionedStockList(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedStockList.resetData(addressBookStates[i]);
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
