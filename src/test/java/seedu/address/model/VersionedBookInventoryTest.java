package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalBooks.ADD;
import static seedu.address.testutil.TypicalBooks.BOB;
import static seedu.address.testutil.TypicalBooks.CHEMISTRY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.BookInventoryBuilder;

public class VersionedBookInventoryTest {

    private final ReadOnlyBookInventory bookInventoryWithAmy = new BookInventoryBuilder().withBook(ADD).build();
    private final ReadOnlyBookInventory bookInventoryWithBob = new BookInventoryBuilder().withBook(BOB).build();
    private final ReadOnlyBookInventory bookInventoryWithCarl = new BookInventoryBuilder().withBook(CHEMISTRY).build();
    private final ReadOnlyBookInventory emptyBookInventory = new BookInventoryBuilder().build();

    @Test
    public void commit_singleBookInventory_noStatesRemovedCurrentStateSaved() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(emptyBookInventory);

        versionedBookInventory.commit();
        assertBookInventoryListStatus(versionedBookInventory,
                Collections.singletonList(emptyBookInventory),
                emptyBookInventory,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleBookInventoryPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);

        versionedBookInventory.commit();
        assertBookInventoryListStatus(versionedBookInventory,
                Arrays.asList(emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob),
                bookInventoryWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleBookInventoryPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedBookInventory, 2);

        versionedBookInventory.commit();
        assertBookInventoryListStatus(versionedBookInventory,
                Collections.singletonList(emptyBookInventory),
                emptyBookInventory,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleBookInventoryPointerAtEndOfStateList_returnsTrue() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);

