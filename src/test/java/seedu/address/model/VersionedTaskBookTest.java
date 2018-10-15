package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static UnRefactored.testutil.TypicalTasks.CS2113_TASK_1;
import static UnRefactored.testutil.TypicalTasks.CS2113_TASK_2;
import static UnRefactored.testutil.TypicalTasks.CS2113_TASK_3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import UnRefactored.testutil.AddressBookBuilder;

public class VersionedTaskBookTest {

    private final ReadOnlyTaskBook addressBookWithTask1 = new AddressBookBuilder().withTask(CS2113_TASK_1).build();
    private final ReadOnlyTaskBook addressBookWithTask2 = new AddressBookBuilder().withTask(CS2113_TASK_2).build();
    private final ReadOnlyTaskBook addressBookWithTask3 = new AddressBookBuilder().withTask(CS2113_TASK_3).build();
    private final ReadOnlyTaskBook emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(emptyAddressBook);

        versionedTaskBook.commit();
        assertAddressBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);

        versionedTaskBook.commit();
        assertAddressBookListStatus(versionedTaskBook,
                Arrays.asList(emptyAddressBook, addressBookWithTask1, addressBookWithTask2),
                addressBookWithTask2,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        versionedTaskBook.commit();
        assertAddressBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);

        assertTrue(versionedTaskBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        assertTrue(versionedTaskBook.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedTaskBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        assertFalse(versionedTaskBook.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        assertTrue(versionedTaskBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        assertTrue(versionedTaskBook.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedTaskBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);

        assertFalse(versionedTaskBook.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);

        versionedTaskBook.undo();
        assertAddressBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithTask1,
                Collections.singletonList(addressBookWithTask2));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        versionedTaskBook.undo();
        assertAddressBookListStatus(versionedTaskBook,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithTask1, addressBookWithTask2));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedTaskBook.NoUndoableStateException.class, versionedTaskBook::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        assertThrows(VersionedTaskBook.NoUndoableStateException.class, versionedTaskBook::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        versionedTaskBook.redo();
        assertAddressBookListStatus(versionedTaskBook,
                Arrays.asList(emptyAddressBook, addressBookWithTask1),
                addressBookWithTask2,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        versionedTaskBook.redo();
        assertAddressBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithTask1,
                Collections.singletonList(addressBookWithTask2));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedTaskBook.NoRedoableStateException.class, versionedTaskBook::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithTask1, addressBookWithTask2);

        assertThrows(VersionedTaskBook.NoRedoableStateException.class, versionedTaskBook::redo);
    }

    @Test
    public void equals() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(addressBookWithTask1, addressBookWithTask2);

        // same values -> returns true
        VersionedTaskBook copy = prepareAddressBookList(addressBookWithTask1, addressBookWithTask2);
        assertTrue(versionedTaskBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedTaskBook.equals(versionedTaskBook));

        // null -> returns false
        assertFalse(versionedTaskBook.equals(null));

        // different types -> returns false
        assertFalse(versionedTaskBook.equals(1));

        // different state list -> returns false
        VersionedTaskBook differentAddressBookList = prepareAddressBookList(addressBookWithTask2, addressBookWithTask3);
        assertFalse(versionedTaskBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedTaskBook differentCurrentStatePointer = prepareAddressBookList(
                addressBookWithTask1, addressBookWithTask2);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);
        assertFalse(versionedTaskBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedTaskBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTaskBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedTaskBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedTaskBook versionedTaskBook,
                                             List<ReadOnlyTaskBook> expectedStatesBeforePointer,
                                             ReadOnlyTaskBook expectedCurrentState,
                                             List<ReadOnlyTaskBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new AddressBook(versionedTaskBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTaskBook.canUndo()) {
            versionedTaskBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTaskBook expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new AddressBook(versionedTaskBook));
            versionedTaskBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTaskBook expectedAddressBook : expectedStatesAfterPointer) {
            versionedTaskBook.redo();
            assertEquals(expectedAddressBook, new AddressBook(versionedTaskBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTaskBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTaskBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedTaskBook} with the {@code addressBookStates} added into it, and the
     * {@code VersionedTaskBook#currentStatePointer} at the end of list.
     */
    private VersionedTaskBook prepareAddressBookList(ReadOnlyTaskBook... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedTaskBook versionedTaskBook = new VersionedTaskBook(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedTaskBook.resetData(addressBookStates[i]);
            versionedTaskBook.commit();
        }

        return versionedTaskBook;
    }

    /**
     * Shifts the {@code versionedTaskBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTaskBook versionedTaskBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedTaskBook.undo();
        }
    }
}