        assertTrue(versionedBookInventory.canUndo());
    }

    @Test
    public void canUndo_multipleBookInventoryPointerAtStartOfStateList_returnsTrue() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedBookInventory, 1);

        assertTrue(versionedBookInventory.canUndo());
    }

    @Test
    public void canUndo_singleBookInventory_returnsFalse() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(emptyBookInventory);

        assertFalse(versionedBookInventory.canUndo());
    }

    @Test
    public void canUndo_multipleBookInventoryPointerAtStartOfStateList_returnsFalse() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedBookInventory, 2);

        assertFalse(versionedBookInventory.canUndo());
    }

    @Test
    public void canRedo_multipleBookInventoryPointerNotAtEndOfStateList_returnsTrue() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedBookInventory, 1);

        assertTrue(versionedBookInventory.canRedo());
    }

    @Test
    public void canRedo_multipleBookInventoryPointerAtStartOfStateList_returnsTrue() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedBookInventory, 2);

        assertTrue(versionedBookInventory.canRedo());
    }

    @Test
    public void canRedo_singleBookInventory_returnsFalse() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(emptyBookInventory);

        assertFalse(versionedBookInventory.canRedo());
    }

    @Test
    public void canRedo_multipleBookInventoryPointerAtEndOfStateList_returnsFalse() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);

        assertFalse(versionedBookInventory.canRedo());
    }

    @Test
    public void undo_multipleBookInventoryPointerAtEndOfStateList_success() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);

        versionedBookInventory.undo();
        assertBookInventoryListStatus(versionedBookInventory,
                Collections.singletonList(emptyBookInventory),
                bookInventoryWithAmy,
                Collections.singletonList(bookInventoryWithBob));
    }

    @Test
    public void undo_multipleBookInventoryPointerNotAtStartOfStateList_success() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedBookInventory, 1);

        versionedBookInventory.undo();
        assertBookInventoryListStatus(versionedBookInventory,
                Collections.emptyList(),
                emptyBookInventory,
                Arrays.asList(bookInventoryWithAmy, bookInventoryWithBob));
    }

    @Test
    public void undo_singleBookInventory_throwsNoUndoableStateException() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(emptyBookInventory);

        assertThrows(VersionedBookInventory.NoUndoableStateException.class, versionedBookInventory::undo);
    }

    @Test
    public void undo_multipleBookInventoryPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedBookInventory, 2);

        assertThrows(VersionedBookInventory.NoUndoableStateException.class, versionedBookInventory::undo);
    }

    @Test
    public void redo_multipleBookInventoryPointerNotAtEndOfStateList_success() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedBookInventory, 1);

        versionedBookInventory.redo();
        assertBookInventoryListStatus(versionedBookInventory,
                Arrays.asList(emptyBookInventory, bookInventoryWithAmy),
                bookInventoryWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleBookInventoryPointerAtStartOfStateList_success() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedBookInventory, 2);

        versionedBookInventory.redo();
        assertBookInventoryListStatus(versionedBookInventory,
                Collections.singletonList(emptyBookInventory),
                bookInventoryWithAmy,
                Collections.singletonList(bookInventoryWithBob));
    }

    @Test
    public void redo_singleBookInventory_throwsNoRedoableStateException() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(emptyBookInventory);

        assertThrows(VersionedBookInventory.NoRedoableStateException.class, versionedBookInventory::redo);
    }

    @Test
    public void redo_multipleBookInventoryPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList(
                emptyBookInventory, bookInventoryWithAmy, bookInventoryWithBob);

        assertThrows(VersionedBookInventory.NoRedoableStateException.class, versionedBookInventory::redo);
    }

    @Test
    public void equals() {
        VersionedBookInventory versionedBookInventory = prepareBookInventoryList
                (bookInventoryWithAmy, bookInventoryWithBob);

        // same values -> returns true
        VersionedBookInventory copy = prepareBookInventoryList(bookInventoryWithAmy, bookInventoryWithBob);
        assertTrue(versionedBookInventory.equals(copy));

        // same object -> returns true
        assertTrue(versionedBookInventory.equals(versionedBookInventory));

        // null -> returns false
        assertFalse(versionedBookInventory.equals(null));

        // different types -> returns false
        assertFalse(versionedBookInventory.equals(1));

        // different state list -> returns false
        VersionedBookInventory differentBookInventoryList = prepareBookInventoryList(
                bookInventoryWithBob, bookInventoryWithCarl);
        assertFalse(versionedBookInventory.equals(differentBookInventoryList));

        // different current pointer index -> returns false
        VersionedBookInventory differentCurrentStatePointer = prepareBookInventoryList(
                bookInventoryWithAmy, bookInventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedBookInventory, 1);
        assertFalse(versionedBookInventory.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedBookInventory}
     * is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedBookInventory#currentStatePointer}
     * is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedBookInventory#currentStatePointer}
     * is equal to {@code expectedStatesAfterPointer}
     */
    private void assertBookInventoryListStatus(VersionedBookInventory versionedBookInventory,
                                               List<ReadOnlyBookInventory> expectedStatesBeforePointer,
                                               ReadOnlyBookInventory expectedCurrentState,
                                               List<ReadOnlyBookInventory> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new BookInventory(versionedBookInventory), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedBookInventory.canUndo()) {
            versionedBookInventory.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyBookInventory expectedBookInventory : expectedStatesBeforePointer) {
            assertEquals(expectedBookInventory, new BookInventory(versionedBookInventory));
            versionedBookInventory.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyBookInventory expectedBookInventory : expectedStatesAfterPointer) {
            versionedBookInventory.redo();
            assertEquals(expectedBookInventory, new BookInventory(versionedBookInventory));
        }

        // check that there are no more states after pointer
        assertFalse(versionedBookInventory.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedBookInventory.undo());
    }

    /**
     * Creates and returns a {@code VersionedBookInventory} with the {@code bookInventoryStates} added into it, and the
     * {@code VersionedBookInventory#currentStatePointer} at the end of list.
     */
    private VersionedBookInventory prepareBookInventoryList(ReadOnlyBookInventory... bookInventoryStates) {
        assertFalse(bookInventoryStates.length == 0);

        VersionedBookInventory versionedBookInventory = new VersionedBookInventory(bookInventoryStates[0]);
        for (int i = 1; i < bookInventoryStates.length; i++) {
            versionedBookInventory.resetData(bookInventoryStates[i]);
            versionedBookInventory.commit();
        }

        return versionedBookInventory;
    }

    /**
     * Shifts the {@code versionedBookInventory#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedBookInventory versionedBookInventory, int count) {
        for (int i = 0; i < count; i++) {
            versionedBookInventory.undo();
        }
    }
}
